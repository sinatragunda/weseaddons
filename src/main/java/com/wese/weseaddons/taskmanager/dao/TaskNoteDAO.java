package com.wese.weseaddons.taskmanager.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.helper.EmailNotification;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.networkrequests.AppUserRequest;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.pojo.Email;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskNote;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Consumer;

public class TaskNoteDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    private Consumer<TaskNote> consumer = (e)->{

        TaskJobDAO taskJobDAO = new TaskJobDAO(tenant);
        String valueId = String.valueOf(e.getTaskJob().getId());
        TaskJob job = taskJobDAO.findWhere("id" ,valueId);
        if(job!=null){
            e.setTaskJob(job);
        }

        AppUserRequest appUserRequest = new AppUserRequest(tenant);
        AppUser appUser = appUserRequest.getAppUser(e.getPostedBy().getId());
        if(appUser!=null){
            e.setPostedBy(appUser);
        }
    };


    public TaskNoteDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<TaskNote> findWhereTaskJob(long taskJobId){

        String sql = String.format("SELECT * FROM m_task_note WHERE task_job_id = %d" ,taskJobId);

        List<TaskNote> taskNoteList = JdbcTemplateInit.template(new TaskNote() ,jdbcTemplate ,sql ,false ,null);

        taskNoteList.stream().forEach(consumer);

        return taskNoteList ;

    }


    public TaskNote find(long id){

        String sql = "SELECT * FROM m_task_note where id=?";

        Object args[] = new Object[]{
                id
        };

        List<TaskNote> taskNoteList = JdbcTemplateInit.template(new TaskNote() ,jdbcTemplate ,sql ,true,args);

        taskNoteList.stream().forEach(consumer);

        return taskNoteList.get(0) ;

    }

    public ObjectNode edit(TaskNote taskNote){

        if(taskNote==null){
            return Helper.statusNodes(false);
        }

        String sql = "update m_task_note set note = ? ,updated_date= ?where id = ?";
        Object args[]= new Object[]{
                taskNote.getNote(),
                taskNote.getUpdatedDate(),
                taskNote.getId()
        };

        int rows = jdbcTemplate.update(sql ,args);

        if(rows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);
    }


    public Long create(TaskNote taskNote){

        String sql ="insert into m_task_note(note ,posted_by,updated_date,task_job_id) values(? ,?,? ,?)";

        Long id = new Long(0) ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,taskNote.getNote());
                    statement.setLong(2 ,taskNote.getPostedBy().getId());
                    statement.setLong(3 , TimeHelper.epochNow());
                    statement.setLong(4 ,taskNote.getTaskJob().getId());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

            //taskNote.getTaskJob().getTaskManager().

            //updated task note here

            ////created task note or updated how will we know ?

            emailNotification(taskNote);

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return id ;

    }

    public void emailNotification(TaskNote taskNote){

        long taskJobId = taskNote.getTaskJob().getId();

        TaskJob taskJob = TaskJobDAO.getInstance(tenant).find(taskJobId);

        long sentTo = taskJob.getTaskManager().getAssignedTo();
        long sentBy = taskJob.getTaskManager().getAssignedBy();

        String subject = String.format("Note added to task %d",taskJob.getTaskManager().getId());

        Email email = new Email(subject,taskNote.getNote() ,"" ,"");

        Runnable runnable = ()->{

            EmailNotification.sendWeseAppUserEmail(email ,new AppUser(sentTo) ,tenant);
            EmailNotification.sendWeseAppUserEmail(email ,new AppUser(sentBy),tenant);
        };

        OffshoreThread.run(runnable);

    }
}
