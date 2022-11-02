/*Created by Sinatra Gunda
  At 08:44 on 7/24/2020 */

package com.wese.weseaddons.taskmanager.dao;

import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.errors.springexceptions.AppUserNotFound;
import com.wese.weseaddons.errors.springexceptions.CreatingDataException;
import com.wese.weseaddons.helper.ListHelper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.networkrequests.AppUserRequest;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.pojo.UserRole;
import com.wese.weseaddons.taskmanager.actions.NotificationAction;
import com.wese.weseaddons.taskmanager.body.NotificationRequestBody;
import com.wese.weseaddons.taskmanager.pojo.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import com.wese.weseaddons.taskmanager.enumerations.STATE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Consumer;

public class TaskArrangementDAO {


    private String tenantIdentifier ;
    private JdbcTemplate jdbcTemplate ;

    private Consumer<TaskArrangement> consumer = (e)->{

        TaskManagerDAO taskManagerDAO = new TaskManagerDAO(tenantIdentifier);
        TaskManager taskManager = taskManagerDAO.find(e.getTaskManager().getId());

        e.setTaskManager(taskManager);

        TaskActionDAO taskActionDAO = new TaskActionDAO(tenantIdentifier);
        TaskAction taskAction = taskActionDAO.find(e.getTaskAction().getId());

        e.setTaskAction(taskAction);

        TaskJobDAO taskJobDAO = new TaskJobDAO(tenantIdentifier);
        TaskJob taskJob = taskJobDAO.find(e.getTaskJob().getId());
        e.setTaskJob(taskJob);

    };

    public TaskArrangementDAO(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
    }

    public List<TaskArrangement> findWhereUser(long userId){

        boolean hasArgs = true ;
        String sql = "SELECT * FROM m_task_arrangement mta INNER JOIN m_task_manager mtm where mtm.assigned_to =? OR mtm_assinged_by = ?";

        AppUserRequest appUserRequest = new AppUserRequest(tenantIdentifier);
        AppUser appUser = appUserRequest.getAppUser(userId);

        if(appUser==null){
            throw new AppUserNotFound(userId);
        }

        List<UserRole> userRoleList = appUser.getSelectedRolesList();

        for(UserRole userRole : userRoleList){

            if(userRole.getName().equalsIgnoreCase("super user")){
                sql = "SELECT * FROM m_task_arrangement";
                hasArgs = false ;
            }
        }

        Object args[] = new Object[]{
                userId,
                userId
        };

        List<TaskArrangement> taskArrangementList = JdbcTemplateInit.template(new TaskArrangement() ,jdbcTemplate ,sql ,hasArgs,args);
        taskArrangementList.stream().forEach(consumer);
        return taskArrangementList;


    }

    public TaskArrangement find(long id){

        String sql = "SELECT * FROM m_task_arrangement where id=?";

        Object args[] = new Object[]{
                id
        };

        List<TaskArrangement> taskArrangementList = JdbcTemplateInit.template(new TaskArrangement() ,jdbcTemplate ,sql ,true,args);
        taskArrangementList.stream().forEach(consumer);
        return ListHelper.get(taskArrangementList,0) ;

    }

    public Long create(TaskArrangement taskArrangement){

        TaskActionDAO taskActionDAO = new TaskActionDAO(tenantIdentifier);
        TaskAction taskAction = taskActionDAO.find(taskArrangement.getTaskAction().getId());

        TaskManagerDAO taskManagerDAO = new TaskManagerDAO(tenantIdentifier);
        TaskManager taskManager = taskArrangement.getTaskManager();
        taskManager.setTaskAction(taskAction); 


        Long taskManagerId = taskManagerDAO.create(taskManager);

        TaskJob taskJob = taskArrangement.getTaskJob();
        taskJob.setTaskManager(taskManager);
        taskJob.setState(STATE.ASSIGNED);

        TaskJobDAO taskJobDAO = new TaskJobDAO(tenantIdentifier);
        Long taskJobId = taskJobDAO.create(taskJob);

        Long taskActionId = taskArrangement.getTaskAction().getId() ;

        String sql ="insert into m_task_arrangement(task_manager_id ,task_job_id,task_action_id) values(? ,?,?)";

        Long id = new Long(0) ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,taskManagerId);
                    statement.setLong(2 ,taskJobId);
                    statement.setLong(3 ,taskActionId);
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw new CreatingDataException("Task Arrangment");
        }

        Runnable runnable = createNotification(id ,taskManager.getAssignedBy() ,true);
        Runnable runnable1 = createNotification(id ,taskManager.getAssignedTo() ,false);

        OffshoreThread.run(runnable);
        OffshoreThread.run(runnable1);

        return id ;
    }

    public Runnable createNotification(long taskArrangmentId ,long userId ,boolean isAssigner){

        Runnable runnable = ()->{

            NotificationRequestBody notificationRequestBody = new NotificationRequestBody();
            notificationRequestBody.setTenantIdentifier(tenantIdentifier);
            notificationRequestBody.setUserId(userId);
            notificationRequestBody.setCode("Assigned");

            if(isAssigner){

                notificationRequestBody.setCode("Manage");
            }

            notificationRequestBody.setObjectIdentifier(taskArrangmentId);
            NotificationAction notificationAction = new NotificationAction(notificationRequestBody);
            notificationAction.createNotification();
        };

        return runnable ;
    }
}
