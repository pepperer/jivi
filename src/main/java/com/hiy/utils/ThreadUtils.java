package com.hiy.utils;

public class ThreadUtils {

    public static void printCurThreadId() {
        System.out.println("当前线程id = " + Thread.currentThread().getId());
    }
}
