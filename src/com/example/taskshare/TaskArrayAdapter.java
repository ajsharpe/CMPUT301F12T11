package com.example.taskshare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskArrayAdapter extends ArrayAdapter<Task> {
	
	Context context; 
    private static int layoutResourceId = android.R.layout.simple_expandable_list_item_2;    
    ArrayList<Task> taskList = null;
    
	public TaskArrayAdapter(Context context, ArrayList<Task> taskList) {
		super(context, layoutResourceId, taskList);
        this.context = context;
        this.taskList = taskList;
	}
	
	public void setAdpaterList(ArrayList<Task> list){
		this.taskList= list;	
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new TaskHolder();
            holder.title = (TextView)row.findViewById(android.R.id.text1);
            holder.dateAndType = (TextView)row.findViewById(android.R.id.text2);
            
            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }
        
        Task task = taskList.get(position);
        String message = "";
        if (task instanceof PhotoTask) message="Photo Based Task\n";
        if (task instanceof TextTask) message="Text Based Task\n";
        holder.title.setHorizontallyScrolling(true);
        holder.title.setLines(1);
        holder.title.setText(task.getName());
        holder.dateAndType.setTextSize(10);
        holder.dateAndType.setText(message + task.getDateAndTypeFormatted());
        
        return row;
    }
    
    static class TaskHolder
    {
        TextView title;
        TextView dateAndType;
    }
}
