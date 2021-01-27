package com.example.javaadvanced.thread;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 手写实现阻塞队列
 *
 * ReentrantLock与Condition结合使用
 */
public class MyArrayBlockingQueue<T> {

    private volatile ArrayList<T> arrayList = new ArrayList<T>();

    private ReentrantLock lock = new ReentrantLock();

    //定义两个条件，分别为“队列非满”和“队列非空”，队列非满时才能新增数据，队列非空时才能读取数据
    private Condition notFullCondition = lock.newCondition();
    private Condition notEmptyCondition = lock.newCondition();

    private volatile int capacity = 5;//阻塞队列的容量
    private volatile int length = 0;//阻塞队列存储的数据个数

    public static void main(String[] agrs) {
        //testMyArrayBlockingQueue();
        testMyArrayBlockingQueue1();
    }

    public static void testMyArrayBlockingQueue() {
        MyArrayBlockingQueue<Integer> myArrayBlockingQueue = new MyArrayBlockingQueue<>();
        for (int i = 0; i < 10; ++i) {
            final int data = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myArrayBlockingQueue.put(data);
                }
            }).start();
        }

        try {
            System.out.println("sleep");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Integer t = myArrayBlockingQueue.take();
                }
            }
        }).start();
    }

    public static void testMyArrayBlockingQueue1() {
        MyArrayBlockingQueue<Integer> myArrayBlockingQueue = new MyArrayBlockingQueue<>();
        for (int i = 0; i < 10; ++i) {
            final int data = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        myArrayBlockingQueue.put(data);
                    }
                }
            }).start();
        }

        try {
            System.out.println("sleep");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Integer t = myArrayBlockingQueue.take();
                }
            }
        }).start();
    }


    /**
     * 当一个线程调用put方法添加元素时，若集合满了则使调用线程阻塞，直到有其他线程从集合中take出数据。
     * @param data
     */
    public void put(T data) {
        System.out.println("---put(), thread="  + Thread.currentThread().getName());
        try {
            //一旦一个线程封锁了锁对象， 其他任何线程都无法通过 lock 语句。
            // 当其他线程调用 lock 时，它们被阻塞，直到第一个线程释放锁对象。
            lock.lock();

            //    if(length == allow)
            while (length == capacity) {//即队列非满时才能新增数据
                //使调用线程挂起
                //await()的作用是挂起当前线程，释放竞争资源的所，从而能够让其他线程访问竞争资源。
                //当外部条件改变时，意味着某个任务可以继续执行，可以通过signal()或者signalAll()通知这个任务
                System.out.println("put(), thread="  + Thread.currentThread().getName() + "，队列存储已经满了，挂起线程...");
                notFullCondition.await();//condition的作用是使线程挂起，当外部满足某一条件时，再通过条件对象的signal()或者signalAll()方法唤醒等待的线程。
            }
            System.out.println("put(), thread="  + Thread.currentThread().getName() + ", 新增数据 data=" + data);
            arrayList.add(data);
            ++length;
            System.out.println("put(), thread="  + Thread.currentThread().getName() + "，唤醒等待着的读取数据的线程");
            notEmptyCondition.signalAll();//唤醒所有在该条件上等待着的线程，即唤醒等待着的读取数据的线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //每个lock()的调用都必须紧跟一个try-finally子句，用来保证在所有情况下都会释放锁，
            //任务在调用await()，signal()，signalAll()之前必须拥有这个锁，即必须先调用lock()方法。
            lock.unlock();
        }
    }

    /**
     * 当一个线程调用take方法获取元素时，若集合为空则使调用线程阻塞，直到有其他线程在集合中加入新元素。
     * @return
     */
    public T take() {
        System.out.println("---take(), thread="  + Thread.currentThread().getName());
        try {
            lock.lock();
            //    if(length == 0)
            while (length == 0) {//即队列非空时才能读取数据
                System.out.println("take(), thread="  + Thread.currentThread().getName() +  "，队列已没有数据，挂起线程...");
                notEmptyCondition.await();
            }
            T data = arrayList.remove(0);
            System.out.println("take(), thread="  + Thread.currentThread().getName() + ", 读取数据 data=" + data);
            --length;
            System.out.println("take(), thread="  + Thread.currentThread().getName() +  "，唤醒等待着的新增数据的线程");
            notFullCondition.signalAll();//唤醒所有在该条件上等待着的线程，即唤醒等待着的新增数据的线程
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


}
