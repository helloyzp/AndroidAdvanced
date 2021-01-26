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

    //定义两个条件，分别为“集合满”和“集合空”
    private Condition fullCondition = lock.newCondition();
    private Condition emptyCondition = lock.newCondition();

    private volatile int allow = 5;
    private volatile int length = 0;

    public static void main(String[] f) {

        MyArrayBlockingQueue<Integer> myArrayBlockingQueue = new MyArrayBlockingQueue<>();
        for (int i = 0; i < 10; ++i) {
            final int j = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myArrayBlockingQueue.put(j);
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
                    System.out.println("pop " + t);
                }
            }
        }).start();
    }

    /**
     * 当一个线程调用put方法添加元素时，若集合满了则使调用线程阻塞，直到有其他线程从集合中take出数据。
     * @param t
     */
    public void put(T t) {

        try {
            //一旦一个线程封锁了锁对象， 其他任何线程都无法通过 lock 语句。
            // 当其他线程调用 lock 时，它们被阻塞，直到第一个线程释放锁对象。
            lock.lock();

            //    if(length == allow)
            while (length == allow) {
                //使调用线程挂起
                fullCondition.await();//condition的作用是使线程先等待，当外部满足某一条件时，在通过条件对象唤醒等待的线程。
            }
            System.out.println("push " + t);
            arrayList.add(t);
            ++length;
            emptyCondition.signalAll();//唤醒所有在该条件上等待着的线程，即唤醒等待着的取数据的线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 当一个线程调用take方法获取元素时，若集合为空则使调用线程阻塞，直到有其他线程在集合中加入新元素。
     * @return
     */
    public T take() {
        try {
            lock.lock();
            //    if(length == 0)
            while (length == 0) {
                emptyCondition.await();
            }
            T t = arrayList.get(0);
            arrayList.remove(0);
            --length;
            fullCondition.signalAll();//唤醒所有在该条件上等待着的线程，即唤醒等待着的新增数据的线程
            return t;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


}
