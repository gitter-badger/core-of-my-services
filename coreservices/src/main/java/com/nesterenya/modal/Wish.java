package com.nesterenya.modal;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("wishes")
public class Wish {
	
	@Id
	private String id;
	private String text;
	private int likes;
	
	public Wish() {
		
	}
	
	public Wish(String text) {
		this.setText(text);
		this.setLikes(0);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
