package com.mike.projectboxscore;

import java.util.ArrayList;

public class Fibonacci {
    long a = 0;
    long b = 1;
    long c = 0;
    int v = 0;
    private ArrayList<Long> longs = new ArrayList<Long>();
    private long mAmount;

    public void init() {
        longs.add(a);
        longs.add(b);
    }

    public ArrayList<Long> fibonacciArray(long amount) {
        for (int i = 0; i < amount; i++) {
            longs.add(longs.get(v) + longs.get(v + 1));
            v += 1;
        }
        return longs;
    }

    public long getFibonacci(long amount) {
        if (amount == 0) {
            return 0;
        } else if (amount == 1) {
            return 1;
        } else if (amount - 2 >= 0) {
            c = a + b;
            a = b;
            b = c;
            System.out.println("" + c);
            amount -= 1;
            getFibonacci(amount);
        }
        return c;
    }

    public ArrayList<Long> getArray(long l) {
        for (long k = l; k > 0; k--) {

//            getFibonacci(k);
        }
        return longs;
    }
}
