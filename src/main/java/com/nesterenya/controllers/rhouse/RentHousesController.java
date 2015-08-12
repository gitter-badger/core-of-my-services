package com.nesterenya.controllers.rhouse;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nesterenya.modal.Ad;
import com.nesterenya.modal.Wish;
import com.nesterenya.services.RentService;

@RestController
@RequestMapping(value={"/rent"})
public class RentHousesController {
	private Logger log = LoggerFactory.getLogger(RentHousesController.class); 
	
	@Autowired
	private RentService service;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Ad> result() {
		return service.getAll();
	}
	
	//@ExceptionHandler(UnknownMatchException.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Ad add(@RequestBody Ad ad) {

		log.error(ad.toString());

		if(ad.getAddress()==null||ad.getAddress().trim().length()==0) {
			log.error("address");
			throw new UnknownMatchException("Empty Address");
		}
		if(ad.getDescription()==null||ad.getDescription().trim().length()==0) {
			log.error("Description");
			throw new UnknownMatchException("Empty Description");
		}
		if(ad.getContacts()==null||ad.getContacts().trim().length()==0) {
			log.error("Contacts");
			throw new UnknownMatchException("Empty Contacts");
		}

		ad.setDate(new Date());
		ad.setViews(0);
		ad.getContacts();

		return service.add(ad);
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
	public List<Ad> get(
			@PathVariable(value="cardOnPage") int cardOnPage,
			@PathVariable(value="pageNumber") int pageNumber,
			@RequestParam(value="sortBy", defaultValue="-date") String sortBy) {

		return service.getPage(pageNumber, cardOnPage, sortBy);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.POST)
	public void view(@PathVariable(value="id") String id) {
		service.addView(id);
	}


}