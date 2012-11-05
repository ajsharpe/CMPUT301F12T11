package com.example.taskshare;

import android.text.format.Time;

public class TextTask extends Task<Text>{

	TextTask(String name, String description, Integer authorID, Time now,
			Boolean shared, Boolean storedOnline) {
		super(name, description, authorID, now, shared, storedOnline);
		// TODO Auto-generated constructor stub
	}

}
