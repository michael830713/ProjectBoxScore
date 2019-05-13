package com.mike.projectboxscore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainActivityTest {

//    public class Fibonacci {
//        long a = 0;
//        long b = 1;
//        long c = 0;
//
//        private long getFibonacci(long amount) {
//            if (amount == 0) {
//                return 0;
//            } else if (amount == 1) {
//                return 1;
//            } else if (amount - 2 > 0) {
//                c = a + b;
//                a = b;
//                b = c;
//                System.out.println("" + c);
//                amount -= 1;
//                getFibonacci(amount);
//            }
//            return c;
//        }
//    }

    private Fibonacci mFibonacci;
    private ArrayList<Long> longs = new ArrayList<>();

    @Before

    public void setUp() throws Exception {
        mFibonacci = new Fibonacci();
    }

    @Test
    public void testFibonacci() {

        long result = mFibonacci.getFibonacci(10);
        assertEquals(55, result);
    }

    @Test
    public void testFibonacciZero() {
        long result = mFibonacci.getFibonacci(0);
        assertEquals(0, result);
    }

    @Test
    public void testFibonacciArray() {
        mFibonacci.init();
        ArrayList<Long> result = mFibonacci.fibonacciArray(10);
        System.out.println(result);
//        assertEquals();
//        assertEquals(0, result);
    }

    @Test
    public void testFibonacciOne() {
        long result = mFibonacci.getFibonacci(2);
        assertEquals(1, result);
    }

    @After
    public void tearDown() throws Exception {
        mFibonacci = null;
    }
}