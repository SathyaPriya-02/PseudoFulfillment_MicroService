package com.example.demo.controller;

//import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.RequestSim;
import com.example.demo.repo.RequestSimRepo;




public class MyController 
{
	@Autowired
	RequestSimRepo simrepo;
	

	@GetMapping("/detail/{phoneNumber}")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<RequestSim> getUserDetails(@PathVariable String phoneNumber)
	 {
		 System.out.println(phoneNumber);
	        // Assuming you have a UserRepository to fetch user details
	        RequestSim user = simrepo.findByPhonenumber(phoneNumber);
	        System.out.println("details "+user);
	        CacheControl cacheControl = CacheControl.noStore().mustRevalidate();
	        if (user != null) {
	        	System.out.println(user);
	        	return ResponseEntity.ok()
	                    .cacheControl(cacheControl)
	                    .body(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	 @PostMapping("/updatewallet/{phonenumber}")
	    public ResponseEntity<?> updateWalletAmount(@PathVariable String phonenumber, @RequestBody int prowallet) {
	        try {
	            // Fetch the user by phone number (you should replace this with your actual user retrieval logic)
	            RequestSim user =simrepo.findByPhonenumber(phonenumber);

	            if (user == null) {
	                return ResponseEntity.notFound().build();
	            }

	            // Update the wallet amount
	            user.setProwallet(prowallet);

	            // Save the updated user with the new wallet amount
	            simrepo.save(user);

	            return ResponseEntity.ok("Wallet amount updated successfully");
	        } catch (Exception e) {
	        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating wallet amount");
	        }
	    }
	
	 @CrossOrigin(origins = "http://localhost:4200")
	 @PostMapping("/addtowallet/{phonenumber}")
	 public ResponseEntity<?> AddWalletAmount(@PathVariable String phonenumber, @RequestBody Map<String, Integer> requestBody) {
	     try {
	         // Fetch the user by phone number (you should replace this with your actual user retrieval logic)
	         RequestSim user = simrepo.findByPhonenumber(phonenumber);

	         if (user == null) {
	             return ResponseEntity.notFound().build();
	         }

	         // Get the 'amount' from the request body
	         Integer amount = requestBody.get("amount");

	         // Update the wallet amount
	         int currentWalletAmount = user.getProwallet();

	         // Calculate the new wallet amount by adding the entered amount
	         int newWalletAmount = currentWalletAmount + amount;

	         user.setProwallet(newWalletAmount);

	         // Save the updated user with the new wallet amount
	         simrepo.save(user);

	         Map<String, String> response = new HashMap<>();
	         response.put("message", "Wallet amount updated successfully");

	         return ResponseEntity.ok(response);
	     } catch (Exception e) {
	    	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating wallet amount");
	     }
	 }
}
