
package com.wese.weseaddons.taskmanager.mapper ;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.taskmanager.enumerations.STATE;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskJobMapper implements RowMapper<TaskJob> ,MapperInterface{

    public TaskJob mapRow(ResultSet resultSet , int row){

        TaskJob taskJob = null ;
        
        try{
           	
           	taskJob = new TaskJob();
           	taskJob.setId(resultSet.getLong(1));

           	int state = resultSet.getInt(2);
           	taskJob.setState(state);

           	long id = resultSet.getLong(3);
           	taskJob.setTaskManager(new TaskManager(id));

           	taskJob.setAccountToActOnId(resultSet.getLong(4));

           	int accountType = resultSet.getInt(5);
           	taskJob.setAccountType(accountType);
        
        }

        catch (SQLException sql){

        }

        return taskJob ;
    }
}