package com.nesterenya.modal;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.BasicDBObject;

//@Document(collection="castles")
@Entity("castles")
public class Castle {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;
	
    public Castle() {}
    public Castle(String position, String title) {
    	this.position=position;
    	this.title = title;
    }
    
    public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String position;
    /*@Reference
    private Employee manager;
    @Reference
    private List<Employee> directReports;*/
    
    //@Property("wage")
    private String title;
}
