package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargePlans {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int planID;
	private String planname;
	private String planDescription;
	private String price;
	private String validity;

}
