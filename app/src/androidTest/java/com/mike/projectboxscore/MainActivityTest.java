package com.mike.projectboxscore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    public class Fibonacci {
        long a = 0;
        long b = 1;
        long c = 0;

        private long getFibonacci(long amount) {

            if (amount - 2 > 0) {
                c = a + b;
                a = b;
                b = c;
                System.out.println("" + c);
                amount -= 1;
                getFibonacci(amount);
            }
            return c;
        }
    }

    private Fibonacci mFibonacci;

    @Before
    public void setUp() throws Exception {
        mFibonacci = new Fibonacci();
    }

    @Test
    public void testFibonacci() {
        long result = mFibonacci.getFibonacci(10);
        assertEquals(34, result);
    }

    @After
    public void tearDown() throws Exception {
        mFibonacci = null;
    }
}