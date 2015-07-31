package com.nesterenya.modal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nesterenya.helpers.ObjectIdDeserializer;
import com.nesterenya.helpers.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("links")
public class Link {

	@JsonDeserialize(using = ObjectIdDeserializer.class)
	@JsonSerialize(using = ObjectIdSerializer.class)
	@Id
	private ObjectId id;
	private int likes;
	private int dislikes;
	private String link;
	private String header;
	private String description;
	private List<String> tags;
	private Date date;
	private String author;
	
	{
		date = new Date();
		tags = new ArrayList<String>();
	}
	
	public ObjectId getId() {
		return id;
	}

	@JsonIgnore
	public String getIdHex() {
		return id.toHexString();
	}

	@JsonIgnore
	public void setIdHex(String id) {
		this.id = new ObjectId(id);
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
