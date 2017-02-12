package georgikoemdzhiev.activeminutes.database.db;

import io.realm.RealmObject;

/**
 * Created by Georgi Koemdzhiev on 12/02/2017.
 */

public class TrainingData extends RealmObject {
    private double accX__fft1;
    private double accX__fft2;
    private double accX__fft3;
    private double accX__fft4;
    private double accX__fft5;

    private double accY__fft1;
    private double accY__fft2;
    private double accY__fft3;
    private double accY__fft4;
    private double accY__fft5;

    private double accZ__fft1;
    private double accZ__fft2;
    private double accZ__fft3;
    private double accZ__fft4;
    private double accZ__fft5;

    private double accM__fft1;
    private double accM__fft2;
    private double accM__fft3;
    private double accM__fft4;
    private double accM__fft5;

    private double classValue;

    public double[] getValues() {
        double[] values = new double[21];
        values[0] = accX__fft1;
        values[1] = accX__fft2;
        values[2] = accX__fft3;
        values[3] = accX__fft4;
        values[4] = accX__fft5;

        values[5] = accY__fft1;
        values[6] = accY__fft2;
        values[7] = accY__fft3;
        values[8] = accY__fft4;
        values[9] = accY__fft5;

        values[10] = accZ__fft1;
        values[11] = accZ__fft2;
        values[12] = accZ__fft3;
        values[13] = accZ__fft4;
        values[14] = accZ__fft5;

        values[15] = accM__fft1;
        values[16] = accM__fft2;
        values[17] = accM__fft3;
        values[18] = accM__fft4;
        values[19] = accM__fft5;
        classValue = values[20];
        return values;
    }

    public void setValues(double[] values) {
        accX__fft1 = values[0];
        accX__fft2 = values[1];
        accX__fft3 = values[2];
        accX__fft4 = values[3];
        accX__fft5 = values[4];

        accY__fft1 = values[5];
        accY__fft2 = values[6];
        accY__fft3 = values[7];
        accY__fft4 = values[8];
        accY__fft5 = values[9];

        accZ__fft1 = values[10];
        accZ__fft2 = values[11];
        accZ__fft3 = values[12];
        accZ__fft4 = values[13];
        accZ__fft5 = values[14];

        accM__fft1 = values[15];
        accM__fft2 = values[16];
        accM__fft3 = values[17];
        accM__fft4 = values[18];
        accM__fft5 = values[19];
        classValue = values[20];
    }
}
