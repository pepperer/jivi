package com.hiy.utils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main_Thread {

    public static void main(String[] args) {
        Main_Thread item = new Main_Thread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                item.testMethod();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                item.testMethod();
            }
        }).start();
        item.testMethod();
    }

    private Lock lock = new ReentrantLock();

    public void testMethod() {
        lock.lock();
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadName = " + Thread.currentThread().getName() + (" " + (i + 1)));
        }
        lock.unlock();
    }
}
