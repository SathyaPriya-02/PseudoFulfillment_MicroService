package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.RechargePlans;

@Service
public class MyService {
	
	 public boolean containsSearchText(RechargePlans plan, String searchText) {
	        if (plan.getPlanname() != null && plan.getPlanname().toLowerCase().contains(searchText)) {
	            return true;
	        }
	        if (plan.getPlanDescription() != null && plan.getPlanDescription().toLowerCase().contains(searchText)) {
	            return true;
	        }
	        if (plan.getPrice() != null && plan.getPrice().toLowerCase().contains(searchText)) {
	            return true;
	        }
	        if (plan.getValidity() != null && plan.getValidity().toLowerCase().contains(searchText)) {
	            return true;
	        }
	        return false;
	    }
	 

}
