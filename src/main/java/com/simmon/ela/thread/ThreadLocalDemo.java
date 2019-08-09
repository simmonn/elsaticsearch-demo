package com.simmon.ela.thread;

public class ThreadLocalDemo {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public Integer increateOne(){
        threadLocal.set(threadLocal.get() + 1);
        return threadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalDemo num = new ThreadLocalDemo();
        NumThread thread1 = new NumThread(num);
        NumThread thread2 = new NumThread(num);
        NumThread thread3 = new NumThread(num);
        thread1.start();
        thread2.start();
        thread3.start();
    }

}
