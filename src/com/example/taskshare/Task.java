/** Task class
 * -getters and setters
 * -allows a list of fulfillments that can be added to*/

package com.example.taskshare;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import android.text.format.Time;

public class Task<T> implements Serializable{
	private String user;
	private String name, description;
	private Time created, modified;
	private Boolean sharedOnline;
	private ArrayList<T> fulfillment;
	private Integer likes;
	private Boolean favourite;

	Task(String name, String description, Boolean sharedOnline){
		this.name = name;
		this.description = description;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
		this.sharedOnline = sharedOnline;
		this.fulfillment = new ArrayList<T>();
		this.likes = 0;
		this.favourite = false;
		this.user = TaskShareApplication.getTaskShare().getUser();
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
		this.modified.setToNow();	
	}

	public void updateFulfillment(ArrayList<T> newFulfillment){
		for (T item : newFulfillment){
			if (! this.fulfillment.contains((T) item)){
				this.fulfillment.add(item);
			}
		}
	}
	public String getDateAndTypeFormatted(){
		return "Modified on " + this.modified.format("%Y/%m/%d %H:%M");
	}

	public Integer getLikes(){
		return this.likes;
	}

	public Boolean getFavourite(){
		return this.favourite;
	}

	public void toggleFavourite(){
		if (this.favourite == true){
			this.favourite = false;
			this.likes--;
		} else {
			this.favourite = true;
			this.likes++;
		}
	}

	public String getUser(){
		return this.user;
	}

	public String toString(){
		return this.name + "\n" + this.modified.format("%Y/%m/%d %H:%M");
	}

	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.user);
		out.writeObject(this.name);
		out.writeObject(this.description);
		Long longCreated = this.created.toMillis(true);
		out.writeObject(longCreated);
		Long longModified = this.modified.toMillis(true);
		out.writeObject(longModified);
		out.writeObject(this.sharedOnline);
		out.writeObject(this.fulfillment);
		out.writeObject(this.likes);
		out.writeObject(this.favourite);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		this.user = (String) in.readObject();
		this.name = (String) in.readObject();
		this.description = (String) in.readObject();
		Long longCreated = (Long) in.readObject();
		this.created = new Time();
		this.created.set(longCreated.longValue());
		Long longModified = (Long) in.readObject();
		this.modified = new Time();
		this.modified.set(longModified.longValue());
		this.sharedOnline = (Boolean) in.readObject();
		this.fulfillment = (ArrayList<T>) in.readObject();
		this.likes = (Integer)  in.readObject();
		this.favourite = (Boolean)  in.readObject();
	}
}
