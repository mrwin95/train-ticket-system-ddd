package win.trainticket.ddd.domain.repository;

import win.trainticket.ddd.domain.model.entity.TicketDetail;

import java.util.Optional;

public interface TicketDetailDomainRepository {

    public Optional<TicketDetail> findById(Long ticketId);
}
