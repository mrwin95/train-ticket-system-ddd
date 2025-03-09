package win.trainticket.ddd.application.service.ticket.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.trainticket.ddd.application.service.ticket.TicketDetailAppService;
import win.trainticket.ddd.domain.model.entity.TicketDetail;
import win.trainticket.ddd.domain.service.TicketDetailDomainService;

@Service
@Slf4j
public class TicketDetailAppServiceImpl implements TicketDetailAppService {

    // Need call service detail domain

    private final TicketDetailDomainService ticketDetailDomainService;

    public TicketDetailAppServiceImpl(TicketDetailDomainService ticketDetailDomainService) {
        this.ticketDetailDomainService = ticketDetailDomainService;
    }

    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        log.info("Implement application: {}", ticketId);
        return ticketDetailDomainService.getTicketDetailById(ticketId);
    }
}
