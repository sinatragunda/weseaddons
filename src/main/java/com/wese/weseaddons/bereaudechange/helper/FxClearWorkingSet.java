package com.wese.weseaddons.bereaudechange.helper;


import com.wese.weseaddons.pojo.AppUser ;
import com.wese.weseaddons.bereaudechange.dao.FxHistoryDAO;
import com.wese.weseaddons.bereaudechange.enumerations.AUDIT_TRAIL;
import com.wese.weseaddons.bereaudechange.pojo.FxHistory;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class FxClearWorkingSet{

	private JdbcTemplate jdbcTemplate ;
	private String prefix = "m_fx_";
	private String tenant ;
	private boolean exceptionThrown = false ;

	private Consumer<String> deleteConsumer = (e)->{

		String sql = String.format("DELETE FROM %s%s",prefix ,e);
		try{

			jdbcTemplate.update(sql);
		}

		catch(Exception a){
			exceptionThrown = true;
		}

	};


	public FxClearWorkingSet(String s){
		this.tenant = s; 
	}

	public boolean clear(long authorizerId){

		jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
		List<String> deleteList = Arrays.asList(Constants.bdxDatabaseList);

		deleteList.stream().forEach(deleteConsumer);

		if(exceptionThrown){
			return false ;
		}

		FxHistoryDAO fxHistoryDAO = new FxHistoryDAO(tenant);
		FxHistory fxHistory = new FxHistory(new AppUser(authorizerId) , AUDIT_TRAIL.CLEAR_WORKING_SET ,0);
		fxHistoryDAO.create(fxHistory);

		return true ;
	
	}
}