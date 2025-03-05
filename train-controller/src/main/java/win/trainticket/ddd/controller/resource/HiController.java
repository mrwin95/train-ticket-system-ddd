package win.trainticket.ddd.controller.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.trainticket.ddd.application.service.event.EventAppService;

@RestController
@RequestMapping("/hello")
public class HiController {

    private final EventAppService eventAppService;

    public HiController(EventAppService eventAppService) {
        this.eventAppService = eventAppService;
    }

    @GetMapping("/hi")
    public String hello() {
        return eventAppService.sayHi("Win");
    }

    @GetMapping("/hi/v1")
    public String sayHi() {
        return eventAppService.sayHi("Rose");
    }

}
