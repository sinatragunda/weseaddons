
package com.wese.weseaddons.taskmanager.mapper ;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.taskmanager.enumerations.DURATION_TYPE;
import com.wese.weseaddons.taskmanager.enumerations.TASK_TYPE;
import com.wese.weseaddons.taskmanager.pojo.TaskAction;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskActionMapper implements RowMapper<TaskAction> ,MapperInterface {

    public TaskAction mapRow(ResultSet resultSet , int row){

        TaskAction taskAction = null ;
        
        try{
           	
           	taskAction = new TaskAction();
           	taskAction.setId(resultSet.getLong(1));
           	taskAction.setName(resultSet.getString(2));
           	taskAction.setDescription(resultSet.getString(3));

           	int durationType = resultSet.getInt(4);
           	taskAction.setDurationType(DURATION_TYPE.fromInt(durationType));

           	int taskType = resultSet.getInt(5);
           	taskAction.setTaskType(TASK_TYPE.fromInt(taskType));

           	taskAction.setDuration(resultSet.getLong(6));
        
        }

        catch (SQLException sql){
        }

        return taskAction ;
    }
}