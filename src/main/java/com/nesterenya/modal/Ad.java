package com.nesterenya.modal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nesterenya.helpers.ObjectIdDeserializer;
import com.nesterenya.helpers.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("ads")
public class Ad {

	@JsonDeserialize(using = ObjectIdDeserializer.class)
	@JsonSerialize(using = ObjectIdSerializer.class)
	@Id
	private ObjectId id;
	private String address;
	private String cost;
	private Date date;
	private int roomCount;
	private String description;
	private String contacts;
	private String source;
	private List<String> images;
	private int views;
	private int costValue;

	{
		images = new ArrayList<String>();
	}

	public Ad() {
		this.id = ObjectId.get();
	}	
	
	public Ad(String address, String cost, Date date, int roomCount,
			String description, String contacts) {
		super();
		this.id = ObjectId.get();
		this.address = address;
		this.cost = cost;
		this.date = date;
		this.roomCount = roomCount;
		this.description = description;
		this.contacts = contacts;
	}
	
	public ObjectId getId() {
		return id;
	}

	@JsonIgnore
	public String getIdHex( ) {return id.toHexString(); }

	@JsonIgnore
	public void setIdHex(String id) {
		this.id = new ObjectId(id);
	}
	public void setId(ObjectId id) {this.id = id;}

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

		Pattern prog = Pattern.compile("^[\\D]+$");
		int costValue = 0;
		if ( prog.matcher(cost).matches() ) {
			costValue = 99999999;
		} else {
			Pattern p = Pattern.compile("[\\d ]+");
			Matcher matcher = p.matcher(cost);
			matcher.find();
			String m = matcher.group();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < m.length(); i++) {
				char ch = m.charAt(i);
				if( Character.isDigit(  ch  )) {
					sb.append(ch);
				}
			}
			costValue = Integer.parseInt(sb.toString());
			// TODO Remove magic number

			if(costValue> 1000000) {
				costValue = costValue/15000;
			}

			setCostValue(costValue);
		}
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

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	@JsonIgnore
	public int getCostValue() {
		return costValue;
	}

	@JsonIgnore
	public void setCostValue(int costValue) {
		System.out.println("check > " + costValue);
		this.costValue = costValue;
	}
}
