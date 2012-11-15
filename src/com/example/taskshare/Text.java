/** Text class, allows text to have a title, a author, a date for both 
 * creation and modification, and the text to be added to the task
 * consists mainly of getters and setters making the building of a text 
 * object simpler */

package com.example.taskshare;

import android.text.format.Time;

public class Text {
	private String title, author, text;
	private Time created, modified;
	
	Text(String title, String author, String text){
		this.title = title;
		this.author = author;
		this.text = text;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public String getText(){
		return this.text;
	}
	
	public Time getDateCreated(){
		return this.created;
	}
	
	public Time getDateModified(){
		return this.modified;
	}
	
	public void setTitle(String title){
		this.title = title;
		this.modified.setToNow();
	}
	
	public void setAuthor(String author){
		this.author = author;
		this.modified.setToNow();
	}
	
	public void setText(String text){
		this.text = text;
		this.modified.setToNow();
	}

}
