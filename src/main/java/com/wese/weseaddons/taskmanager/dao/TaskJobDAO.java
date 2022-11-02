package com.wese.weseaddons.taskmanager.dao;

import com.wese.weseaddons.helper.ListHelper;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.taskmanager.actions.TaskJobAction;
import com.wese.weseaddons.taskmanager.interfaces.DAOService;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;
import com.wese.weseaddons.taskmanager.pojo.TaskNote;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Consumer;

public class TaskJobDAO implements DAOService {

    private String tenantIdentifier;
    private JdbcTemplate jdbcTemplate ;
    private static TaskJobDAO instance ;

    private Consumer<TaskJob> consumer = (e)->{

        TaskManagerDAO taskManagerDAO = new TaskManagerDAO(tenantIdentifier);
        String valueId = String.valueOf(e.getTaskManager().getId());

        TaskManager taskManager = taskManagerDAO.find(e.getTaskManager().getId());

        if(taskManager!=null){
            e.setTaskManager(taskManager);
        }
    };


    public TaskJobDAO(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
    }

    public static TaskJobDAO getInstance(String tenantIdentifier) {
        if(instance==null){
            instance = new TaskJobDAO(tenantIdentifier);
        }
        return instance;
    }

    public List<TaskJob> findAll(){
        return null ;
    }

    public TaskJob find(long id){

        String sql = "SELECT * FROM m_task_job WHERE id = ?";

        Object args[] = new Object[]{
                id
        };

        List<TaskJob> taskJobList = JdbcTemplateInit.template(new TaskJob() ,jdbcTemplate ,sql ,true ,args);
        taskJobList.stream().forEach(consumer);
        return ListHelper.get(taskJobList ,0);


    }


    public TaskJob findWhere(String columnName ,String value){

        String sql = String.format("SELECT * FROM m_task_job WHERE %s = %s",columnName,value);

        jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

        List<TaskJob> taskJobList = JdbcTemplateInit.template(new TaskJob() ,jdbcTemplate ,sql ,false ,null);

        taskJobList.stream().forEach(consumer);

        return ListHelper.get(taskJobList ,0);

    }

    public Long create(TaskJob taskJob){

        String sql ="insert into m_task_job(state,task_manager_id,account_to_act_on_id,account_type) values(? ,?,? ,?)";
        Long id = new Long(0) ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1,taskJob.getState().ordinal());
                    statement.setLong(2 ,taskJob.getTaskManager().getId());
                    statement.setLong(3 ,taskJob.getAccountToActOnId());
                    statement.setInt(4 ,taskJob.getAccountType().ordinal());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return id ;

    }

    public boolean update(TaskJob taskJob){

        String sql = "UPDATE m_task_job SET state = ? WHERE id= ? ";

        Object args[] = new Object[]{
            taskJob.getState().ordinal(),
            taskJob.getId()
        };

        int rows = jdbcTemplate.update(sql ,args);

        if(rows > 0){
            return true ;
        }

        return false ;
    }

}
