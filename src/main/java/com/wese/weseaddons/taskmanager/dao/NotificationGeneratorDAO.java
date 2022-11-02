package com.wese.weseaddons.taskmanager.dao;

import com.wese.weseaddons.taskmanager.body.NotificationRequestBody;
import com.wese.weseaddons.jdbc.JdbcConnectionProperties;
import com.wese.weseaddons.jdbc.JdbcInit;
import com.wese.weseaddons.jdbc.JdbcService;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.taskmanager.pojo.NotificationGenerator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class NotificationGeneratorDAO implements JdbcService {

	private long userId ;
	private String tenantIdentifier ;
	private JdbcInit jdbcInit ;
	private JdbcTemplate jdbcTemplate ;
	private NotificationGenerator notificationGenerator ;
	private JdbcConnectionProperties jdbcConnectionProperties ;

	public NotificationGeneratorDAO(NotificationGenerator notificationGenerator ,NotificationRequestBody notificationRequestBody){
		
	    this.userId = notificationRequestBody.getUserId();
		this.tenantIdentifier = notificationRequestBody.getTenantIdentifier() ;
		this.notificationGenerator = notificationGenerator ;
		
		init();
	}

	@Override
	public void init(){

		this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

	}

	public void createNotification(){
	    
		if(notificationGenerator==null) {
	            return;
		}

		long actor = notificationGenerator.getActor();
		String objectType = notificationGenerator.getObjectType();
		String action = notificationGenerator.getAction();
		boolean isSystemGenerated = notificationGenerator.isSystemGenerated();
		String notificationEvent = notificationGenerator.getNotificationContent();
		long objectIdentifer =notificationGenerator.getObjectIdentifier();

		String createdAt = notificationGenerator.getCreatedAt();

		String sql = "INSERT INTO notification_generator(object_type ,object_identifier ,action ,actor ,is_system_generated ,notification_content ,created_at) values(? ,?,?,?,?,?,?)";

		if(jdbcTemplate==null) {

		    System.out.println("Jdbc template is null");
		}
		
		long notificationId = 0 ;
		
		try {
		    
		    //jdbcTemplate.update(sql ,objectType ,objectIdentifer, action ,actor ,isSystemGenerated ,notificationEvent ,createdAt);

			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					statement.setString(1,objectType);
					statement.setLong(2 ,objectIdentifer);
					statement.setString(3 ,action);
					statement.setLong(4 ,actor);
					statement.setBoolean(5 ,isSystemGenerated);
					statement.setString(6 ,notificationEvent);
					statement.setString(7 ,createdAt);

					return statement;
				}
			}, holder);

			notificationId = holder.getKey().longValue();
		}
		
		catch (Exception e) {
                    // TODO: handle exception
		    e.printStackTrace();
		    return ; 
                }
		
		
		/// update also another table called notifacation mapper

		String sql1 = "INSERT INTO notification_mapper(notification_id ,user_id ,is_read ,created_at) values(? , ?,?,?)";

		jdbcTemplate.update(sql1 ,notificationId ,userId ,false ,createdAt);

	}


}