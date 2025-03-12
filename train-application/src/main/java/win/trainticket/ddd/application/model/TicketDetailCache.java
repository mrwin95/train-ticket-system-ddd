package win.trainticket.ddd.application.model;

import lombok.Data;
import win.trainticket.ddd.domain.model.entity.TicketDetail;

@Data
public class TicketDetailCache {

    private Long version;
    private TicketDetail ticketDetail;

    public TicketDetailCache withCache(TicketDetail ticketDetail) {
        this.ticketDetail = ticketDetail;
        return this;
    }

    public TicketDetailCache withVersion(Long version) {
        this.version = version;
        return this;
    }
}
