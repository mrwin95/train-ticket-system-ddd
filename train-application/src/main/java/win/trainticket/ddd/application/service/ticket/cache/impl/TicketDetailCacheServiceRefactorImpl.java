package win.trainticket.ddd.application.service.ticket.cache.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.trainticket.ddd.application.service.ticket.cache.TicketDetailCacheServiceRefactor;
import win.trainticket.ddd.domain.model.entity.TicketDetail;
import win.trainticket.ddd.domain.service.TicketDetailDomainService;
import win.trainticket.ddd.infra.cache.redis.RedisInfraService;
import win.trainticket.ddd.infra.distributed.redisson.RedisDistributedLocker;
import win.trainticket.ddd.infra.distributed.redisson.RedisDistributedService;

import java.util.concurrent.TimeUnit;

@Service
public class TicketDetailCacheServiceRefactorImpl implements TicketDetailCacheServiceRefactor {

    private static final Logger log = LoggerFactory.getLogger(TicketDetailCacheServiceRefactorImpl.class);
    // call redis cache

    private final RedisInfraService redisInfraService;

    private final RedisDistributedService redisDistributedService;

    private final TicketDetailDomainService ticketDetailDomainService;

    private final static Cache<Long, TicketDetail> localCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .concurrencyLevel(10)
            .expireAfterAccess(10, TimeUnit.MINUTES).build();

    public TicketDetailCacheServiceRefactorImpl(RedisInfraService redisInfraService, RedisDistributedService redisDistributedService, TicketDetailDomainService ticketDetailDomainService) {
        this.redisInfraService = redisInfraService;
        this.redisDistributedService = redisDistributedService;
        this.ticketDetailDomainService = ticketDetailDomainService;
    }

    public boolean placeOrderByUser(Long ticketId) {
        localCache.invalidate(ticketId);
        redisInfraService.delete(getEventItemKey(ticketId));
        return true;
    }
    public TicketDetail getTicketDetailCacheDefaultNormal(Long ticketId, Long version) {
        // 1. Get Ticket detail from redis

        TicketDetail ticketDetail = redisInfraService.getObject(getEventItemKey(ticketId), TicketDetail.class);

        // 2. Hit cache

        if (ticketDetail != null) {
            return ticketDetail;
        }

        // 3. If NO missing cache

        //4 . get from db

        ticketDetail = ticketDetailDomainService.getTicketDetailById(ticketId);

        // 5. check item
        if (ticketDetail != null) {
            redisInfraService.setObject(getEventItemKey(ticketId), ticketDetail);
        }

        return ticketDetail;
    }

    public TicketDetail getTicketDetailCacheDefaultVIP(Long ticketId, Long version) {

        //1. Get ticket from cache
        TicketDetail ticketDetail = redisInfraService.getObject(getEventItemKey(ticketId), TicketDetail.class);
        if (ticketDetail != null) {
            log.info("From cache exits: {}", ticketDetail);
            return ticketDetail;
        }

        // 2. process no cache

        RedisDistributedLocker locker = redisDistributedService.getDistributedLock("PRO_LOCK_KEY_ITEM" + ticketDetail);
        try {
            // create lock

            boolean isLock = locker.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLock) {
                log.info("Lock wait item: {}, version: {}", ticketId, version);
                return ticketDetail;
            }
            // Get cache

            ticketDetail = redisInfraService.getObject(getEventItemKey(ticketId), TicketDetail.class);
            // Yes

            if (ticketDetail != null) {
                log.info("From cache exits: {}", ticketDetail);
                return ticketDetail;
            }

            // if still cannot , get from cache

            ticketDetail = ticketDetailDomainService.getTicketDetailById(ticketId);
            log.info("From dbs exits: {}", ticketDetail);
            if (ticketDetail == null) {
                log.info("Ticket not exist: {}", ticketDetail);
                redisInfraService.setObject(getEventItemKey(ticketId), ticketDetail);
                return ticketDetail;
            }

            // if exist set cache

            redisInfraService.setObject(getEventItemKey(ticketId), ticketDetail);

            return ticketDetail;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            locker.unlock();
        }

    }

    private TicketDetail getTicketDetailLocalCache(Long ticketId) {
        try {
            return localCache.getIfPresent(ticketId);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public TicketDetail getTicketDetailDefaultCacheLocal(Long ticketId, Long version) {

        //1. Get ticket from cache
        TicketDetail ticketDetail =  getTicketDetailLocalCache(ticketId);// redisInfraService.getObject(getEventItemKey(ticketId), TicketDetail.class);
        if (ticketDetail != null) {
            log.info("From LOCAL CACHE: {}", ticketDetail);
            return ticketDetail;
        }

        // get cache from redis
        ticketDetail = redisInfraService.getObject(getEventItemKey(ticketId), TicketDetail.class);
        if (ticketDetail != null) {
            log.info("From DISTRIBUTED CACHE: {}", ticketDetail);
            localCache.put(ticketId, ticketDetail);
            return ticketDetail;
        }


        // 2. process no cache

        RedisDistributedLocker locker = redisDistributedService.getDistributedLock("PRO_LOCK_KEY_ITEM" + ticketDetail);
        try {
            // create lock

            boolean isLock = locker.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLock) {
                log.info("Lock wait item: {}, version: {}", ticketId, version);
                return ticketDetail;
            }
            // Get cache

            ticketDetail = redisInfraService.getObject(getEventItemKey(ticketId), TicketDetail.class);
            // Yes

            if (ticketDetail != null) {
                log.info("From cache exits: {}", ticketDetail);
                localCache.put(ticketId, ticketDetail);
                return ticketDetail;
            }

            // if still cannot , get from cache

            ticketDetail = ticketDetailDomainService.getTicketDetailById(ticketId);
            log.info("From dbs exits: {}", ticketDetail);
            if (ticketDetail == null) {
                log.info("Ticket not exist: {}", ticketDetail);
                redisInfraService.setObject(getEventItemKey(ticketId), ticketDetail);
                localCache.put(ticketId, null);
                return ticketDetail;
            }

            // if exist set cache

            redisInfraService.setObject(getEventItemKey(ticketId), ticketDetail);
            localCache.put(ticketId, ticketDetail);
            return ticketDetail;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            locker.unlock();
        }

    }

    private String getEventItemKey(Long itemKey) {
        return "PROD_TICKET:ITEM:" + itemKey;
    }
}
