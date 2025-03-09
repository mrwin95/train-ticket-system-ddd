package win.trainticket.ddd.application.service.ticket.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.trainticket.ddd.domain.model.entity.TicketDetail;
import win.trainticket.ddd.domain.service.TicketDetailDomainService;
import win.trainticket.ddd.infra.cache.redis.RedisInfraService;

@Service
@Slf4j
public class TicketDetailCacheService {

    // call redis cache

    private final RedisInfraService redisInfraService;

    private final TicketDetailDomainService ticketDetailDomainService;

    public TicketDetailCacheService(RedisInfraService redisInfraService, TicketDetailDomainService ticketDetailDomainService) {
        this.redisInfraService = redisInfraService;
        this.ticketDetailDomainService = ticketDetailDomainService;
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

    private String getEventItemKey(Long itemKey) {
        return "PROD_TICKET:ITEM:" + itemKey;
    }
}
