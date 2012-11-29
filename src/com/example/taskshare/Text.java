/** Text class, allows text to have a title, a author, a date for both 
 * creation and modification, and the text to be added to the task
 * consists mainly of getters and setters making the building of a text 
 * object simpler */

package com.example.taskshare;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import android.text.format.Time;

public class Text implements Serializable{
	private String title, author, text, user;
	private Time created, modified;
	
	Text(String title, String author, String text){
		this.title = title;
		this.author = author;
		this.text = text;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
		this.user = TaskShareApplication.getTaskShare().getUser();
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
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.title);
		out.writeObject(this.author);
		out.writeObject(this.text);
		out.writeObject(this.created);
		out.writeObject(this.modified);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		title = (String)in.readObject();
		author = (String) in.readObject();
		text = (String) in.readObject();
		created = (Time) in.readObject();
		modified = (Time) in.readObject();
	}

}
