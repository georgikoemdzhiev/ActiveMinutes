package georgikoemdzhiev.activeminutes.har.common.data;

import java.util.ArrayList;

/**
 * Created by Georgi koemdzhiev on 10/02/2017.
 */

public class TimeSeries extends ArrayList<Point> {
    /**
     * The time series unique ID
     */
    private String id;

    public TimeSeries(String id) {
        this.id = id;
    }

    /**
     * Adds a point to the series if there is no other point at the same time instant
     */
    public boolean addPoint(Point p) {
        if ((p != null) && (!this.contains(p))) {
            return super.add(p);
        } else {
            return false;
        }
    }

    public String getId() {
        return id;
    }

    public boolean add(Point p) {
        throw new UnsupportedOperationException("This method is not supported. Use addPoints instead.");
    }

    public void add(int x, Point p) {
        throw new UnsupportedOperationException("This method is not supported. Use addPoints instead.");
    }

    public double[] asArrayOfPointValues() {
        double[] temp = new double[size()];
        for (int i = 0; i < size(); i++) {
            temp[i] = get(i).getValue();
        }

        return temp;
    }
}