/** Allows saving of photos */

package com.example.taskshare;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.text.format.Time;

/** This is a model class which acts as a container for 
 *  photo files. It stores data about the photo's title, 
 *  photographer, time created and last modified, and 
 *  the user uploading the file.
 *  @author AJ
 */
public class Photo implements Serializable{
	private String title, photographer, user;
	private File photo;
	private Time created, modified;
	
	Photo(String title, String photographer, File photo){
		this.title = title;
		this.photographer = photographer;
		this.photo = photo;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
		this.user = TaskShareApplication.getTaskShare().getUser();
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getPhotographer(){
		return this.photographer;
	}
	
	public File getPhoto(){
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
	
	public void setPhoto(File photo){
		this.photo = photo;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.title);
		out.writeObject(this.photographer);
		out.writeObject(this.photo);
		out.writeObject(this.created);
		out.writeObject(this.modified);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		title = (String)in.readObject();
		photographer = (String) in.readObject();
		photo = (File) in.readObject();
		created = (Time) in.readObject();
		modified = (Time) in.readObject();
	}
	
}
