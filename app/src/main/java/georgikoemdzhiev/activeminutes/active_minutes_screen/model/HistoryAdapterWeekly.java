package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.data_layer.db.Activity;

/**
 * Created by Georgi Koemdzhiev on 07/03/2017.
 */

public class HistoryAdapterWeekly extends
        RecyclerView.Adapter<HistoryAdapterWeekly.HistoryViewHolder> {
    private Context mContext;
    private List<List<Activity>> mData;

    public HistoryAdapterWeekly(Context context, List<List<Activity>> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item_layout, parent, false);

        HistoryViewHolder vh = new HistoryViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        List<Activity> activitiesForWeek = mData.get(position);
        holder.mDate.setText(formatDate(activitiesForWeek));

        holder.mActivePB.setMax(toMinutes(getMaxPaSum(activitiesForWeek)));
        holder.mActivePB.setProgress(toMinutes(getActiveTimeSum(activitiesForWeek)));
        if (isPaGoalReached(activitiesForWeek)) {
            holder.mActivePB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar));
        } else {
            holder.mActivePB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar_goal_not_achieved));
        }

        holder.mStaticPB.setMax(toMinutes(getMaxStaticGoalForWeek(activitiesForWeek)));
        holder.mStaticPB.setProgress(toMinutes(getLongestInacIntervalInWeek(activitiesForWeek)));
        if (isStaticTargetReached(activitiesForWeek)) {
            holder.mStaticPB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar_goal_not_achieved));
        } else {
            holder.mStaticPB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar));
        }

        holder.mPaGoal.setText(String.valueOf(toHours(getUserPaGoalSum(activitiesForWeek))));
        holder.mPaProgress.setText(String.valueOf(toHours(getUserPaGoalProgress(activitiesForWeek))));

        holder.mStaticGoal.setText(String.valueOf(toMinutes(getMaxStaticGoalForWeek(activitiesForWeek))));
        holder.mStaticProgress.setText(String.valueOf(toMinutes(getMaxContInacForWeek(activitiesForWeek))));
    }


    private int getLongestInacIntervalInWeek(List<Activity> activitiesForWeek) {
        int longestInacInterval = 0;
        for (Activity a : activitiesForWeek) {
            if (a.getLongestInactivityInterval() > longestInacInterval) {
                longestInacInterval = a.getLongestInactivityInterval();
            }
        }
        return longestInacInterval;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private int toMinutes(int value) {
        return value / 60;
    }

    private double toHours(double value) {
        return Math.round(((value / 60) / 60) * 100.0) / 100.0;
    }

    private String formatDate(List<Activity> activities) {
        Calendar calendar = Calendar.getInstance(Locale.UK);
        Date date = activities.get(activities.size() - 1).getDate();
        calendar.setTime(date);
        String output = "" + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.UK);

        calendar.setTime(activities.get(0).getDate());

        output += " - " + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.UK);
        return output;
    }

    private int getMaxPaSum(List<Activity> activities) {
        int maxPa = 0;
        for (Activity a : activities) {
            maxPa += a.getUserPaGoal();
        }
        return maxPa;
    }

    private int getActiveTimeSum(List<Activity> activities) {
        int activeTime = 0;
        for (Activity a : activities) {
            activeTime += a.getActiveTime();
        }
        return activeTime;
    }

    // This method returns the max MCI of all activities/days in a week
    private int getUserMCITargetForWeek(List<Activity> activities) {
        int mci = 0;
        for (Activity a : activities) {
            if (a.getUserMaxContInacTarget() > mci) {
                mci = a.getUserMaxContInacTarget();
            }
        }
        return mci;
    }

    private int getUserPaGoalSum(List<Activity> activities) {
        int userPaGoalSummed = 0;
        for (Activity a : activities) {
            userPaGoalSummed += a.getUserPaGoal();
        }
        return userPaGoalSummed;
    }

    // This method returns the MCI from all of the activities in a week
    private int getMaxStaticGoalForWeek(List<Activity> activities) {
        int maxStaticGoal = activities.get(0).getUserMaxContInacTarget();
        for (Activity a : activities) {
            if (a.getUserMaxContInacTarget() > maxStaticGoal) {
                maxStaticGoal = a.getUserMaxContInacTarget();
            }
        }
        return maxStaticGoal;
    }

    // This method returns the longest continuous inactivity for all days in a week
    private int getMaxContInacForWeek(List<Activity> activities) {
        int maxStaticProgress = activities.get(0).getLongestInactivityInterval();
        for (Activity a : activities) {
            if (a.getLongestInactivityInterval() > maxStaticProgress) {
                maxStaticProgress = a.getLongestInactivityInterval();
            }
        }
        return maxStaticProgress;
    }

    private int getUserPaGoalProgress(List<Activity> activitiesForWeek) {
        int activeTime = 0;
        for (Activity a : activitiesForWeek) {
            activeTime += a.getActiveTime();
        }
        return activeTime;
    }

    private boolean isPaGoalReached(List<Activity> activities) {
        return getActiveTimeSum(activities) >= getMaxPaSum(activities);
    }

    private boolean isStaticTargetReached(List<Activity> activities) {
        boolean output = false;
        for (Activity activity : activities) {
            if (activity.getLongestInactivityInterval() >= activity.getUserMaxContInacTarget()) {
                output = true;
            }
        }

        return output;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        protected TextView mDate;
        @BindView(R.id.pa_goal)
        protected TextView mPaGoal;
        @BindView(R.id.pa_progress)
        protected TextView mPaProgress;
        @BindView(R.id.static_goal)
        protected TextView mStaticGoal;
        @BindView(R.id.static_progress)
        protected TextView mStaticProgress;
        @BindView(R.id.activeProgress)
        protected ProgressBar mActivePB;
        @BindView(R.id.staticProgress)
        protected ProgressBar mStaticPB;

        public HistoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Activity> activitiesForThisWeek = mData.get(getAdapterPosition());

                    int maxContInacTarget = getUserMCITargetForWeek(activitiesForThisWeek);

                    int longestInactInterval = getMaxContInacForWeek(activitiesForThisWeek);
                    int result = Math.round(longestInactInterval / maxContInacTarget);
                    Toast.makeText(mContext, "In this week you have exceeded the inactivity target: x"
                            + result, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}