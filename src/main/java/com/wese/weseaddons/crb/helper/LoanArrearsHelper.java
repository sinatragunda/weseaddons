/*Created by Sinatra Gunda
  At 12:50 PM on 3/3/2020 */

package com.wese.weseaddons.crb.helper;

import com.wese.weseaddons.dashboard.helper.LoansDashboardHelper;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;

public class LoanArrearsHelper {


	public static int daysInArreas(long loanId , String tenantIdentifier){

		AgingDetails agingDetails = null ;

		try{

		    agingDetails = LoansDashboardHelper.agingDetailsClient(tenantIdentifier ,loanId).get(0);
	
		}
		catch(NullPointerException n){
			return 0 ;
		}
		
		return agingDetails.getDaysInArrears();
	
	}
    
}
