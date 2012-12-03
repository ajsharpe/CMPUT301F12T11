/**Class that allows the building of a list of tasks
 * -also allows the adding and removing of tasks from a list
 * -only makes offline lists as of now*/

package com.example.taskshare;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class TaskShare implements Serializable{
	private ArrayList<Task> myTaskList, onlineTaskList;
	private String user;

	TaskShare(){
		this.myTaskList = new ArrayList<Task>();
		this.onlineTaskList = new ArrayList<Task>();
	}

	public ArrayList<Task> getMyTaskList(){
		return this.myTaskList;
	}

	public ArrayList<Task> getOnlineList(){
		boolean download = new BuildListOfSharedTasks().execute(onlineTaskList) != null;
		if (download) return this.onlineTaskList;
		else return this.myTaskList;
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

	/* Add a task from  myTaskList, and	*
	 * remove the task previously at 	*
	 * that index.						*
	 * Returns true if successful,      *
	 * false if not found			 	*/
	public void replaceMyTask(Task task, int index){
		myTaskList.add(index, task);
		myTaskList.remove(index+1);
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

	public String getUser(){
		return this.user;
	}

	private void writeObject(ObjectOutputStream out) throws IOException{
		out.writeObject(this.myTaskList);
		out.writeObject(this.user);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		this.myTaskList = (ArrayList<Task>) in.readObject();
		this.user = (String) in.readObject();
	}

	public void saveToFile(){
		String path = "/sdcard/.taskshare/taskshare";
		try {  
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path)); 
			this.writeObject(out);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}

	public void loadFromFile(){
		String path = "/sdcard/.taskshare/taskshare";
		try {  
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path)); 
            this.readObject(in);
            in.close();  
        } 
         catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } 
	}
}
