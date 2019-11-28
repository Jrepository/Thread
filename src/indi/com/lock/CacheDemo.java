package indi.com.lock;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.security.PrivateKey;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 03_
 */
public class CacheDemo {
    private Map<String,Object> cacheMap = new HashMap<String,Object>();

    public static void main(String[] args) {

    }

    public synchronized Object getData(String key){
        Object obj = cacheMap.get(key);
        if(obj == null){
            obj = "aaa";
        }
        return obj;
    }


    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public synchronized Object getData2(String key){
        lock.readLock().lock();
        Object obj = null;
        try {
            obj = cacheMap.get(key);
            if(obj == null){
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    if(obj == null){
                        obj = "aaa";
                    }
                }finally {
                    lock.writeLock().unlock();
                }
                lock.readLock().lock();
            }
        }finally {
            lock.readLock().unlock();
        }
        return obj;
    }
}
