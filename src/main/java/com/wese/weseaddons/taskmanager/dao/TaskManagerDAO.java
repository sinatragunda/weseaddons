package com.wese.weseaddons.taskmanager.dao;

import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.EmailNotification;
import com.wese.weseaddons.helper.ListHelper;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.networkrequests.AppUserRequest;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.helper.DurationHelper;
import com.wese.weseaddons.pojo.Email;
import com.wese.weseaddons.taskmanager.interfaces.DAOService;
import com.wese.weseaddons.taskmanager.interfaces.NullUnchecked;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TaskManagerDAO implements DAOService ,NullUnchecked {

    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;
    private AppUserRequest appUserRequest ;

    private Consumer<TaskManager> consumer = (e)->{

        AppUser appUser = appUserRequest.getAppUser(e.getAssignedBy());
        e.setAssignedByName(appUser.getDisplayName());

        AppUser appUser1 = appUserRequest.getAppUser(e.getAssignedTo());
        e.setAssignedToName(appUser.getDisplayName());

    };

    public TaskManagerDAO(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
        this.appUserRequest = new AppUserRequest(tenantIdentifier);

    }

    public List<TaskManager> findAll(){

        String sql = "SELECT * FROM m_task_manager";
        List<TaskManager> taskManagerList = JdbcTemplateInit.template(new TaskManager(),jdbcTemplate ,sql,false ,null);
        taskManagerList.stream().forEach(consumer);

        return taskManagerList ;
    }

    public TaskManager find(long id){

        String sql = String.format("SELECT * FROM m_task_manager WHERE id = %d",id);
        List<TaskManager> taskManagerList = JdbcTemplateInit.template(new TaskManager(),jdbcTemplate ,sql ,false ,null);
        taskManagerList.stream().forEach(consumer);
        return  ListHelper.get(taskManagerList,0) ;

    }

    public List<TaskManager> findWhere(String columnName ,long id){

        String sql = String.format("SELECT * FROM m_task_manager WHERE %s = ?",columnName);

        Object args[]= new Object[]{
            id
        };

        List<TaskManager> taskManagerList = JdbcTemplateInit.template(new TaskManager(),jdbcTemplate ,sql ,true ,args);
        taskManagerList.stream().forEach(consumer);
        return taskManagerList;

    }

    public long create(TaskManager taskManager){

        long dueOn = DurationHelper.taskDurationCalculator(taskManager,Constants.zimbabweTimeZone);


        System.err.println("---------Due date is "+dueOn);

        taskManager.setDueDate(dueOn);

        String sql ="insert into m_task_manager(start_date ,assigned_to ,assigned_by ,due_date) values(? ,?,? ,?)";
        Long id = new Long(0) ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,taskManager.getStartDate());
                    statement.setLong(2 ,taskManager.getAssignedTo());
                    statement.setLong(3 ,taskManager.getAssignedBy());
                    statement.setLong(4 ,taskManager.getDueDate());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
            emailNotification(taskManager);
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return id ;

    }

    public void emailNotification(TaskManager taskManager){

        AppUser appUser = appUserRequest.getAppUser(taskManager.getAssignedBy());

        String subject = String.format("New task assigned");
        Email email = new Email(subject ,"","" ,"");
        Email sender = new Email("You have assigned a new task" ,"" ,"" ,"");

        Runnable runnable = ()->{

            EmailNotification.sendWeseAppUserEmail(email ,new AppUser(taskManager.getAssignedTo()) ,tenantIdentifier);
            EmailNotification.sendWeseAppUserEmail(sender ,new AppUser(taskManager.getAssignedBy()) ,tenantIdentifier);
        };

        OffshoreThread.run(runnable);

    }

}
