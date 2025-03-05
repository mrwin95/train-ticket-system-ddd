package win.trainticket.ddd.application.service.event.impl;

import org.springframework.stereotype.Service;
import win.trainticket.ddd.application.service.event.EventAppService;

@Service
public class EventAppServiceImpl implements EventAppService {
    @Override
    public String sayHi(String who) {
        return "Hello Application";
    }
}
