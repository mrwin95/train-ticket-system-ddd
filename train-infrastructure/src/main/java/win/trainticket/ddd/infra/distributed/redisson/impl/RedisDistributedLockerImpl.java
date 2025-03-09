package win.trainticket.ddd.infra.distributed.redisson.impl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import win.trainticket.ddd.infra.distributed.redisson.RedisDistributedLocker;
import win.trainticket.ddd.infra.distributed.redisson.RedisDistributedService;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisDistributedLockerImpl implements RedisDistributedService {

    private final RedissonClient redissonClient;

    public RedisDistributedLockerImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RedisDistributedLocker getDistributedLock(String lockKey) {

        RLock lock = redissonClient.getLock(lockKey);

        return new RedisDistributedLocker() {
            @Override
            public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
                return lock.tryLock(waitTime, leaseTime, unit);
            }

            @Override
            public void lock(long leaseTime, TimeUnit unit) {
                lock.lock(leaseTime, unit);
            }

            @Override
            public void unlock() {
                if(isLocked() && isHeldByCurrentThread()){
                    lock.unlock();
                }
            }

            @Override
            public boolean isLocked() {
                return lock.isLocked();
            }

            @Override
            public boolean isHeldByThread(long threadId) {
                return lock.isHeldByThread(threadId);
            }

            @Override
            public boolean isHeldByCurrentThread() {
                return lock.isHeldByCurrentThread();
            }
        };
    }
}
