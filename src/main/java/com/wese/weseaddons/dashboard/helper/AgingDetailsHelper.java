
package com.wese.weseaddons.dashboard.helper ;

import com.wese.weseaddons.dashboard.enumerations.AGING_DETAILS_ENUM;
import com.wese.weseaddons.dashboard.pojo.AgingDetailsNormalizedData;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgingDetailsHelper{


	public static Map<Integer ,AgingDetailsNormalizedData> normalizeData(List<AgingDetails> agingDetailsList) {

		Map<Integer, AgingDetailsNormalizedData> map = new HashMap<>();

		for(AgingDetails agingDetails : agingDetailsList) {

			int daysInAreas = agingDetails.getDaysInArrears();

			AGING_DETAILS_ENUM agingDetailsEnum = getAgingDetailsCategory(daysInAreas);

			if(agingDetailsEnum==null){
				continue;
			}

			int i = agingDetailsEnum.ordinal() ;
			double principalOverdue = agingDetails.getPrincipalOverdue() ;

			if(map.containsKey((Integer)i)){

				AgingDetailsNormalizedData agingDetailsNormalizedData = map.get(i);
				agingDetailsNormalizedData.addToSum(principalOverdue);
				map.replace(i ,agingDetailsNormalizedData);
				continue;

			}


			AgingDetailsNormalizedData agingDetailsNormalizedData = new AgingDetailsNormalizedData(principalOverdue ,agingDetailsEnum);
			map.put(i ,agingDetailsNormalizedData);

		}

		return map ;


	}


	public static AGING_DETAILS_ENUM getAgingDetailsCategory(int daysInArears){

		AGING_DETAILS_ENUM agingDetailsEnum = null ;

		if(daysInArears < 30){
			agingDetailsEnum = AGING_DETAILS_ENUM.ONE_TO_30 ;
		}

		else if(daysInArears >= 30 && daysInArears < 60){
			agingDetailsEnum = AGING_DETAILS_ENUM.THIRTY_TO_60;
		}

		else if(daysInArears >= 60 && daysInArears < 90){
			agingDetailsEnum = AGING_DETAILS_ENUM.SIXTY_TO_90;
		}

		else if(daysInArears >= 90 && daysInArears < 180){
			agingDetailsEnum = AGING_DETAILS_ENUM.NINETY_TO_180;
		}

		else if(daysInArears >= 180 && daysInArears < 360){
			agingDetailsEnum = AGING_DETAILS_ENUM.ONE_80_TO_360;
		}

		else if (daysInArears >= 360){
			agingDetailsEnum = AGING_DETAILS_ENUM.YEAR;
		}

		return agingDetailsEnum ;

	}


}
