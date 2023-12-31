package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.config.OtpConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class OtpService {

	
	@Autowired
	private OtpConfig twilioConfig;
	
	
	
	
	
	private Map<String, String> otpStorage = new HashMap<>();

 

	public String generateRandomOTP() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        
        return String.valueOf(otpValue);
    }

 

	public boolean sendOtp(String phoneNumber, String otp) {
	try {
		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
		 Message message = Message.creator(
			      new com.twilio.type.PhoneNumber("+91"+phoneNumber),
			      new com.twilio.type.PhoneNumber("+12564454765"),
			      "Your OTP is:"+otp)
			    .create();
		 otpStorage.put(phoneNumber,otp);
		 return true;
	}catch(Exception e)
	{

	e.printStackTrace();
		return false;
	}
	}
	
	public static void sendOtpByEmail(String otp,Session session, String fromEmail, String subject, String body, String toEmail) 
	{
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(toEmail));
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setText(body, "UTF-8");

			// Add DKIM and SPF headers here if you have configured them
			System.out.println("Message is ready");
			Transport.send(msg);
			System.out.println("Email Sent Successfully!!");



		} catch (Exception e) {



			e.printStackTrace();



		}



	}

	public boolean sendMail(String otp,String emailAddress) {

		System.out.println("Outlook Email Start");



		String smtpHostServer = "smtp.office365.com";



		final String emailID = "priya.jp0210@outlook.com";



		final String password = "Welcome@123";



		String toEmail = emailAddress;



		String subject = "Generated otp";
		
		
		String messageBody = "otp is " +otp;



		Properties props = new Properties();



		props.put("mail.smtp.host", smtpHostServer);



		props.put("mail.smtp.port", "587");



		props.put("mail.smtp.auth", "true");



		props.put("mail.smtp.starttls.enable", "true");


		



		Session session = Session.getInstance(props, new Authenticator() {



			protected PasswordAuthentication getPasswordAuthentication() {



				return new PasswordAuthentication(emailID, password);



			}



		});







		sendOtpByEmail(otp,session, emailID, subject, messageBody, toEmail);

		return true;

}
	

	
	
	

	public boolean validateOtp(String phoneNumber, String userEnteredOtp) {
        // Retrieve the previously generated OTP for the given phone number from the map
        String storedOtp = otpStorage.get(phoneNumber);
        // Compare the user-entered OTP with the stored OTP
        Boolean s= storedOtp != null && storedOtp.equals(userEnteredOtp);
        System.out.println(s);
        System.out.println(storedOtp);
        return s;
        
    }
	public void clearOtp(String phoneNumber) 
	{
		otpStorage.remove(phoneNumber);
	}
	
	
	
}
