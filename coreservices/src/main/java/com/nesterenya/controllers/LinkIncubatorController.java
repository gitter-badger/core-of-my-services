package com.nesterenya.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.nesterenya.controllers.rhouse.ImagesController;
import com.nesterenya.modal.Link;
import com.nesterenya.services.LinkService;

@RestController
@RequestMapping("/links")
public class LinkIncubatorController {

	private Logger log = LoggerFactory.getLogger(LinkIncubatorController.class); 
	String adminHash = "6435c6eba4357dda539f9e71ddf9b2a922a72b10a5d2ab373644b0e1c679ccda";
	
	@Autowired
	LinkService service;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Link> result() {
				
		return service.getAll();
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Link put(@RequestBody Link link) {
		
		HashFunction hash = Hashing.sha256();
		HashCode myhash = hash.hashString(link.getAuthor(), Charsets.UTF_8);
		if(adminHash.equals(myhash.toString())) {
			link.setAuthor("nesterione");
		} else {
			if( link.getAuthor()!=null &&( link.getAuthor().contains("nesterione") || link.getAuthor().contains("esterenya") )) {
				link.setAuthor("not defined");
			} 
		}
		
		link.setDislikes(0);
		link.setLikes(0);
		return service.add(link);
	}

	@RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
	public void like(@PathVariable(value = "id") String id) {
		service.like(id);
	}

	@RequestMapping(value = "/dislike/{id}", method = RequestMethod.POST)
	public void dislike(@PathVariable(value = "id") String id) {
		service.dislike(id);
	}
}