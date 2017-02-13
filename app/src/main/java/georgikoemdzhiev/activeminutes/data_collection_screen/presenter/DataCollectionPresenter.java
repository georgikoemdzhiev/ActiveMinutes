package georgikoemdzhiev.activeminutes.data_collection_screen.presenter;

import georgikoemdzhiev.activeminutes.data_collection_screen.model.IDataCollectionModel;
import georgikoemdzhiev.activeminutes.data_collection_screen.view.IDataCollectionView;
import georgikoemdzhiev.activeminutes.database.NumOfInstResult;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public class DataCollectionPresenter implements IDataCollectionPresenter {
    private IDataCollectionController controller;
    private IDataCollectionView view;
    private IDataCollectionModel model;

    public DataCollectionPresenter(IDataCollectionController controller, IDataCollectionModel model) {
        this.controller = controller;
        this.model = model;
    }


    @Override
    public void getNumberOfInstances() {
        model.getNumOfInstances(new NumOfInstResult() {
            @Override
            public void onResult(String result) {
                view.updateNumberOfInstances(result);
            }
        });
    }

    @Override
    public void startRecording(String activityLabel) {
        if (activityLabel.isEmpty()) {
            view.showChooseActivityMessage();
        } else {
            controller.startRecording();
        }
    }

    @Override
    public void stopRecording() {
        controller.stopService();
    }

    @Override
    public void exportData() {
        controller.exportCollectedData();
    }

    @Override
    public void clearData() {
        controller.clearCollectedData();
    }

    @Override
    public void startDataColService() {
        controller.startService();
    }

    @Override
    public void setActivityLabel(String label) {
        controller.setActivityLabel(label);
    }

    public void setView(IDataCollectionView view) {
        this.view = view;
    }

    public void releaseView() {
        this.view = null;
    }

}
