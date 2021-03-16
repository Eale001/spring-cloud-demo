package com.eale.lock.test;

import com.eale.lock.resource.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @Author Admin
 * @Date 2020/11/3
 * @Description //
 * 信号量
 * Shared Semaphore，一个计数的信号量类似JDK的 Semaphore，
 * JDK中 Semaphore 维护的一组许可(permits)，而Cubator中称之为租约(Lease)。
 * 有两种方式可以决定 semaphore 的最大租约数，
 * 第一种方式是由用户给定的 path 决定，
 * 第二种方式使用 SharedCountReader 类。
 * 如果不使用 SharedCountReader，没有内部代码检查进程是否假定有10个租约而进程B假定有20个租约。
 * 所以所有的实例必须使用相同的 numberOfLeases 值.
 * @Version 1.0
 **/
public class SharedSemaphoreTest {

    private static final int MAX_LEASE = 10;
    private static final String PATH = "/testZK/semaphore";
    private static final FakeLimitedResource resource = new FakeLimitedResource();

    private static final String address = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(address,new ExponentialBackoffRetry(1000,3));
        client.start();
        InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, PATH, MAX_LEASE);
        Collection<Lease> leases = semaphore.acquire(5);
        System.out.println("获取租约数量：" + leases.size());
        Lease lease = semaphore.acquire();
        System.out.println("获取单个租约");
        resource.use(); // 使用资源
        // 再次申请获取5个leases，此时leases数量只剩4个，不够，将超时
        Collection<Lease> leases2 = semaphore.acquire(5, 10, TimeUnit.SECONDS);
        System.out.println("获取租约，如果超时将为null：" + leases2);
        System.out.println("释放租约");
        semaphore.returnLease(lease);
        // 再次申请获取5个，这次刚好够
        leases2 = semaphore.acquire(5, 10, TimeUnit.SECONDS);
        System.out.println("获取租约，如果超时将为null：" + leases2);
        System.out.println("释放集合中的所有租约");
        semaphore.returnAll(leases);
        semaphore.returnAll(leases2);
        client.close();
        System.out.println("结束!");
    }

}
