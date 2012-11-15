/** Allows saving of photos */

package com.example.taskshare;

import android.graphics.Bitmap;
import android.text.format.Time;

public class Photo {
	private String title, photographer;
	private Bitmap photo;
	private Time created, modified;
	
	Photo(String title, String photographer, Bitmap photo){
		this.title = title;
		this.photographer = photographer;
		this.photo = photo;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getPhotographer(){
		return this.photographer;
	}
	
	public Bitmap getPhoto(){
		return this.photo;
	}
	
	public Time getDateCreated(){
		return this.created;
	}
	
	public Time getDateModified(){
		return this.modified;
	}
	
	public void setTitle(String title){
		this.title = title;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public void setPhotographer(String photographer){
		this.photographer = photographer;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public void setPhoto(Bitmap photo){
		this.photo = photo;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
}
