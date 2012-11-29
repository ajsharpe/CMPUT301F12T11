package com.example.taskshare;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.media.AudioTrack;
import android.text.format.Time;

public class Audio implements Serializable{
	/** Allows saving of audio */

	private String title, artist;
	private File audio;
	private Time created, modified;

	Audio(String title, String artist, File audio){
		this.title = title;
		this.artist = artist;
		this.audio = audio;
		this.created = new Time(Time.getCurrentTimezone());
		this.created.setToNow();
		this.modified = new Time(Time.getCurrentTimezone());
		this.modified.setToNow();
	}

	public String getTitle(){
		return this.title;
	}

	public String getArtist(){
		return this.artist;
	}

	public File getAudio(){
		return this.audio;
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

	public void setArtist(String artist){
		this.artist = artist;
		this.modified = new Time(Time.getCurrentTimezone());
	}

	public void setAudio(File audio){
		this.audio = audio;
		this.modified = new Time(Time.getCurrentTimezone());
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.title);
		out.writeObject(this.artist);
		out.writeObject(this.audio);
		out.writeObject(this.created);
		out.writeObject(this.modified);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		title = (String)in.readObject();
		artist = (String) in.readObject();
		audio = (File) in.readObject();
		created = (Time) in.readObject();
		modified = (Time) in.readObject();
	}
}