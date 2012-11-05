package com.example.taskshare;

import java.util.ArrayList;

public class TaskShare extends FModel<FView> {
	private ArrayList<Task> myTaskList, onlineTaskList;
	
	TaskShare(){
		this.myTaskList = new ArrayList<Task>();
		this.onlineTaskList = null; //will be implemented later
	}
	
	public ArrayList<Task> getMyTaskList(){
		return this.myTaskList;
	}
	
	/* Adds a new task to taskList  *
	 * Returns true if successful,  *
	 * false if duplicate			*/
	public Boolean addMyTask(Task task){
		if (! myTaskList.contains(task)){
			myTaskList.add(task);
			return true;
		}
		return false;
	}
	
	/* Removes a task from  myTaskList  *
	 * Returns true if successful,      *
	 * false if not found			 	*/
	public Boolean removeMyTask(Task task){
		if (myTaskList.contains(task)){
			myTaskList.remove(task);
			return true;
		}
		return false;
	}
	
	//Online stuff
	public ArrayList<Task> getOnlineTaskList(){
		return this.onlineTaskList;
	}
	
	/* Adds a new task to taskList  *
	 * Returns true if successful,  *
	 * false if duplicate			*/
	public Boolean addOnlineTask(Task task){
		if (! onlineTaskList.contains(task)){
			onlineTaskList.add(task);
			return true;
		}
		return false;
	}
	
	/* Removes a task from  onlineTaskList  *
	 * Returns true if successful,      *
	 * false if not found			 	*/
	public Boolean removeOnlineTask(Task task){
		if (onlineTaskList.contains(task)){
			onlineTaskList.remove(task);
			return true;
		}
		return false;
	}
}
