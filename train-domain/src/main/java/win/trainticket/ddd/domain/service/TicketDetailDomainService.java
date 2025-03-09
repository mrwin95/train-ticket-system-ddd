package win.trainticket.ddd.domain.service;

import win.trainticket.ddd.domain.model.entity.TicketDetail;

public interface TicketDetailDomainService {

    public TicketDetail getTicketDetailById(Long ticketId);

}
