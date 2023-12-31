package com.example.demo.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BillService;

@RestController

@RequestMapping("/user")
public class BillController 
{
	@Autowired
	BillService service;
	
	@CrossOrigin(origins="http://localhost:4200")
	 @PostMapping("/activation-bill/{emailAddress}")
	 public ResponseEntity<String> generateActivationBill(@PathVariable String emailAddress,
	            @RequestBody Map<String, String> requestBody){
		 
		 System.out.println("activation mail "+emailAddress);
		 String firstname = requestBody.get("firstname");
		 String phonenumber = requestBody.get("phonenumber");
		 String planname = requestBody.get("planname");
	     String validity = requestBody.get("validity");
	     String price = requestBody.get("price");
	     String transactionid = requestBody.get("transactionid");
	     String expiredate = requestBody.get("expiredate");
//	     String rechargedate = billdetails.getRechargedate();
		 
		 System.out.println(firstname+" "+phonenumber+" "+planname+" "+validity+" "+price+" "+transactionid);
	
		 try {
	        	
	        	System.out.println("email for pdf "+emailAddress);

	            service.generatePdfActivationBill(emailAddress,firstname,phonenumber,planname,validity,price,transactionid);

	            String userName = firstname; // Replace with the actual user name

	            String result = service.sendActivationBillMail(emailAddress, firstname ,phonenumber,planname,price,validity,transactionid);



	            return ResponseEntity.ok("Bill generated and email sent successfully: " + result);

	        } catch (Exception e) {

	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating wallet amount");

	        }

	    }
	
		
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/generate-bill/{emailAddress}")
	 public ResponseEntity<String> generateBill(@PathVariable String emailAddress, @RequestBody Map<String, String> requestBody){
		 
		 System.out.println(emailAddress);
		 String firstname = requestBody.get("firstname");
		 String phonenumber = requestBody.get("phonenumber");
		 String planname = requestBody.get("planname");
	     String validity = requestBody.get("validity");
	     String price = requestBody.get("price");
	     String transactionid = requestBody.get("transactionid");
	     String expiredate = requestBody.get("expiredate");
//	     String rechargedate = billdetails.getRechargedate();
		 
		 System.out.println(firstname+" "+phonenumber+" "+planname+" "+validity+" "+price+" "+transactionid);
	
		 try {
	        	
	        	System.out.println("email for pdf "+emailAddress);

	        	service.generatePdfBill(emailAddress,firstname,phonenumber,planname,validity,price,transactionid);

	            String userName = firstname; // Replace with the actual user name

	            String result = service.sendMail(emailAddress, firstname ,phonenumber,planname,price,validity,transactionid);



	            return ResponseEntity.ok("Bill generated and email sent successfully: " + result);

	        } catch (Exception e) {

	            
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating bill and sending email");

	        }

	    }
	
}
