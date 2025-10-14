package org.example.utils;

public class Autoincrement {
    private long current = 0;

    public Autoincrement() {
    }

    public Autoincrement(long start) {
        this.current = start;
    }

    public long increment() {
        return current++;
    }
}
