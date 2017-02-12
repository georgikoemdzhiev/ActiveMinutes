package georgikoemdzhiev.activeminutes.data_collection_screen.presenter;

import georgikoemdzhiev.activeminutes.data_collection_screen.view.IDataCollectionView;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class DataCollectionPresenter implements IDataCollectionPresenter {
    private IDataCollectionController mController;
    private IDataCollectionView view;

    public DataCollectionPresenter(IDataCollectionController controller) {
        mController = controller;
    }

    @Override
    public void startRecording(String activityLabel) {
        if (activityLabel.isEmpty()) {
            view.showChooseActivityMessage();
        } else {
            mController.startRecording();
        }
    }

    @Override
    public void stopRecording() {
        mController.stopService();
    }

    @Override
    public void exportData() {
        mController.exportCollectedData();
    }

    @Override
    public void clearData() {
        mController.clearCollectedData();
    }

    @Override
    public void startDataColService() {
        mController.startService();
    }

    @Override
    public void setActivityLabel(String label) {
        mController.setActivityLabel(label);
    }

    public void setView(IDataCollectionView view) {
        this.view = view;
    }

    public void releaseView() {
        this.view = null;
    }

}
