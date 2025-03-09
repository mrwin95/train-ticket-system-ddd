package win.trainticket.ddd.infra.persistence.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.trainticket.ddd.domain.model.entity.TicketDetail;
import win.trainticket.ddd.domain.repository.TicketDetailDomainRepository;
import win.trainticket.ddd.infra.persistence.mapper.TicketDetailJPAMapper;

import java.util.Optional;

@Service
@Slf4j
public class TicketDetailInfraRepositoryImpl implements TicketDetailDomainRepository {

    // call ticket detail mapper
    private final TicketDetailJPAMapper ticketDetailJPAMapper;

    public TicketDetailInfraRepositoryImpl(TicketDetailJPAMapper ticketDetailJPAMapper) {
        this.ticketDetailJPAMapper = ticketDetailJPAMapper;
    }

    @Override
    public Optional<TicketDetail> findById(Long ticketId) {
        log.info("Implement infrastructure {}", ticketId);
        return ticketDetailJPAMapper.findById(ticketId);
    }
}
