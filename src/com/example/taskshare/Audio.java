package com.example.taskshare;

import android.media.AudioTrack;
import android.text.format.Time;

public class Audio {
	/** Allows saving of audio */

	private String title, artist;
	private AudioTrack audio;
	private Time created, modified;

	Audio(String title, String artist, AudioTrack audio){
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

	public AudioTrack getAudio(){
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

	public void setAudio(AudioTrack audio){
		this.audio = audio;
		this.modified = new Time(Time.getCurrentTimezone());
	}

}