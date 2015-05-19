package com.nesterenya.modal;

public class Place {
	private final long id;
    private final String position;
    private final String title;

    public Place(long id, String position, String title) {
        this.id = id;
        this.position = position;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }
    
    public String getTitle() {
    	return title;
    }
}
