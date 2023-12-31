package com.example.demo.repo;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.RechargePlans;


public interface RechargePlanRepo extends JpaRepository<RechargePlans, Integer> {
	
   
    
}
