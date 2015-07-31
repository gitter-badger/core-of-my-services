package com.nesterenya.modal;

import java.util.Date;


public class Comment {
	//@Id
	private String user;
	private String text;
	private Date date;
	
	public Comment() {
		
	}
	
	public Comment(String user, String text, Date date) {
		super();
		this.user = user;
		this.text = text;
		this.date = date;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Comment [user=" + user + ", text=" + text
				+ ", date=" + date + "]";
	}
}
