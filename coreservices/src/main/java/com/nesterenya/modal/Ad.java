package com.nesterenya.modal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Ad {
	
	@Id
	private String id;
	private String address;
	private String cost;
	private Date date;
	private int roomCount;
	private String description;
	private String contacts;
	private String source;
	private List<String> images;
	
	{
		images = new ArrayList<String>();
	}
	
	public Ad() {
		this.id = ObjectId.get().toHexString();
	}	
	
	public Ad(String address, String cost, Date date, int roomCount,
			String description, String contacts) {
		super();
		this.id = ObjectId.get().toHexString();
		this.address = address;
		this.cost = cost;
		this.date = date;
		this.roomCount = roomCount;
		this.description = description;
		this.contacts = contacts;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getRoomCount() {
		return roomCount;
	}
	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
	
}
