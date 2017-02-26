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

}
