package georgikoemdzhiev.activeminutes.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static boolean isSleepingHours(Date startSH, Date now, Date stopSH) {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm", Locale.UK);
            Date startTime = parser.parse(startSH.getHours() + ":" + startSH.getMinutes());
            Date endTime = parser.parse(stopSH.getHours() + ":" + stopSH.getMinutes());
            Date nowTime = parser.parse(now.getHours() + ":" + now.getMinutes());

            if (startTime.after(endTime)) {
                return endTime.after(nowTime);
            } else {
                return startTime.before(nowTime) && endTime.after(nowTime);
            }
        } catch (java.text.ParseException e) {
            return false;
        }

    }

    public static Date getDefaultStartSleepingHours() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 21);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }

    public static Date getDefaultStopSleepingHours() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, 1);
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }

    public static int getDifferenceInHours(Date date1, Date date2) {
        int diffInDays = (int) ((date1.getTime() - date2.getTime())
                / (1000 * 60 * 60));

        return Math.abs(diffInDays);
    }


}
