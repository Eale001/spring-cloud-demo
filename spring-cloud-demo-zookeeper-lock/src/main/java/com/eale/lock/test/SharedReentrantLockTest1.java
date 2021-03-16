package com.eale.lock.test;

import com.eale.lock.resource.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
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
 * 不可重入锁
 * Shared Lock 与 Shared Reentrant Lock 相似，但是不可重入。这个不可重入锁由类 InterProcessSemaphoreMutex 来实现，使用方法和上面的类类似。
 *
 * 将上面程序中的 InterProcessMutex 换成不可重入锁 InterProcessSemaphoreMutex，如果再运行上面的代码，结果就会发现线程被阻塞在第二个 acquire 上，直到超时，也就是此锁不是可重入的
 * @Version 1.0
 **/
public class SharedReentrantLockTest1 {

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
                        final InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(client, lockPath);
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
