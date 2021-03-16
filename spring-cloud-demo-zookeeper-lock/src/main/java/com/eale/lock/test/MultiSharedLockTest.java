package com.eale.lock.test;

import com.eale.lock.resource.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @Author Admin
 * @Date 2020/11/3
 * @Description //
 *  多锁
 * Multi Shared Lock 是一个锁的容器。
 * 当调用 acquire，所有的锁都会被 acquire，如果请求失败，所有的锁都会被 release。
 * 同样调用 release 时所有的锁都被 release(失败被忽略)
 * 。基本上，它就是组锁的代表，在它上面的请求释放操作都会传递给它包含的所有的锁
 * @Version 1.0
 **/
public class MultiSharedLockTest {

    private static final String lockPath1 = "/testZK/MSLock1";
    private static final String lockPath2 = "/testZK/MSLock2";
    private static final FakeLimitedResource resource = new FakeLimitedResource();
    private static final String address = "127.0.0.1:2181";


    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(address,new ExponentialBackoffRetry(1000,3));
        client.start();

        // 可重入锁
        InterProcessLock lock1 = new InterProcessMutex(client, lockPath1);
        // 不可重入锁
        InterProcessLock lock2 = new InterProcessSemaphoreMutex(client, lockPath2);
        // 组锁，多锁
        InterProcessMultiLock lock = new InterProcessMultiLock(Arrays.asList(lock1, lock2));
        if (!lock.acquire(10, TimeUnit.SECONDS)) {
            throw new IllegalStateException("不能获取多锁");
        }
        System.out.println("已获取多锁");
        System.out.println("是否有第一个锁: " + lock1.isAcquiredInThisProcess());
        System.out.println("是否有第二个锁: " + lock2.isAcquiredInThisProcess());
        try {
            // 资源操作
            resource.use();
        } finally {
            System.out.println("释放多个锁");
            // 释放多锁
            lock.release();
        }
        System.out.println("是否有第一个锁: " + lock1.isAcquiredInThisProcess());
        System.out.println("是否有第二个锁: " + lock2.isAcquiredInThisProcess());
        client.close();
        System.out.println("结束!");
    }

}
