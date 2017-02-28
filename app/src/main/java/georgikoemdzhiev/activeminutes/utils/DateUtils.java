package georgikoemdzhiev.activeminutes.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Georgi Koemdzhiev on 26/02/2017.
 */

public class DateUtils {

    public static Date truncateDate(Date date) {
        Date tempDate = date;
        Calendar cal = Calendar.getInstance();

        cal.setTime(tempDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        tempDate = cal.getTime();
        return tempDate;
    }

    public static Date getDefaultStartSleepingHours() {
        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH, 0);
//        cal.set(Calendar.DAY_OF_MONTH, 0);
//        cal.set(Calendar.HOUR_OF_DAY, 20);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date getDefaultStopSleepingHours() {
        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH, 0);
//        cal.set(Calendar.DAY_OF_MONTH, 0);
//        cal.set(Calendar.HOUR_OF_DAY, 8);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

}
