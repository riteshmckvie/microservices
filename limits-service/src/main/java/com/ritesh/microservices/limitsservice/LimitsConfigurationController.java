package com.ritesh.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
		
	}
	
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod="faultBackToleranceConfig")
	public LimitConfiguration faultToleranceExample() {
		throw new RuntimeException("error");
	}
	
	public LimitConfiguration faultBackToleranceConfig() {
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
		
	}
}
