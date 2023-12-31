package com.example.demo.entity;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSim 
{
	@Id
	private String emailid;
	
	private String first_name;
	private String last_name;
	private String dob;
	private String gender;
	private String location;
	private String simnumber;
	private String phonenumber;
	private String status;
	private String current_plan;
	private String expire_date;
	private int prowallet;
	
	

}
