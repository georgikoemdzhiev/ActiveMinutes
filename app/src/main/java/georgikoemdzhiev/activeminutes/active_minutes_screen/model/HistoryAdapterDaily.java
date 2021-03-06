package georgikoemdzhiev.activeminutes.active_minutes_screen.model;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
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

public class HistoryAdapterDaily extends RecyclerView.Adapter<HistoryAdapterDaily.HistoryViewHolder> {
    private Context mContext;
    private List<Activity> mData;

    public HistoryAdapterDaily(Context context, List<Activity> data) {
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
        Activity activity = mData.get(position);
        holder.mDate.setText(formatDate(activity.getDate()));

        holder.mActivePB.setMax(toMinutes(activity.getUserPaGoal()));
        holder.mActivePB.setProgress(toMinutes(activity.getActiveTime()));
        if (isPaGoalReached(activity)) {
            holder.mActivePB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar));
        } else {
            holder.mActivePB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar_goal_not_achieved));
        }


        holder.mStaticPB.setMax(toMinutes(activity.getUserMaxContInacTarget()));
        holder.mStaticPB.setProgress(toMinutes(activity.getLongestInactivityInterval()));
        if (isStaticTargetReached(activity)) {
            holder.mStaticPB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar_goal_not_achieved));
        } else {
            holder.mStaticPB.setProgressDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.rounded_corners_progress_bar));
        }

        holder.mPaGoal.setText(String.valueOf(toMinutes(activity.getUserPaGoal())));
        holder.mPaProgress.setText(String.valueOf(toMinutes(activity.getActiveTime())));

        holder.mStaticGoal.setText(String.valueOf(toMinutes(activity.getUserMaxContInacTarget())));
        holder.mStaticProgress.
                setText(String.valueOf(toMinutes(activity.getLongestInactivityInterval())));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private int toMinutes(int value) {
        return value / 60;
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd", Locale.UK);
        return sdf.format(date);
    }

    private boolean isPaGoalReached(Activity activity) {
        return activity.getActiveTime() >= activity.getUserPaGoal();
    }

    private boolean isStaticTargetReached(Activity activity) {
        return activity.getLongestInactivityInterval() >= activity.getUserMaxContInacTarget();
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
                    Activity activity = mData.get(getAdapterPosition());
                    int maxContInacTarget = activity.getUserMaxContInacTarget();
                    int longestInactInterval = activity.getLongestInactivityInterval();
                    int times_inac_exceeded = Math.round(longestInactInterval / maxContInacTarget);

                    boolean wrapInScrollView = true;
                    MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                            .title(R.string.dialog_daily_title)
                            .customView(R.layout.history_daily_dialog_details, wrapInScrollView)
                            .positiveText(R.string.dialog_positive_message)
                            .show();

                    View detailsContainer = dialog.getCustomView();
                    TextView timesInacTargetExceeded, longestInacInter, averageInacInter, mciTarget;
                    timesInacTargetExceeded = (TextView) detailsContainer.findViewById(R.id.times_target_exceeded);
                    timesInacTargetExceeded.setText(String.valueOf(times_inac_exceeded));
                    mciTarget = (TextView) detailsContainer.findViewById(R.id.mci_target);
                    mciTarget.setText(
                            String.format(mContext.getString(R.string.min_target_exceeded_label),
                                    String.valueOf(toMinutes(maxContInacTarget))));
                    longestInacInter = (TextView) detailsContainer.findViewById(R.id.longest_cont_inac);
                    longestInacInter.setText(
                            String.valueOf(toMinutes(activity.getLongestInactivityInterval())));
                    averageInacInter = (TextView) detailsContainer.findViewById(R.id.average_cont_inac);
                    averageInacInter.setText(
                            String.valueOf(toMinutes(activity.getAverageInactInterval())));
                }
            });
        }
    }
}