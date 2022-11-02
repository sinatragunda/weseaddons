/*Created by Sinatra Gunda
  At 09:54 on 7/24/2020 */

package com.wese.weseaddons.taskmanager.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.taskmanager.pojo.TaskAction;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;
import com.wese.weseaddons.taskmanager.pojo.TaskArrangement;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskArrangementMapper implements RowMapper<TaskArrangement>,MapperInterface {

    public TaskArrangement mapRow(ResultSet resultSet, int row) {

        TaskArrangement taskArrangement = null;

        try {

            taskArrangement = new TaskArrangement();
            taskArrangement.setId(resultSet.getLong(1));

            long taskManagerId = resultSet.getLong(2);
            taskArrangement.setTaskManager(new TaskManager(taskManagerId));

            long taskJobId = resultSet.getLong(3);
            taskArrangement.setTaskJob(new TaskJob(taskJobId));

            long taskActionId = resultSet.getLong(4);
            taskArrangement.setTaskAction(new TaskAction(taskActionId));
        }
        catch (SQLException sql) {

        }

        return taskArrangement;

    }
}