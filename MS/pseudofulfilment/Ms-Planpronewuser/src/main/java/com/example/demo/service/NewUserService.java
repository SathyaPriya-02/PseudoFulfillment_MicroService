package com.example.demo.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.RechargePayment;
import com.example.demo.entity.RequestSim;
import com.example.demo.repo.RechargePaymentRepo;
import com.example.demo.repo.RequestSimRepo;


@Service
public class NewUserService 
{
	@Autowired
	RechargePaymentRepo payrepo;
	
	@Autowired
	RequestSimRepo simrepo;
	
	public Map<String, String> sendMail(String emailAddress) {
	    System.out.println("Outlook Email Start");
	    String smtpHostServer = "smtp.office365.com";
	    final String emailID = "priya.jp0210@outlook.com";
	    final String password = "Welcome@123";
	    String toEmail = emailAddress;
	    String phoneNumber = generateRandomPhoneNumber();
	    String simNumber = generateSimCardNumber();
//	    String activationToken = generateActivationToken();
	    System.out.println("Generated Phone Number from send mail: " + phoneNumber);
	    System.out.println("Generated SIM Number from send mail: " + simNumber);
//	    System.out.println(activationToken);

	    String subject = "Your Plan Pro SIM Card Information";
	    String activationLink = "http://localhost:4200/starter-recharge";
	    System.out.println("Activation Link " + activationLink);

	    // Compose the email body
	    String messageBody = "<html>" +
	            "<head>" +
	            "<style>" +
	            "  h1 { color: #FF0000; }" +
	            "  p { font-size: 16px; }" +
	            "  a { color: #3498db; text-decoration: none; }" +
	            "  a:hover { text-decoration: underline; }" +
	            "</style>" +
	            "</head>" +
	            "<body>" +
	            "<h1>Welcome to Plan Pro!</h1>" +
	            "<p>Thank you for choosing Plan Pro for your mobile needs. Your SIM card information is below:</p>" +
	            "<p><strong>SIM Number:</strong> " + simNumber + "</p>" +
	            "<p><strong>Phone Number:</strong> " + phoneNumber + "</p>" +
	            "<p>To activate your account, please click on the following link:</p>" +
	            "<a href='" + activationLink + "'>" + activationLink + "</a>" +
	            "<p>We're excited to have you on board. Enjoy the world of Plan Pro!</p>" +
	            "</body>" +
	            "</html>";

	    Map<String, String> response = new HashMap<>();
	    response.put("phoneNumber", phoneNumber);
	    response.put("simNumber", simNumber);
//	    response.put("activationToken", activationToken);

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

	    // Create the MimeMessage
	    MimeMessage msg = new MimeMessage(session);
	    try {
			msg.setFrom(new InternetAddress(emailID));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			msg.setSubject(subject, "UTF-8");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Set the content type to HTML
	    try {
			msg.setContent(messageBody, "text/html; charset=utf-8");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Send the email
	    try {
			Transport.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return response;
	}
	
	public String generateRandomPhoneNumber() {
	    Random random = new Random();
	    StringBuilder phoneNumber = new StringBuilder();
	    phoneNumber.append(random.nextInt(2) + 8); // Start with 8 or 9

	    // Generate the remaining 9 digits
	    for (int i = 0; i < 9; i++) {
	        int digit = random.nextInt(10); // Generate a random digit between 0 and 9
	        phoneNumber.append(digit);
	    }

	    return phoneNumber.toString();
	}
	
	
	public static String generateSimCardNumber() {
        // Define the length of the SIM card number you want to generate
        int simCardNumberLength = 12; // Adjust the length as needed

        // Characters allowed in the SIM card number
        String characters = "0123456789";
        
        // Create a StringBuilder to store the generated SIM card number
        StringBuilder simCardNumber = new StringBuilder();

        // Create an instance of Random
        Random random = new Random();

        // Generate the SIM card number
        for (int i = 0; i < simCardNumberLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            simCardNumber.append(randomChar);
        }
        System.out.println(simCardNumber);
        return simCardNumber.toString();
    }
	
	public boolean activateUser(String simnumber,String phonenumber, String name,String planname, int validity, int price,String rechargeDate,String transactionId) {
	    // Retrieve the user by activation token
		System.out.println("for pay "+phonenumber);
		System.out.println("active ser" +planname);
	    RequestSim user = simrepo.findByPhonenumber(phonenumber);
	    RechargePayment pay = new RechargePayment();
	    

	    if (user != null) {
	        // Update the user's status to "active"
	        user.setStatus("active");
	        user.setCurrent_plan(planname);
	    }
           // Update other payment properties
	        pay.setPhonenumber(phonenumber);
	        pay.setSimnumber(simnumber);
	        pay.setPlanname(planname);
	        pay.setFirstname(name);
	        pay.setValidity(validity);
	        pay.setPrice(price);
	        pay.setRechargedate(rechargeDate);
	        pay.setTransactionid(transactionId);
	        System.out.println("Value updated");
	        if (validity > 0) {
	                try {
	                    // Parse the recharge date string to a Date
	                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                    Date date = dateFormat.parse(rechargeDate);

	                    // Calculate the expiration date by adding validity days
	                    Calendar calendar = Calendar.getInstance();
	                    calendar.setTime(date);
	                    calendar.add(Calendar.DATE, validity);

	                    // Get the expiration date as a Date
	                    Date expirationDate = calendar.getTime();

	                    // Format the expiration date as a string with the desired format (only date)
	                    String formattedExpirationDate = dateFormat.format(expirationDate);

	                    // Update the user's expiration date
	                    // user.setExpiredate(formattedExpirationDate);
	                    pay.setExpiredate(formattedExpirationDate);
	                    user.setExpire_date(formattedExpirationDate);
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                    return false; // Return false if there is a parsing error
	                }
	            }

	            
	            payrepo.save(pay);
	            simrepo.save(user);

	        return true; // Activation was successful
	    

	   
	}
	
	public String generateTransactionId(String phoneNumber) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Generate a random number
        Random random = new Random();
        int randomValue = random.nextInt(10000);

        // Combine the elements to create a unique transaction ID as a string
        DecimalFormat decimalFormat = new DecimalFormat("0");
        decimalFormat.setMaximumFractionDigits(0);
        String transactionId = phoneNumber+decimalFormat.format(randomValue);

        // You can hash or encrypt the transaction ID if needed
        // For example: transactionId = hashFunction(transactionId);

        return transactionId;
    }
}
