package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.RechargePayment;
import com.example.demo.entity.RequestSim;

public interface RechargePaymentRepo extends JpaRepository<RechargePayment, Integer> {
	
	 RechargePayment findByPhonenumber(String phonenumber);
	 
	 RechargePayment findByTransactionid(String tid);

}
