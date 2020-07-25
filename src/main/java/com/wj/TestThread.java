package com.wj;

public class TestThread {

    public static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        final Thread thread1 = new Thread() {
            public void run() {
                synchronized (lock) {
                    System.out.println("thread1 enter");
                    try {
                        System.out.println("thread1 begin wait");
                        lock.wait(5000);
                        System.out.println("thread1 begin recover");
                    } catch (InterruptedException e) {
                        System.out.println("thread1 interrupt");
                    }

                    lock.notifyAll();
                }
            }
        };

        Thread thread2 = new Thread() {
            public void run() {
                synchronized (lock) {
                    System.out.println("thread2 enter");
                    try {
                        System.out.println("thread2 begin wait");
                        sleep(10000);
                        System.out.println(thread1.getState());
                        System.out.println("thread2 begin recover");
                    } catch (InterruptedException e) {
                        System.out.println("thread2 interrupt");
                    }
                    System.out.println("thread2 begin notify");
                    lock.notifyAll();
                }
            }
        };

        thread1.start();
        thread2.start();
    }
}
