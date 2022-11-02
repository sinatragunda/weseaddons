
package com.wese.weseaddons.taskmanager.mapper ;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskManagerMapper implements RowMapper<TaskManager> ,MapperInterface {

    public TaskManager mapRow(ResultSet resultSet , int row){

        TaskManager taskManager = null ;
        
        try{
           	
           	taskManager = new TaskManager();
	        taskManager.setId(resultSet.getLong("id"));
	        taskManager.setStartDate(resultSet.getLong("start_date"));
	        taskManager.setAssignedTo(resultSet.getLong("assigned_to"));
            taskManager.setAssignedByName(resultSet.getString("assigned_by_name"));
            taskManager.setAssignedBy(resultSet.getLong( "assigned_by"));
            taskManager.setAssignedToName(resultSet.getString("assigned_to_name"));
            taskManager.setDueDate(resultSet.getLong("due_date"));
        }

        catch (SQLException sql){
        }

        return taskManager ;
    }
}