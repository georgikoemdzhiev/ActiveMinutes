package georgikoemdzhiev.activeminutes.data_collection.presenter;

import georgikoemdzhiev.activeminutes.data_collection.view.IDataCollectionView;

/**
 * Created by koemdzhiev on 09/02/2017.
 */

public interface IDataCollectionPresenter {

    void startRecording();

    void stopRecording();

    void exportData();

    void clearData();


    void setView(IDataCollectionView view);

    void releaseView();
}
