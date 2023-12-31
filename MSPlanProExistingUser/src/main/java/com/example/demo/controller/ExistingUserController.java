package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.RechargePayment;



public class ExistingUserController 
{

	@PostMapping("/recharge/{phonenumber}")
	 @CrossOrigin(origins="http://localhost:4200")
	 public ResponseEntity<String> recharge(
	            @PathVariable String phonenumber,
	            @RequestBody Map<String, String> requestBody) {
		 	String firstname = requestBody.get("userName");
		 	System.out.println("recharge"+firstname);
	        String planname = requestBody.get("planname");
	        System.out.println(planname);
	        String validity = requestBody.get("validity");
	        System.out.println(validity);
	        String emailid = requestBody.get("emailid");
	        String price = requestBody.get("price");
	        System.out.println(price);
	        int validityAsInt = Integer.parseInt(validity);
	        
	        String simnumber = requestBody.get("simnumber");
	        System.out.println("new sim "+simnumber);
	        
	        LocalDate currentDate = LocalDate.now();
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Change the pattern as needed

	        // Format the LocalDate as a string
	        String formattedDate = currentDate.format(formatter);

	        
	        System.out.println(validityAsInt);
	        double priceAsDouble = Double.parseDouble(price); // Parse as double
	        System.out.println(priceAsDouble);
	       
	        String transactionId = ps.generateTransactionId(phonenumber);
	        System.out.println("tid "+transactionId);
	        
	        RechargePayment updatedPayment = ps.updatePayment(emailid,firstname,phonenumber,simnumber,planname,priceAsDouble,validityAsInt,formattedDate,transactionId);

	        if (updatedPayment != null) {
	        	System.out.println("before return "+transactionId);
	            return ResponseEntity.ok(transactionId);
	        } else {
	            return ResponseEntity.badRequest().body("Payment not found for the given phone number");
	        }
	    }
}
