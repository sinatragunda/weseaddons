package com.wese.weseaddons.sqlquerries.helper;

import com.google.common.base.Strings;
import com.wese.weseaddons.helper.FileHelper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import com.wese.weseaddons.sqlquerries.enumerations.WORKING_SET_NAME;
import com.wese.weseaddons.utility.ThreadContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class ClearWorkingSetHelper{


	private JdbcTemplate jdbcTemplate ;

	public static String[] portfolio = {"m_loan","m_appuser_role","m_appuser", "m_calendar", "m_calendar_instance", "m_charge", "m_client", "m_client_identifier", "m_deposit_account", "m_deposit_account_transaction", "m_document", "m_fund", "m_group", "m_group_level", "m_group_client", "m_guarantor", "m_loan_charge", "m_loan_arrears_aging", "m_loan_collateral", "m_loan_officer_assignment_history", "m_loan_repayment_schedule", "m_loan_transaction", "m_note", "m_office", "m_office_transaction", "m_organisation_currency", "m_permission", "m_portfolio_command_source", "m_product_deposit", "m_product_loan", "m_product_loan_charge", "m_role", "m_role_permission", "m_savings_account", "m_savings_account_transaction", "m_savings_product", "m_staff", "ref_loan_transaction_processing_strategy"};
	public static String[] accounting = {"acc_gl_journal_entry","acc_gl_account","acc_gl_closure","acc_product_mapping"};
	public static String[] configuration = {"c_configuration","m_code","m_code_value"};
	

	public boolean clear(int workingSet){

		String tenantIdentifier = ThreadContext.getTenant();

		WORKING_SET_NAME workingSetName = WORKING_SET_NAME.fromInt(workingSet);

		String[] tablesToClear = tablesToClearData(workingSetName);

		if(tablesToClear==null){
			return false ;
		}

		this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);

		//String x = String.format("DELETE FROM information_schema.key_column_usage where CONSTRAINT_NAME = %s",tenantIdentifier);

        //jdbcTemplate.execute(x);


        for(String table : tablesToClear){

		    String sql = deleteSqlQuery(table);
			jdbcTemplate.execute(sql);
		}

		if(workingSetName== WORKING_SET_NAME.ACCOUNTING || workingSetName==WORKING_SET_NAME.ALL){
			barebones();
		}


		return true; 

	}

	public String deleteSqlQuery(String tableName){
		String sql = String.format("DELETE FROM %s",tableName);
		return sql ;
	}

	public String[] tablesToClearData(WORKING_SET_NAME workingSetName){

		switch(workingSetName){
			case ACCOUNTING:
				return accounting ;
			case PORTFOLIO:
				return portfolio;
			case CONFIGURATION:
				return configuration;
			case ALL:
				return clearAll();
		}

		return null ;
	}

	public String[] clearAll(){

	    //long way here so bad
		return portfolio ;


	}

	public void barebones(){

		String sql = FileHelper.getInstance().readFileAsResource("SystemConfig.sql",true);
		if(sql!=null){
			jdbcTemplate.execute(sql);
		}
		
	}



}