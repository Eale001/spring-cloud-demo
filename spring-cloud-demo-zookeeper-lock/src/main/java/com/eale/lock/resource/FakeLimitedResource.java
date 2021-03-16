package com.eale.lock.resource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author Admin
 * @Date 2020/11/3
 * @Description //TODO
 * @Version 1.0
 **/
public class FakeLimitedResource {

//    InterProcessMutex

    private final AtomicBoolean inUse = new AtomicBoolean(false);

    public void use() throws InterruptedException {
        if (!inUse.compareAndSet(false,true)){
            // 在使用
            throw new RuntimeException("needs to be used by one client at a time");
        }
        try {
            Thread.sleep((long) (100 * Math.random()));
        }finally {
            inUse.set(false);
        }

    }

}
