package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargePayment {

	
	
	private String phonenumber;
	private String simnumber;
	private String firstname;
	private String emailid;
	
	private String planname;
	private double price;
	@Id
	private String transactionid;
	private String expiredate;
	private int validity;
	private int prowallet;
	private String rechargedate;
	
	
	
	
}
