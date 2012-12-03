package com.example.taskshare;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.text.format.Time;

/** This is a model class which acts as a container for 
 *  video files. It stores data about the video's title, 
 *  a text description which could include actors, directors,
 *  or other info, as well as time created and last modified,  
 *  and the user who uploaded the file.
 *  @author AJ
 */
public class Video implements Serializable{

	private String title, description, user;
	private File video;
	private Time created, modified;

	Video(String title, String description, File video){
		this.title = title;
		this.description = description;
		this.video = video;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
		this.user = TaskShareApplication.getTaskShare().getUser();
	}

	public String getTitle(){
		return this.title;
	}

	public String getDescription(){
		return this.description;
	}

	public File getVideo(){
		return this.video;
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

	public void setDescription(String description){
		this.description = description;
		this.modified = new Time(Time.getCurrentTimezone());
	}

	public void setVideo(File video){
		this.video = video;
		this.modified = new Time(Time.getCurrentTimezone());
	}

	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.title);
		out.writeObject(this.description);
		out.writeObject(this.video);
		out.writeObject(this.created);
		out.writeObject(this.modified);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		title = (String)in.readObject();
		description = (String) in.readObject();
		video = (File) in.readObject();
		created = (Time) in.readObject();
		modified = (Time) in.readObject();
	}
}