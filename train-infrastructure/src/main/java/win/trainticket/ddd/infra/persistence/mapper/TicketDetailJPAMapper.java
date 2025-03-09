package win.trainticket.ddd.infra.persistence.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import win.trainticket.ddd.domain.model.entity.TicketDetail;

import java.util.Optional;

public interface TicketDetailJPAMapper extends JpaRepository<TicketDetail, Long> {

    @Override
    Optional<TicketDetail> findById(Long id);
}
