package georgikoemdzhiev.activeminutes.utils;

import java.text.DecimalFormat;
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

    public static boolean isSleepingHours(Date startSH, Date now, Date stopSH) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        Date midNight = calendar.getTime();

        int midNightHourInt = midNight.getHours();

        int startSHHourInt = startSH.getHours();

        int stopSHHourInt = stopSH.getHours();

        int nowHourInt = now.getHours();

        if (nowHourInt >= startSHHourInt && nowHourInt <= midNightHourInt) {
            return true;
        }

        return nowHourInt < stopSHHourInt;

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

    /***
     * Method that rounds a double variable to 1 decimal point
     * @param value input value
     * @return value formatted with 1 decimal point
     */
    public static double round(double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(value));
    }


}
