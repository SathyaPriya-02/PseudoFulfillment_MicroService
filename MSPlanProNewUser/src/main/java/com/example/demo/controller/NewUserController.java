package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.RechargePayment;
import com.example.demo.entity.RequestSim;
import com.example.demo.repo.RequestSimRepo;
import com.example.demo.service.NewUserService;



@RestController

@RequestMapping("/user")
public class NewUserController 
{
	
	@Autowired
	RequestSimRepo simrepo;
	
	@Autowired
	NewUserService service;
	
	@ResponseBody
	@PostMapping("/newuser")
	@CrossOrigin(origins="http://localhost:4200")
	public String addProduct(@RequestBody RequestSim rs)
	{
		System.out.println("registered details "+rs);
		rs.setStatus("inactive");
		simrepo.save(rs);
		return "added";
	}
	
	@ResponseBody
	@PostMapping("/sendmail")
	@CrossOrigin(origins="http://localhost:4200")
	public Map<String, String> sendMail(@RequestBody String email)
	{
		System.out.println("Email id for sending phone number and sim card number" +email);
		String emailAddress=email;
		Map<String, String> response = service.sendMail(emailAddress);
		return response;		
	}
	
	@PostMapping("/activate/{phonenumber}")
	@CrossOrigin(origins="http://localhost:4200")
	 public ResponseEntity<String> activateUser(@PathVariable String phonenumber,@RequestBody Map<String, String> requestBody) {
	        System.out.println("Im there "+phonenumber);
		 	String planname = requestBody.get("planname");
	        String validity = requestBody.get("validity");
	        String price = requestBody.get("price");
	        String name = requestBody.get("userName");
	        String simnumber = requestBody.get("simnumber");
	        int validityAsInt = Integer.parseInt(validity);
	        int priceAsInt = Integer.parseInt(price);
	        
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Change the pattern as needed
	        // Format the LocalDate as a string
	        String formattedDate = currentDate.format(formatter);
	        String transactionId = service.generateTransactionId(phonenumber);
	        System.out.println("active "+planname);
	        System.out.println(validityAsInt);
	        System.out.println(priceAsInt);
	        System.out.println(formattedDate);
	       
	        
	        boolean activationSuccessful = service.activateUser(simnumber,phonenumber,name,planname,validityAsInt,priceAsInt,formattedDate,transactionId );

	        if (activationSuccessful) {
	            return ResponseEntity.ok(transactionId);
	        } else {
	            return ResponseEntity.badRequest().body("Invalid activation token");
	        }
	    }
	
	
}
