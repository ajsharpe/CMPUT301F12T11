package com.example.taskshare;

import android.text.format.Time;

public class Text {
	private String title, author, text;
	private Time created, modified;
	
	Text(String title, String author, String text, Time now){
		this.title = title;
		this.author = author;
		this.text = text;
		this.created = now;
		this.modified = now;
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
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public void setAuthor(String author){
		this.author = author;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	public void setText(String text){
		this.text = text;
		this.modified = new Time(Time.getCurrentTimezone());
	}

}
