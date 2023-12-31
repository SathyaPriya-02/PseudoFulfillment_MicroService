package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPlanprobillApplication {
	
	public static void main(String arg[]) 
	{
	SpringApplication.run(MsPlanprobillApplication.class, arg);
	}
			
	

}
