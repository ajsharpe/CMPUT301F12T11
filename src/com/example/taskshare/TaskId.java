package com.example.taskshare;


public class TaskId {
	private String id;
	private Task<?> content;
	
	public Task<?> getTask() {
		return this.content;
	}

	public void setTask(Task<?> task) {
		this.content = task;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String toString(){
		return id + content.toString();
	}
}
