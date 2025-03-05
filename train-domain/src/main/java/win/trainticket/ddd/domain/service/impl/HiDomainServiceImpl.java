package win.trainticket.ddd.domain.service.impl;

import org.springframework.stereotype.Service;
import win.trainticket.ddd.domain.repository.HiDomainRepository;
import win.trainticket.ddd.domain.service.HiDomainService;

@Service
public class HiDomainServiceImpl implements HiDomainService {

    private final HiDomainRepository hiDomainRepository;

    public HiDomainServiceImpl(HiDomainRepository hiDomainRepository) {
        this.hiDomainRepository = hiDomainRepository;
    }

    @Override
    public String sayHi(String who) {
        return hiDomainRepository.sayHi(who);
    }
}
