package net.news.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component("dateManager")
@Scope("prototype")
public class DateManager {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd";

    private Date startDate;
    private Date stopDate;


    public DateManager calculate(String dateStr) throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = dateTimeFormat.parse(dateStr);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        startDate = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        stopDate = cal.getTime();
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }
}
