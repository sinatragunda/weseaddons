package com.wese.weseaddons.helper;

import com.wese.weseaddons.taskmanager.enumerations.DURATION_TYPE;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DurationHelper {

    private String locale ;

    public DurationHelper(String locale){
        this.locale = locale;
    }


    public long taskDuration(TaskManager taskManager){

        return taskDurationCalculator(taskManager ,"Africa/Harare");
    }

    public static long taskDurationCalculator(TaskManager taskManager ,String timezone){

        long duration = taskManager.getDueDate() ;

        ZoneId zoneId = TimeHelper.getZoneId(timezone);

        ZonedDateTime zonedDateTime = Instant.ofEpochMilli(taskManager.getStartDate()).atZone(zoneId);
        ZonedDateTime zonedDueDate = null ;

        DURATION_TYPE durationType = taskManager.getTaskAction().getDurationType();

        switch (durationType){

            case HOUR:
                zonedDueDate = zonedDateTime.plusHours(duration) ;
                break ;
            case DAYS:
                zonedDueDate = zonedDateTime.plusDays(duration);
                break;
            case WEEKS:
                zonedDueDate = zonedDateTime.plusWeeks(duration);
                break;

            case MONTHS:
                zonedDueDate = zonedDateTime.plusMonths(duration);
                break;
        }

        return zonedDueDate.toEpochSecond() ;

    }
}
