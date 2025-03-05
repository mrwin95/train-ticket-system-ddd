package win.trainticket.ddd.application.service.event.impl;

import org.springframework.stereotype.Service;
import win.trainticket.ddd.application.service.event.EventAppService;
import win.trainticket.ddd.domain.service.HiDomainService;

@Service
public class EventAppServiceImpl implements EventAppService {

    private final HiDomainService hiDomainService;

    public EventAppServiceImpl(HiDomainService hiDomainService) {
        this.hiDomainService = hiDomainService;
    }

    @Override
    public String sayHi(String who) {
        return hiDomainService.sayHi(who);
    }
}
