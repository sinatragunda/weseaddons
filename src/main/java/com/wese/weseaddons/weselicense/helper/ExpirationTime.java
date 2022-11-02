package com.wese.weseaddons.weselicense.helper;

import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.weselicense.dao.DaoSession;
import com.wese.weseaddons.weselicense.dao.LicenseDurationDao;
import com.wese.weseaddons.weselicense.pojo.LicenseDuration;
import com.wese.weseaddons.weselicense.pojo.Tenant;

import java.time.*;
import java.util.Date;
import java.util.List;

public class ExpirationTime {

    private Date dateNow ;
    private ZoneId zoneId ;
    private Tenant tenant ;
    private DaoSession daoSession ;

    public ExpirationTime(Tenant tenant,DaoSession daoSession){

        zoneId = ZoneId.of("Africa/Harare");
        dateNow = getDateNow();
        this.tenant = tenant ;
        this.daoSession = daoSession ;

    }

    public Date getDateNow(){

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        Date date = fromLocalDate(localDate);
        return date ;

    }

    public Date fromLocalDate(LocalDate localDate){

        Instant instant = localDate.atStartOfDay(zoneId).toInstant();
        return Date.from(instant);
    
    }

    public long getCurrentLicenseDaysToExpiration(){

        LicenseDurationDao licenseDurationDao = new LicenseDurationDao(daoSession);
        List<LicenseDuration> licenseDurationList = licenseDurationDao.findAllWithCustomCriteria(tenant.getId());

        if(licenseDurationList.isEmpty()){

            return -1 ;
        }

        Date dateNow = getDateNow();
        LicenseDuration licenseDuration = licenseDurationList.get(0);
        long gracePeriod = tenant.getGracePeriod();

        Date expiryDate = TimeHelper.timestampToDate(licenseDuration.getExpiryDate());

        ZonedDateTime zonedExpiryDateTime = expiryDate.toInstant().atZone(zoneId);
        ZonedDateTime zonedDueDate = zonedExpiryDateTime.plusDays(gracePeriod);

        Date futureDate = Date.from(zonedDueDate.toInstant());


        DateDifferenceComparator dateDifferenceComparator = new DateDifferenceComparator();
        long daysToExpiry = dateDifferenceComparator.differenceInDays(futureDate ,dateNow);
        return daysToExpiry ;

    }

}
