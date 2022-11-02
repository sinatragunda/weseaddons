/*
	Added 01/02/2021


*/

package  com.wese.weseaddons.sqlquerries.helper ;


import com.wese.weseaddons.helper.SessionIDGenerator;
import com.wese.weseaddons.utility.CompletableFutureEx;
import com.wese.weseaddons.utility.WeseSessionObject;

import java.util.concurrent.Callable ;
import java.util.concurrent.Future ;
import java.util.concurrent.Executors ;
import java.util.concurrent.ExecutorService ;



public class BulkReportTask implements WeseSessionObject {
	
	private String sessionId ;
	private Future future = null;

	public BulkReportTask(){
		this.sessionId = SessionIDGenerator.sessionId();
	}

	public void call(Callable callable){
		ExecutorService executor = Executors.newSingleThreadExecutor();
		this.future = executor.submit(callable);
		CompletableFutureEx.getInstance().add(sessionId ,future);
	} 

	@Override
	public String getSessionId(){
		return this.sessionId ;
	}

	public Future getFuture(){
		return this.future ;
	}

}