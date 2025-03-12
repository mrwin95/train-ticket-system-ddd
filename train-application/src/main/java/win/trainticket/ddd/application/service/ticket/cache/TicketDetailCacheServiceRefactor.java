package win.trainticket.ddd.application.service.ticket.cache;

import win.trainticket.ddd.domain.model.entity.TicketDetail;

public interface TicketDetailCacheServiceRefactor {
    public boolean placeOrderByUser(Long ticketId);

    public TicketDetail getTicketDetailCacheDefaultNormal(Long ticketId, Long version);

    public TicketDetail getTicketDetailCacheDefaultVIP(Long ticketId, Long version);

    public TicketDetail getTicketDetailDefaultCacheLocal(Long ticketId, Long version);
}
