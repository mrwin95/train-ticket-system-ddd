package win.trainticket.ddd.application.service.ticket.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.trainticket.ddd.application.service.ticket.TicketDetailAppService;
import win.trainticket.ddd.application.service.ticket.cache.TicketDetailCacheService;
import win.trainticket.ddd.domain.model.entity.TicketDetail;
import win.trainticket.ddd.domain.service.TicketDetailDomainService;

@Service
public class TicketDetailAppServiceImpl implements TicketDetailAppService {

    private static final Logger log = LoggerFactory.getLogger(TicketDetailAppServiceImpl.class);
    // Need call service detail domain

    private final TicketDetailDomainService ticketDetailDomainService;

    // call cache
    private final TicketDetailCacheService ticketDetailCacheService;

    public TicketDetailAppServiceImpl(TicketDetailDomainService ticketDetailDomainService, TicketDetailCacheService ticketDetailCacheService) {
        this.ticketDetailDomainService = ticketDetailDomainService;
        this.ticketDetailCacheService = ticketDetailCacheService;
    }

    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        log.info("Implement application: {}", ticketId);
//        return ticketDetailDomainService.getTicketDetailById(ticketId);
//        return ticketDetailCacheService.getTicketDetailCacheDefaultNormal(ticketId, System.currentTimeMillis());
//        return ticketDetailCacheService.getTicketDetailCacheDefaultVIP(ticketId, System.currentTimeMillis());
        return ticketDetailCacheService.getTicketDetailDefaultCacheLocal(ticketId, System.currentTimeMillis());
    }

    @Override
    public boolean placeOrderTicketByUser(Long id) {
        return ticketDetailCacheService.placeOrderByUser(id);
    }
}
