package win.trainticket.ddd.infra.distributed.redisson;

public interface RedisDistributedService {

    public RedisDistributedLocker getDistributedLock(String lockKey);
}
