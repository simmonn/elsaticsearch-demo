package com.simmon.ela.thread;

public class NumThread extends Thread {
        private ThreadLocalDemo threadLocalDemo;

        public NumThread(ThreadLocalDemo threadLocalDemo) {
            this.threadLocalDemo = threadLocalDemo;
        }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("thread-" + Thread.currentThread().getName() +"-" + threadLocalDemo.increateOne());
        }
    }
}
