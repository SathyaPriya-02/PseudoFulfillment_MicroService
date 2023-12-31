package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.RechargePlans;
import com.example.demo.repo.RechargePlanRepo;
import com.example.demo.service.MyService;


@RestController

@RequestMapping("/user")
public class ViewPlansController 
{
		
	@Autowired
	RechargePlanRepo planrepo;
	
	@Autowired
	MyService service;
	
	
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/plan")
	@ResponseBody
	public List<RechargePlans> viewProduct()
	{
		return planrepo.findAll();
	}
	
	
	@GetMapping("/searchplan/{searchText}")
	 @CrossOrigin(origins="http://localhost:4200")
	 public List<RechargePlans> searchPlans(@PathVariable String searchText) {
	     System.out.println(searchText);
	     //return planrepo.findByPlanname(searchText);
	     List<RechargePlans> matchingPlans = new ArrayList<>();

	     List<RechargePlans> plans = planrepo.findAll(); // Assuming planrepo is your repository for RechargePlans

	     for (RechargePlans plan : plans) {
	         if (service.containsSearchText(plan, searchText)) {
	             matchingPlans.add(plan);
	         }
	     }

	     return matchingPlans;
	 }
	
	
	 
}
