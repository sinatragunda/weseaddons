package com.wese.weseaddons.taskmanager.helper;

import com.wese.weseaddons.taskmanager.pojo.TaskArrangement;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;

import java.util.Comparator;

public class TaskArrangmentComparator implements Comparator<TaskArrangement> {


    @Override
    public int compare(TaskArrangement tl ,TaskArrangement tr){

        if(tl ==null || tr==null){
            return -1 ;
        }

        TaskManager left = tl.getTaskManager();
        TaskManager right = tr.getTaskManager();

        if(left.getId() < right.getId()){
            return 0 ;
        }
        else{
            if(left.getId()==right.getId()){
                return 1 ;
            }
        }
        return -1 ;
    }
}
