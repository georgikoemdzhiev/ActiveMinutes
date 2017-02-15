package georgikoemdzhiev.activeminutes.har;

import georgikoemdzhiev.activeminutes.data_layer.IHarDataManager;

/**
 * Created by Georgi Koemdzhiev on 14/02/2017.
 */

public class HarClassifyManager extends HarManager {
    public HarClassifyManager(IHarDataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void feedData(float[] xyz, long timestamp) {

    }
}
