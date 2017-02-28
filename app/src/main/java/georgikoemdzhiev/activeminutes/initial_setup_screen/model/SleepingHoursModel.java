package georgikoemdzhiev.activeminutes.initial_setup_screen.model;

import java.util.Calendar;

import georgikoemdzhiev.activeminutes.data_layer.IAuthDataManager;
import georgikoemdzhiev.activeminutes.data_layer.db.User;
import io.realm.Realm;

/**
 * Created by Georgi Koemdzhiev on 27/02/2017.
 */

public class SleepingHoursModel implements ISleepingHoursModel {
    private IAuthDataManager mAuthDataManager;
    private Realm mRealm;

    public SleepingHoursModel(IAuthDataManager authDataManager, Realm realm) {
        mAuthDataManager = authDataManager;
        mRealm = realm;
    }

    @Override
    public void setSleepingHours(int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        User user = mAuthDataManager.getLoggedInUser();

        mRealm.beginTransaction();
        user.setStartSleepingHours(calendar.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDayEnd);
        calendar.set(Calendar.MINUTE, minuteEnd);

        user.setStopSleepingHours(calendar.getTime());

        mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();
    }
}
