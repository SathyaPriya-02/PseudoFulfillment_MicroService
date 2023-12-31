package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.RequestSim;

public interface RequestSimRepo extends JpaRepository<RequestSim, String> {
	
	RequestSim findByPhonenumber(String phoneNumber);

	RequestSim findByPhonenumberAndEmailid(String phoneNumber, String email);

	

}
