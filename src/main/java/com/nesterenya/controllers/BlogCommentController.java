package com.nesterenya.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nesterenya.modal.Comment;
import com.nesterenya.services.CastleService;
import com.nesterenya.services.CommentService;

@RestController
@RequestMapping("/blog/comments")
public class BlogCommentController {

	@Autowired
	private CommentService service;
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public List<Comment> result(@PathVariable(value="id") String id) {
		// 55a68f793066cfbef2fd729a
		List<Comment> comments = service.get(id);
		Collections.sort(comments,new Comparator<Comment>() {
			public int compare(Comment o1, Comment o2) {
				return -o1.getDate().compareTo(o2.getDate());
			};
		});
		
		
		return comments;
	}
	
	@RequestMapping(value = "/createpost", method = RequestMethod.GET)
	public String create() {
		return service.create();
	}
	
	@RequestMapping(value = "/add/{id}", method = RequestMethod.PUT)
	public void put(@PathVariable(value="id") String id, @RequestBody Comment comment) {
		if(comment.getText()!=null&&comment.getText().trim().equals("")) {
			return;
		}
		
		if(comment.getDate()==null) {
			comment.setDate(new Date());
		}
		
		if(comment.getUser()!=null&&comment.getUser().trim().equals("")) {
			comment.setUser("аноним");
		}
		
		service.push(id, comment);
	}
	
	/*
	@Autowired
	MongoConnectionConfig config;
	
	@RequestMapping(value = "/ff", method = RequestMethod.GET)
	public String avoid() {
		return config.getConnectionURI();
	}
	*/
}
