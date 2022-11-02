package com.wese.weseaddons.taskmanager.dao;

import com.wese.weseaddons.errors.springexceptions.CreatingDataException;
import com.wese.weseaddons.helper.ListHelper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.taskmanager.pojo.TaskAction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TaskActionDAO {

    private String tenantIdentifier;
    private JdbcTemplate jdbcTemplate ;

    public TaskActionDAO(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
    }


    public List<TaskAction> findAll(){

        String sql = "SELECT * FROM m_task_action ";

        List<TaskAction> taskActionList = JdbcTemplateInit.template(new TaskAction() ,jdbcTemplate ,sql ,false ,null);

        return taskActionList ;

    }
    

    public TaskAction findWhere(long id){


        String sql = "SELECT * FROM m_task_action WHERE id =? ";

        Object args[] = new Object[]{
                id
        };

        List<TaskAction> taskActionList = JdbcTemplateInit.template(new TaskAction() ,jdbcTemplate ,sql ,true ,args);

        return ListHelper.get(taskActionList ,0);

    }

    public TaskAction find(long id){

        String sql = "SELECT * FROM m_task_action WHERE id =? ";
        Object args[] = new Object[]{
                id
        };

        List<TaskAction> taskActionList = JdbcTemplateInit.template(new TaskAction() ,jdbcTemplate ,sql ,true ,args);
        return ListHelper.get(taskActionList ,0);

    }



    public Long create(TaskAction taskAction){

        String sql ="insert into m_task_action(name ,description ,duration_type ,task_type ,duration) values(? ,?,?,?,?)";
        Long id = new Long(0) ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,taskAction.getName());
                    statement.setString(2 ,taskAction.getDescription());
                    statement.setInt(3 ,taskAction.getDurationType().ordinal());
                    statement.setInt(4 ,taskAction.getTaskType().ordinal());
                    statement.setLong(5 ,taskAction.getDuration());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw new CreatingDataException("Task Action");
        }

        return id ;
    }

    public boolean edit(TaskAction taskAction){

        String sql ="UPDATE m_task_action SET name = ? ,description= ? ,duration=? ,duration_type = ? ,task_type = ? WHERE id = ?";

        Object args[] = new Object[]{
                taskAction.getName(),
                taskAction.getDescription(),
                taskAction.getDuration(),
                taskAction.getDurationType().ordinal(),
                taskAction.getTaskType().ordinal(),
                taskAction.getId()
        };

        int rows = jdbcTemplate.update(sql ,args);

        if(rows > 0){
            return true ;
        }

        return false ;
    }
}
