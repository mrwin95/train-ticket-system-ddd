package win.trainticket.ddd.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Ticket {

    @Id
    private Long id;

    private String name;
    private String description;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
