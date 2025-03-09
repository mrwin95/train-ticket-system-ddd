package win.trainticket.ddd.application.service.ticket;

import win.trainticket.ddd.domain.model.entity.TicketDetail;

public interface TicketDetailAppService {

    public TicketDetail getTicketDetailById(Long ticketId);


}
