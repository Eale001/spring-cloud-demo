package com.eale.lock.test;

import com.eale.lock.resource.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author Admin
 * @Date 2020/11/3
 * @Description //
 * 可重入锁
 * Shared Reentrant Lock，全局可重入锁，所有客户端都可以请求，同一个客户端在拥有锁的同时，可以多次获取，不会被阻塞。
 * @Version 1.0
 **/
public class SharedReentrantLockTest {

    private static final String lockPath = "/testZK/sharedreentrantlock";
    private static final Integer clientNums = 5;
    private static final String address = "127.0.0.1:2181";

    // 共享的资源
    final static FakeLimitedResource resource = new FakeLimitedResource();

    private static CountDownLatch countDownLatch = new CountDownLatch(clientNums);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < clientNums; i++) {
            String clientName = "client#" + i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework client = CuratorFrameworkFactory.newClient(address,new ExponentialBackoffRetry(1000,3));
                    client.start();
                    Random random = new Random();
                    try {
                        final InterProcessMutex lock = new InterProcessMutex(client, lockPath);
                        // 每个客户端请求10次共享资源
                        for (int j = 0; j < 10; j++) {
                            if (!lock.acquire(10, TimeUnit.SECONDS)) {
                                throw new IllegalStateException(j + ". " + clientName + " 不能得到互斥锁");
                            }
                            try {
                                System.out.println(j + ". " + clientName + " 已获取到互斥锁");
                                resource.use(); // 使用资源
                                if (!lock.acquire(10, TimeUnit.SECONDS)) {
                                    throw new IllegalStateException(j + ". " + clientName + " 不能再次得到互斥锁");
                                }
                                System.out.println(j + ". " + clientName + " 已再次获取到互斥锁");
                                lock.release(); // 申请几次锁就要释放几次锁
                            } finally {
                                System.out.println(j + ". " + clientName + " 释放互斥锁");
                                lock.release(); // 总是在finally中释放
                            }
                            Thread.sleep(random.nextInt(100));
                        }
                    } catch (Throwable e) {
                        System.out.println(e.getMessage());
                    } finally {
                        CloseableUtils.closeQuietly(client);
                        System.out.println(clientName + " 客户端关闭！");
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println("结束！");
    }


}
