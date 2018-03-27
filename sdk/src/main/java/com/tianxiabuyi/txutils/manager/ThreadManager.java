package com.tianxiabuyi.txutils.manager;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 管理线程池
 * 根据不同的业务, 开启的线程数不一样
 *
 * @author WangYaoDong
 * @date 2018/3/27 15:42
 */
public class ThreadManager {

    private static ThreadPoolProxy mLongPool = null;
    private static final Object mLongLock = new Object();

    private static ThreadPoolProxy mShortPool = null;
    private static final Object mShortLock = new Object();

    private static ThreadPoolProxy mDownLoadPool = null;
    private static final Object mDownLoadLock = new Object();

    /**
     * 网络请求, 用长的线程池
     */
    public static ThreadPoolProxy creatLongPool() {
        synchronized (mLongLock) {
            if (mLongPool == null) {
                mLongPool = new ThreadPoolProxy(5, 5,
                        5L, "long-pool-%d");
            }
            return mLongPool;
        }
    }

    /**
     * 本地IO操作
     */
    public static ThreadPoolProxy creatShortPool() {
        synchronized (mShortLock) {
            if (mShortPool == null) {
                mShortPool = new ThreadPoolProxy(2, 2,
                        5L, "short-pool-%d");
            }
            return mShortPool;
        }
    }

    /**
     * 下载
     */
    public static ThreadPoolProxy creatDownLoadPool() {
        synchronized (mDownLoadLock) {
            if (mDownLoadPool == null) {
                mDownLoadPool = new ThreadPoolProxy(2, 2,
                        5L, "download-pool-%d");
            }
            return mDownLoadPool;
        }
    }

    /**
     * 封装了线程池
     */
    public static class ThreadPoolProxy {
        // 系统提供的线程池
        private ThreadPoolExecutor mPool;
        // 线程名
        private String mThreadName;
        // 线程数量
        private int mCorePoolSize;
        // 额外的数量
        private int maximumPoolSize;
        // 保持的时间
        private long mKeepAliveTime;

        public ThreadPoolProxy(int mCorePoolSize, int maximumPoolSize, long mKeepAliveTime, String name) {
            super();
            this.mCorePoolSize = mCorePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.mKeepAliveTime = mKeepAliveTime;
            this.mThreadName = mThreadName;
        }

        /**
         * 执行任务
         */
        public synchronized void execute(Runnable runnable) {
            if (runnable == null) {
                return;
            }
            // 1 线程池的任务数量
            // 2 如果队列放满了 额外创建的线程
            // 3 没有任务的还能活多久
            // 4 存活时间的单位
            // 5 线程池满了队列   可以指定上限
            // 6 创建线程池的工厂
            // 7 处理异常的Handler (固定写法 )
            if (mPool == null) {
                ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                        .setNameFormat(mThreadName).build();
                mPool = new ThreadPoolExecutor(mCorePoolSize, maximumPoolSize,
                        mKeepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(10),
                        namedThreadFactory,
                        new AbortPolicy());
            }
            mPool.execute(runnable);
        }

        /**
         * 移除任务
         */
        public synchronized void cancel(Runnable runnable) {
            if (runnable == null) {
                return;
            }
            if (mPool != null) {
                if (!mPool.isShutdown() || mPool.isTerminating()) {
                    mPool.getQueue().remove(runnable);
                }
            }
        }
    }
}

