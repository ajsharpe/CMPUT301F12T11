package com.example.taskshare;

import java.util.ArrayList;
import android.text.format.Time;

public class Task<T>{
	private String name, description;
	private Time created, modified;
	private Integer authorID;
	private Boolean sharedOnline;
	private ArrayList<T> fulfillment;
	
	Task(String name, String description, Integer authorID, Boolean sharedOnline){
		this.name = name;
		this.description = description;
		this.authorID = authorID;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
		this.sharedOnline = sharedOnline;
		this.fulfillment = new ArrayList<T>();
	}
	
	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
		this.modified.setToNow();
	}
	
	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
		this.modified.setToNow();	}
	
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
		return this.sharedOnline;
	}
	
	public void setPrivacy(Boolean sharedOnline){
		this.sharedOnline = sharedOnline;
		this.modified.setToNow();	}
	
	public void updateFulfillment(ArrayList<T> newFulfillment){
		for (T item : newFulfillment){
			if (! this.fulfillment.contains((T) item)){
				this.fulfillment.add(item);
			}
		}
	}
	
	public String toString(){
		return this.name + " - " + this.modified.format("%Y/%m/%d %H:%M");
	}
}
