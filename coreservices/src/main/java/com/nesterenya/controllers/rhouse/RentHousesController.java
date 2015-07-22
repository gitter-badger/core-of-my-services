package com.nesterenya.controllers.rhouse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nesterenya.modal.Ad;
import com.nesterenya.services.RentService;

@RestController
@RequestMapping("/ads/rent")
public class RentHousesController {

	@Autowired
	private RentService service;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Ad> result() {
		
		return service.getAll();
	}
	
	
	@RequestMapping(value = "/test_parsed", method = RequestMethod.GET)
	public List<Ad> parsed() {
		
		return service.getTestParsedData();
	}
}