package com.nesterenya.controllers.rhouse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nesterenya.modal.Ad;
import com.nesterenya.modal.Wish;
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
	
	// TODO возможно следует сделать чтобы все методы API возвращали JSON
	@RequestMapping(value = "/count/{cardOnPage}", method = RequestMethod.GET)
	public int count(@PathVariable(value="cardOnPage") int cardOnPage) {
		return service.getCountPages(cardOnPage);
	}
	
	@RequestMapping(value = "/get/{cardOnPage}/{pageNumber}", method = RequestMethod.GET)
	public List<Ad> get(@PathVariable(value="cardOnPage") int cardOnPage, @PathVariable(value="pageNumber") int pageNumber ) {
		return service.getPage(pageNumber, cardOnPage);
	}
}