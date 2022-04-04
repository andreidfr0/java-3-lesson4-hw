package com.gb.java;
public class Main {
    static Object lock = new Object();
    static volatile String c = "C";
    /*1. Создать три потока, каждый из которых выводит
    определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
     Используйте wait/notify/notifyAll.
    2. На серверной стороне сетевого чата реализовать
    управление потоками через ExecutorService.*/
    public static void main(String[] args) {

        Runnable rA = new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (true) {
                        synchronized (lock) {
                            while (i > 4) lock.wait();
                            while (c != "C") lock.wait();
                            c = "A";
                            System.out.print(c);
                            i++;
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
//                        System.out.println(Thread.currentThread().getName() + " прерван.");
                }
//                    System.out.println(Thread.currentThread().getName() + " завершен.");
            }
        };

        Runnable rB = new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (true) {
                        synchronized (lock) {
                            while (i > 4) lock.wait();
                            while (c != "A") lock.wait();
                            c = "B";
                            System.out.print(c);
                            i++;
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
//                        System.out.println(Thread.currentThread().getName() + " прерван.");
                }
//                    System.out.println(Thread.currentThread().getName() + " завершен.");
            }
        };

        Runnable rC = new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    while (true) {
                        synchronized (lock) {
                            while (i > 4) lock.wait();
                            while (c != "B") lock.wait();
                            c = "C";
                            System.out.print(c);
                            i++;
                            lock.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
//                        System.out.println(Thread.currentThread().getName() + " прерван.");
                }
//                    System.out.println(Thread.currentThread().getName() + " завершен.");
            }
        };

        Thread trC = new Thread(rC);
        Thread trB = new Thread(rB);
        Thread trA = new Thread(rA);

        trA.start();
        trB.start();
        trC.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        trA.interrupt();
        trB.interrupt();
        trC.interrupt();
    }
}
