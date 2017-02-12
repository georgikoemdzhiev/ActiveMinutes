package georgikoemdzhiev.activeminutes.data_collection_screen.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.data_collection_screen.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.data_collection_screen.presenter.IDataCollectionPresenter;

public class DataCollectionActivity extends AppCompatActivity implements IDataCollectionView {
    @BindView(R.id.activityLabel)
    TextView mActivityLabel;
    @BindView(R.id.recordedInstances)
    TextView mNumRecInstances;

    @Inject
    IDataCollectionPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        ButterKnife.bind(this);
        satisfyDependencies();
        mPresenter.setView(this);
        mPresenter.startDataColService();
    }


    @OnClick(R.id.startBtn)
    public void onStartButton(Button button) {
        mPresenter.startRecording(mActivityLabel.getText().toString());
    }

    @OnClick(R.id.stopBtn)
    public void onStopButton(Button button) {
        mPresenter.stopRecording();
    }

    @OnClick(R.id.exportBtn)
    public void onExportButton(Button button) {
        mPresenter.exportData();
    }

    @OnClick(R.id.clearBtn)
    public void onClearButton(Button button) {
        mPresenter.clearData();
    }

    @OnClick(R.id.changeActLabelBtn)
    public void onChangeLabelButton(Button button) {
        new MaterialDialog.Builder(this)
                .title(R.string.select_activity_dialog_title)
                .items(R.array.activity_types)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence activityName) {
                        mPresenter.setActivityLabel(activityName.toString());
                        mActivityLabel.setText(activityName);
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ActiveMinutesApplication) getApplication()).releaseDataCollectionComponent();
        mPresenter.releaseView();
    }

    /***
     * This method performs the dependency injection
     * and satisfies the dependencies in this class
     */
    private void satisfyDependencies() {
        ((ActiveMinutesApplication) getApplication())
                .getComponent()
                .plus(new DataCollectionModule())
                .inject(this);
    }

    @Override
    public void showChooseActivityMessage() {
        Toast.makeText(this, "Select an Activity first!", Toast.LENGTH_SHORT).show();
    }
}
