package georgikoemdzhiev.activeminutes.data_collection_screen.presenter;

import georgikoemdzhiev.activeminutes.data_collection_screen.view.IDataCollectionView;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public interface IDataCollectionPresenter {

    void startRecording();

    void stopRecording();

    void exportData();

    void clearData();

    void setActivityLabel(String label);

    void setView(IDataCollectionView view);

    void releaseView();
}
