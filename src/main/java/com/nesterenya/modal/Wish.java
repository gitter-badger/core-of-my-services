package com.nesterenya.modal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nesterenya.helpers.ObjectIdDeserializer;
import com.nesterenya.helpers.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("wishes")
public class Wish {

	@JsonDeserialize(using = ObjectIdDeserializer.class)
	@JsonSerialize(using = ObjectIdSerializer.class)
	@Id
	private ObjectId id;
	private String text;
	private int likes;
	private int dislikes;
	private Date date;
	
	public Wish() {
		this.setLikes(0);
		this.setDislikes(0);
		this.setDate( new Date() );
	}
	
	public Wish(String text) {
		this.setText(text);
		this.setLikes(0);
		this.setDislikes(0);
		this.setDate( new Date() );
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

	public ObjectId getId() {
		return id;
	}

	@JsonIgnore
	public String getIdHex() {return  id.toHexString(); }

	public void setId(String id) {
		this.id = new ObjectId(id);
	}

	public void setId(ObjectId id) {this.id = id;}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
}
