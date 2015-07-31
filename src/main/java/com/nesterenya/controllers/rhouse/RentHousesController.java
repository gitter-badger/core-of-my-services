package com.nesterenya.controllers.rhouse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger log = LoggerFactory.getLogger(RentHousesController.class); 
	
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
	
	@RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
	public Ad one(@PathVariable(value="id") String id) {
		Ad ad = service.get(id);
		//log.info("find obj: "+ ad);
		return ad;
	}
	
	@RequestMapping(value = "/get/{cardOnPage}/{pageNumber}", method = RequestMethod.GET)
	public List<Ad> get(@PathVariable(value="cardOnPage") int cardOnPage, @PathVariable(value="pageNumber") int pageNumber ) {
		return service.getPage(pageNumber, cardOnPage);
	}
}