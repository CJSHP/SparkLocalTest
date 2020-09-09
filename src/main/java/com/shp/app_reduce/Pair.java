package com.shp.app_reduce;

import java.io.Serializable;

public class Pair implements Comparable<Pair>, Serializable {

    protected int first;
    protected int second;

    private Pair() {
    }

    public Pair(int first, int second) {
        if (first > second) {
            int temp = first;
            first = second;
            second = temp;
        }
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(Pair p) {
        int order = 1;
        if (p == null) {
            return -1 * order;
        }
        if (first == p.first) {
            if (second == p.second) {
                return 0;
            }
            return (second < p.second ? -1 : 1) * order;
        }
        return (first < p.first ? -1 : 1) * order;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", first, second);
    }
}
