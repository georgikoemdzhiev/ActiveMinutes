package georgikoemdzhiev.activeminutes.utils;

import java.util.ArrayList;

public class LimitedSizeList<K> extends ArrayList<K> {

    private int maxSize;

    public LimitedSizeList(int size) {
        this.maxSize = size;
    }

    public boolean add(K k) {
        boolean r = super.add(k);
        if (size() >= maxSize) {
            removeRange(0, size() - maxSize);
        }
        return r;
    }

    public K getYongest() {
        return get(size() - 1);
    }

    public K getOldest() {
        return get(0);
    }
}