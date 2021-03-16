package com.eale.lock.test;

import com.eale.lock.resource.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author Admin
 * @Date 2020/11/3
 * @Description //
 * 可重入读写锁
 * Shared Reentrant Read Write Lock，可重入读写锁，一个读写锁管理一对相关的锁，
 * 一个负责读操作，另外一个负责写操作；
 * 读操作在写锁没被使用时可同时由多个进程使用，而写锁在使用时不允许读(阻塞)；
 * 此锁是可重入的；一个拥有写锁的线程可重入读锁，但是读锁却不能进入写锁，这也意味着写锁可以降级成读锁，
 * 比如 请求写锁 --->读锁 ---->释放写锁；从读锁升级成写锁是不行的。
 * @Version 1.0
 **/
public class SharedReentrantLockTest2 {

    private static final String lockPath = "/testZK/sharedreentrantlock";
    private static final Integer clientNums = 5;
    private static final String address = "127.0.0.1:2181";

    // 共享的资源
    final static FakeLimitedResource resource = new FakeLimitedResource();

    private static CountDownLatch countDownLatch = new CountDownLatch(clientNums);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < clientNums; i++) {
            final String clientName = "client#" + i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework client = CuratorFrameworkFactory.newClient(address,new ExponentialBackoffRetry(1000,3));
                    client.start();
                    final InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, lockPath);
                    final InterProcessMutex readLock = lock.readLock();
                    final InterProcessMutex writeLock = lock.writeLock();

                    try {
                        // 注意只能先得到写锁再得到读锁，不能反过来！！！
                        if (!writeLock.acquire(10, TimeUnit.SECONDS)) {
                            throw new IllegalStateException(clientName + " 不能得到写锁");
                        }
                        System.out.println(clientName + " 已得到写锁");
                        if (!readLock.acquire(10, TimeUnit.SECONDS)) {
                            throw new IllegalStateException(clientName + " 不能得到读锁");
                        }
                        System.out.println(clientName + " 已得到读锁");
                        try {
                            resource.use(); // 使用资源
                        } finally {
                            System.out.println(clientName + " 释放读写锁");
                            readLock.release();
                            writeLock.release();
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        CloseableUtils.closeQuietly(client);
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println("结束！");
    }

}
