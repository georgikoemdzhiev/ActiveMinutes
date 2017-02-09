package georgikoemdzhiev.activeminutes.data_collection.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import georgikoemdzhiev.activeminutes.R;
import georgikoemdzhiev.activeminutes.application.ActiveMinutesApplication;
import georgikoemdzhiev.activeminutes.data_collection.dagger.DataCollectionModule;
import georgikoemdzhiev.activeminutes.data_collection.presenter.IDataCollectionPresenter;

public class DataCollectionActivity extends AppCompatActivity implements IDataCollectionView {
    @Inject
    IDataCollectionPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        ButterKnife.bind(this);
        satisfyDependencies();
        mPresenter.setView(this);
    }


    @OnClick(R.id.startBtn)
    public void onStartButton(Button button) {
        mPresenter.startRecording();
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

}
