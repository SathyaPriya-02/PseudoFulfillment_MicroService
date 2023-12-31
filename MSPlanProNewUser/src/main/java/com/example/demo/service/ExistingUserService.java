package com.example.demo.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.RechargePayment;
import com.example.demo.entity.RequestSim;
import com.example.demo.repo.RechargePaymentRepo;
import com.example.demo.repo.RequestSimRepo;



public class ExistingUserService {
	
	@Autowired
	RechargePaymentRepo payrepo;
	
	@Autowired
	 RequestSimRepo simrepo;
	
	
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
	 
	 public RechargePayment updatePayment(String emailid,String firstname,String phonenumber,String simnumber, String planname,  double price,int validity,String rechargedate,String transactionId) {
	        // Find the payment by phone number
		 		RequestSim user = simrepo.findByPhonenumber(phonenumber);
		 		if(user != null)
		 		{
		 			user.setCurrent_plan(planname);
		 		}

		 		RechargePayment payment = new RechargePayment();
	            // Update payment information
		 		payment.setPhonenumber(phonenumber);
		 		payment.setSimnumber(simnumber);
	        	payment.setFirstname(firstname);
	            payment.setPlanname(planname);
	            payment.setValidity(validity);
	            payment.setPrice(price);
	            payment.setRechargedate(rechargedate);
	            payment.setTransactionid(transactionId);
	            payment.setEmailid(emailid);

	            if (validity > 0) {
	                
	                    // Parse the recharge date string to a Date
	                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                    Date date = null;
						try {
							date = dateFormat.parse(rechargedate);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

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
	                    payment.setExpiredate(formattedExpirationDate);
	                    user.setExpire_date(formattedExpirationDate);
	                
	            }
	            // Save the updated payment
	            return payrepo.save(payment);
	        
	    }

}
