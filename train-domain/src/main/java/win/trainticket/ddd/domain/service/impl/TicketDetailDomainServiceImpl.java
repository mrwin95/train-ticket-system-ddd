package win.trainticket.ddd.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.trainticket.ddd.domain.model.entity.TicketDetail;
import win.trainticket.ddd.domain.repository.TicketDetailDomainRepository;
import win.trainticket.ddd.domain.service.TicketDetailDomainService;

import java.util.Optional;

@Service
@Slf4j
public class TicketDetailDomainServiceImpl implements TicketDetailDomainService {

    // Call repository in domain
    private final TicketDetailDomainRepository domainRepository;
    public TicketDetailDomainServiceImpl(TicketDetailDomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }
    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
//        log.info("Implement domain: {}", ticketId);
        return domainRepository.findById(ticketId).orElse(null);
    }
}
