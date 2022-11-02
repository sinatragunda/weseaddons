//package com.wese.weseaddons.bereaudechange.helper ;
//
//import com.wese.weseaddons.bereaudechange.impl.FxDAO;
//import com.wese.weseaddons.jdbc.JdbcTemplateInit;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DaoHelper<T>{
//
//	private static DaoHelper instance ;
//
//	public static DaoHelper instance(){
//
//		if(instance==null){
//			instance = new DaoHelper<>();
//		}
//
//		return instance ;
//	}
//
//	public DaoHelper(){}
//
//	public <T> List<T> findAll(String tenant){
//
//		T object = new T();
//
//		JdbcTemplate jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
//
//		String table = FxSchema.get(object);
//		String sql = String.format("SELECT * from %s" ,table);
//
//		List<T> listOfObject = jdbcTemplate.query(sql ,new FxDAO());
//
//		if(listOfObject.isEmpty()){
//			return new ArrayList<>();
//		}
//
//		return listOfObject ;
//	}
//}