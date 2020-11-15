package com.example.javaadvanced.network.okhttp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.internal.Util;

/**
 * 线程池策略：
 * 当线程数量小于corePoolSize，新建线程(核心)来处理被添加的任务；
 * <p>
 * 当线程数量大于等于 corePoolSize，如果存在空闲线程，使用空闲线程执行新任务；
 * <p>
 * 当线程数量大于等于 corePoolSize，如果不存在空闲线程(线程都在执行任务，没有空闲的线程)，
 * 新任务被添加到等待队列(注意这时不是新建线程执行任务，而是加入到等待队列)，
 * 如果添加成功则等待空闲线程执行，如果添加失败(说明队列满了)：
 * 如果线程数量小于maximumPoolSize，新建线程执行新任务；
 * 如果线程数量等于maximumPoolSize，拒绝此任务，使用饱和策略来处理这个任务。
 * <p>
 * 测试 为什么okhttp要用SynchronousQueue这种队列。
 */
public class ThreadPoolTest {


    public static void main(String[] args) {

        //testArrayBlockingQueue();
        testArrayBlockingQueue2();
        //testLinkedBlockingQueue();
        //testLinkedBlockingQueue2();
        //testSynchronousQueue();

    }

    /**
     * 输出结果：
     * 任务1Thread[pool-1-thread-1,5,main]
     * <p>
     * <p>
     * 任务2一直没有执行，为什么？因为加入任务1后，线程池第一次肯定是会新建一个线程执行的，我们称为线程1。
     * 然后加入任务2，因为此时队列是空的，所以任务2被放到队列中，在等待空闲线程，注意这时不会新建线程(因为核心线程数是0，而队列未满，可以加入元素)，
     * 而线程1一直在执行任务1中的while循环，所以没有空闲线程，导致任务2一直在队列中等待。
     * <p>
     * 所以，如果okhttp这么配置线程池参数，会导致后面的网络请求被前面的网络请求卡住的情况，不能达到最大的并发量。
     */
    public static void testArrayBlockingQueue() {
        //ArrayBlockingQueue必须传递容量
        ArrayBlockingQueue<Runnable> runnablesQueue = new ArrayBlockingQueue(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0,    //核心线程数
                Integer.MAX_VALUE,  //最大线程数，包括核心线程数
                60,    //空闲线程的闲置时间，即空闲线程等待新任务到来的最长时间，超过这个时间，空闲线程会被销毁
                TimeUnit.SECONDS,    //闲置时间的单位
                runnablesQueue      //线程等待队列
        );

        //发起一次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务1" + Thread.currentThread());
                //while循环模拟一次非常耗时的网络请求
                while (true) {

                }
            }
        });

        //发起第二次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务2" + Thread.currentThread());
            }
        });


    }

    /**
     * 输出结果：
     * 任务1Thread[pool-1-thread-1,5,main]
     * 任务3Thread[pool-1-thread-2,5,main]
     * 任务2Thread[pool-1-thread-2,5,main]
     * <p>
     * <p>
     * 反而是任务3先执行，为什么？
     * 因为添加任务2时发现队列没有满，所以任务2被加入了队列，
     * 添加任务3时发现队列已经满了，所以根据线程池策略会新建一个线程2来执行任务3，
     * 任务3执行完成后，线程2空闲，会去队列中取出任务2执行，所以出现了后提交的反而先执行的情况。
     */
    public static void testArrayBlockingQueue2() {
        //ArrayBlockingQueue必须传递容量
        ArrayBlockingQueue<Runnable> runnablesQueue = new ArrayBlockingQueue(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0,    //核心线程数
                Integer.MAX_VALUE,  //最大线程数，包括核心线程数
                60,    //空闲线程的闲置时间，即空闲线程等待新任务到来的最长时间，超过这个时间，空闲线程会被销毁
                TimeUnit.SECONDS,    //闲置时间的单位
                runnablesQueue      //线程等待队列
        );

        //发起一次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务1" + Thread.currentThread());
                //while循环模拟一次非常耗时的网络请求
                while (true) {

                }
            }
        });

        //发起第二次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务2" + Thread.currentThread());
            }
        });

        //发起第三次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务3" + Thread.currentThread());
            }
        });


    }


    /**
     * 输出结果：
     * 任务1Thread[pool-1-thread-1,5,main]
     * <p>
     * 和ArrayBlockingQueue一样。
     * 如果okhttp这么配置线程池参数，会导致后面的网络请求被前面的网络请求卡住的情况，不能达到最大的并发量。
     */
    public static void testLinkedBlockingQueue() {
        //LinkedBlockingQueue可以不传递容量，默认容量是Integer.MAX_VALUE
        LinkedBlockingQueue<Runnable> runnablesQueue = new LinkedBlockingQueue(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0,    //核心线程数
                Integer.MAX_VALUE,  //最大线程数，包括核心线程数
                60,    //空闲线程的闲置时间，即空闲线程等待新任务到来的最长时间，超过这个时间，空闲线程会被销毁
                TimeUnit.SECONDS,    //闲置时间的单位
                runnablesQueue      //线程等待队列
        );

        //发起一次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务1" + Thread.currentThread());
                //while循环模拟一次非常耗时的网络请求
                while (true) {

                }
            }
        });

        //发起第二次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务2" + Thread.currentThread());
            }
        });


    }

    /**
     * 输出结果：
     * 任务1Thread[pool-1-thread-1,5,main]
     */
    public static void testLinkedBlockingQueue2() {
        //LinkedBlockingQueue可以不传递容量，默认容量是Integer.MAX_VALUE
        LinkedBlockingQueue<Runnable> runnablesQueue = new LinkedBlockingQueue();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0,    //核心线程数
                Integer.MAX_VALUE,  //最大线程数，包括核心线程数
                60,    //空闲线程的闲置时间，即空闲线程等待新任务到来的最长时间，超过这个时间，空闲线程会被销毁
                TimeUnit.SECONDS,    //闲置时间的单位
                runnablesQueue      //线程等待队列
        );

        //发起一次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务1" + Thread.currentThread());
                //while循环模拟一次非常耗时的网络请求
                while (true) {

                }
            }
        });

        //发起第二次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务2" + Thread.currentThread());
            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务3" + Thread.currentThread());
            }
        });


    }

    /**
     * 输出结果：
     * 任务1Thread[pool-1-thread-1,5,main]
     * 任务2Thread[pool-1-thread-2,5,main]
     * <p>
     * 因为SynchronousQueue是无容量的队列，新任务加入队列肯定失败，此时会新建一个新线程执行新任务，直到线程数量<=最大线程数
     */
    public static void testSynchronousQueue() {
        //LinkedBlockingQueue可以不传递容量，默认容量是Integer.MAX_VALUE
        SynchronousQueue<Runnable> runnablesQueue = new SynchronousQueue();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0,    //核心线程数
                Integer.MAX_VALUE,  //最大线程数，包括核心线程数
                60,    //空闲线程的闲置时间，即空闲线程等待新任务到来的最长时间，超过这个时间，空闲线程会被销毁
                TimeUnit.SECONDS,    //闲置时间的单位
                runnablesQueue      //线程等待队列
        );

        //发起一次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务1" + Thread.currentThread());
                //while循环模拟一次非常耗时的网络请求
                while (true) {

                }
            }
        });

        //发起第二次网络请求
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务2" + Thread.currentThread());
            }
        });


    }


    ExecutorService executorService;

    /**
     * 这是okhttp的线程池配置策略
     * <p>
     * 这个方法的代码和executorService = Executors.newCachedThreadPool();这种写法是一样的，
     * 只不过可以更直观的看到各个参数。
     *
     * @return
     */
    public synchronized ExecutorService executorService() {


        if (executorService == null) {
            executorService = new ThreadPoolExecutor(
                    0,                //核心线程数
                    Integer.MAX_VALUE,  //最大线程数，包括核心线程数
                    60,                    //空闲线程的闲置时间，即空闲线程等待新任务到来的最长时间，超过这个时间，空闲线程会被销毁
                    TimeUnit.SECONDS,    //闲置时间的单位
                    new SynchronousQueue<Runnable>(), //线程等待队列
                    Util.threadFactory("OkHttp Dispatcher", false) //线程创建工厂，okhttp这里传递这个参数就是为了给线程起名字
            );
        }
        return executorService;
    }


}
