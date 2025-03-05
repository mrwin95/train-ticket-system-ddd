package win.trainticket.ddd.infra.persistence.repository;

import org.springframework.stereotype.Service;
import win.trainticket.ddd.domain.repository.HiDomainRepository;

@Service
public class HiInfraRepositoryImpl implements HiDomainRepository {
    @Override
    public String sayHi(String who) {
        return "Hi from infrastructure! ";
    }
}
