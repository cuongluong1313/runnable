package org.winnie.runnable.run.thread.distributedlock;


import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedisLock1 {
    public static void main(String[] args) {
        String redisHost = "localhost";
        String redisPort = "6379";
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", redisHost, redisPort);
        config.useSingleServer().setAddress(redisAddress).setPassword("Password1");
        RedissonClient redissonClient = Redisson.create(config);
        RLock lock = null;

        try {
            lock = redissonClient.getLock("myLock:lock");
        } catch (Exception e) {
            System.out.printf("Failed to get Lock for key with exception: %s", e.toString());
        }

        try {
            // Attempt to acquire the lock
            if (lock != null) {
                if (lock.tryLock(50, 10000, TimeUnit.MILLISECONDS)) {
                    // The lock has been acquired; perform your critical section here
                    System.out.println("Lock acquired. Performing critical section...");
                    RBucket<Object> value = redissonClient.getBucket("myLock", StringCodec.INSTANCE);
                    System.out.println(value.get());
                    Thread.sleep(5000); // Simulate some work
                    System.out.println("Critical section complete.");
                    // Release lock
                    lock.unlock();
                } else {
                    // The lock couldn't be acquired because it's already held by another process
                    System.out.println("Failed to acquire the lock. Another process holds it.");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

//         Shutdown the Redisson client when done
        redissonClient.shutdown();
    }

}

