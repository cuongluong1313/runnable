package org.winnie.runnable.run.thread.distributedlock;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.time.Duration;

public class RedisCustomLock1 {

    public static void main(String[] args) throws InterruptedException {
        String redisHost = "localhost";
        String redisPort = "6379";
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", redisHost, redisPort);
        config.useSingleServer().setAddress(redisAddress).setPassword("Password1");
        RedissonClient redissonClient = Redisson.create(config);

        RBucket<Object> keyCustomLock = redissonClient.getBucket("projectId:apiId");
        boolean isLocked = keyCustomLock.setIfAbsent(1, Duration.ofSeconds(15));
        if (isLocked) {
            // The lock has been acquired; perform your critical section here
            System.out.println("Lock acquired. Performing critical section...");
            RMapCache<String, Object> map = redissonClient.getMapCache("projectId", StringCodec.INSTANCE);
            System.out.println("Value of map for key: 'apiId' is " + map.get("apiId"));
            Thread.sleep(5000); // Simulate some work
            System.out.println("Critical section complete.");

            // Release Lock
            Object releasedLockKey = keyCustomLock.getAndDelete();
            if (releasedLockKey == null) {
                System.out.println("There is no lock to release");
            } else {
                System.out.println("Release custom lock successfully with lock key name: releasedLockKey");
            }
        } else {
            // The lock couldn't be acquired because it's already held by another process
            System.out.println("Failed to acquire the lock. Another process holds it.");
        }

//        Shutdown the Redisson client when done
        redissonClient.shutdown();
    }


}
