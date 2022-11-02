
package com.wese.weseaddons.dashboard.helper ;


import com.wese.weseaddons.enumerations.DURATION_MARKER;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.weselicense.helper.DateDifferenceComparator;


import java.util.List;
import  java.util.Date ;

public class ClientDashboardHelper{


	private ClientRequest clientRequest ;


	public static int[] clientStats(String tenantIdentifier){

		int indexed[] = {0 ,0,0,0};

		ClientRequest clientRequest = new ClientRequest(tenantIdentifier);
		Date date = TimeHelper.date();
		List<Client> clientList = clientRequest.getClientList();
		DateDifferenceComparator dateDifferenceComparator = new DateDifferenceComparator();



		for(Client client : clientList) {

			Date activationDate = client.getActivationDate();
			long days = dateDifferenceComparator.differenceInDays(date,activationDate);

			if (days > 0 && days < 1){
				indexed[DURATION_MARKER.DAY.ordinal()] = indexed[DURATION_MARKER.DAY.ordinal()]+1;
			}
			if (days > 0 && days < 7) {
				indexed[DURATION_MARKER.WEEK.ordinal()] = indexed[DURATION_MARKER.WEEK.ordinal()]+1;
			}

			if (days > 7 && days < 30) {
				indexed[DURATION_MARKER.MONTH.ordinal()] = indexed[DURATION_MARKER.MONTH.ordinal()]+1;
			}

			if (days > 30 && days < 365){
				indexed[DURATION_MARKER.YEAR.ordinal()] = indexed[DURATION_MARKER.YEAR.ordinal()]+1;
			}
	    }

		return indexed ;

	}

}