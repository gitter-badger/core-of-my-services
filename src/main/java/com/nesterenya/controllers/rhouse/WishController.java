package com.nesterenya.controllers.rhouse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nesterenya.modal.Wish;
import com.nesterenya.services.WishService;

@RestController
@RequestMapping("/wish")
public class WishController {
	
	@Autowired
	WishService service;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Wish> result() {
		return service.getAll();
	}
	
	//ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	//@ExceptionHandler(UnknownMatchException.class)
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Wish put(@RequestBody Wish wish) {
		
		if(wish.getText().trim().equals("")) {
			throw new UnknownMatchException("Empty query string");
		}
		
		wish.setLikes(0);
		return service.add(wish);
	}
	
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public Wish result(@PathVariable(value="id") String id) {
		return service.get(id);
	}
	
	@RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
	public void like(@PathVariable(value="id") String id) {
		service.like(id);
	}
	
	@RequestMapping(value = "/dislike/{id}", method = RequestMethod.POST)
	public void dislike(@PathVariable(value="id") String id) {
		service.dislike(id);
	}
		
}

