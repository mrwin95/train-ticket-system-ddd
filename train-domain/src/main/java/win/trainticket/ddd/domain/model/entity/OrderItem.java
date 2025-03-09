package win.trainticket.ddd.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Problematic N+1 query
 */
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

}
