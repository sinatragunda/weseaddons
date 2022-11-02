
package com.wese.weseaddons.taskmanager.mapper ;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskNote;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskNoteMapper implements RowMapper<TaskNote> ,MapperInterface {

    public TaskNote mapRow(ResultSet resultSet , int row){

        TaskNote taskNote = null ;
        
        try{
           	
           	taskNote = new TaskNote();
           	taskNote.setId(resultSet.getLong(1));
           	taskNote.setNote(resultSet.getString(2));
           	Long appUserId = resultSet.getLong(3);

           	taskNote.setPostedBy(new AppUser(appUserId));
           	taskNote.setUpdatedDate(resultSet.getLong(4));

           	long id = resultSet.getLong(5);
           	taskNote.setTaskJob(new TaskJob(id));
        
        }

        catch (SQLException sql){
        }

        return taskNote ;
    }
}