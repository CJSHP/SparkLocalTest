package com.shp.app_reduce;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.LinkedList;

@SuppressWarnings("unused")
public class Pair implements Comparable<Pair>, Serializable {

    private static final long serialVersionUID = 1166402657119922731L;
    protected int first;
    protected int second;

    private static final LinkedList<Pair> pairs = new LinkedList<>();

    private Pair() {}

    public Pair(int first, int second) {
        if (first > second) {
            int temp = first;
            first = second;
            second = temp;
        }
        this.first = first;
        this.second = second;
    }

    public static Pair getPair() {
        if (pairs.isEmpty()) {
            return new Pair(0, 0);
        }
        return pairs.removeLast();
    }

    public static void pushPair(Pair p) {
        pairs.addLast(p);
    }

    @Override
    public int hashCode() {
        return (this.first << 8) ^ this.first ^ (this.second << 4) ^ this.second;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair p = (Pair) o;
        return this.first == p.first && this.second == p.second;
    }

    @Override
    public int compareTo(@Nonnull Pair p) {
        int order = 1;
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
