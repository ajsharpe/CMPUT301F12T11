package com.example.taskshare;

import java.util.ArrayList;
import android.text.format.Time;

public class Task<T>{
	private String name, description;
	private Time created, modified;
	private Integer authorID;
	private Boolean shared, storedOnline;
	private ArrayList<T> fulfillment;
	
	Task(String name, String description, Integer authorID, 
			Time now, Boolean shared, Boolean storedOnline){
		this.name = name;
		this.description = description;
		this.authorID = authorID;
		this.created = now;
		this.modified = now;
		this.shared = shared;
		this.storedOnline = storedOnline;
		this.fulfillment = new ArrayList<T>();
	}
	
	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public Integer getAuthorID(){
		return this.authorID;
	}
	
	public Time getDateCreated(){
		return this.created;
	}
	
	public Time getDateModified(){
		return this.modified;
	}
	
	public Boolean getPrivacy(){
		return this.shared;
	}
	
	public void setPrivacy(Boolean shared){
		this.shared = shared;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public Boolean isStoredOnline(){
		return this.storedOnline;
	}
	
	public void toggleOnline(Boolean online){
		this.storedOnline = online;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public void updateFulfillment(ArrayList<T> newFulfillment){
		for (T item : newFulfillment){
			if (! this.fulfillment.contains((T) item)){
				this.fulfillment.add(item);
			}
		}
	}
}
