package win.trainticket.ddd.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_item")
public class TicketDetail {

    @Id
    private Long id;

    private String name;
    private String description;

    private int stockInitial;
    private int stockAvailable;

    private int priceOriginal;
    private int priceFlash;

    private boolean isStockPrepared;

    private LocalDateTime saleStartTime;
    private LocalDateTime saleEndTime;

    private int status;

    private Long activityId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
