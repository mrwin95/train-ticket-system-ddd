package win.trainticket.ddd.controller.rest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import win.trainticket.ddd.application.service.event.EventAppService;

import java.security.SecureRandom;

@RestController
@RequestMapping("/hello")
public class HiController {

    private static final Logger log = LoggerFactory.getLogger(HiController.class);
    private final EventAppService eventAppService;

    private static final SecureRandom random = new SecureRandom();

    private final RestTemplate restTemplate;

    public HiController(EventAppService eventAppService, RestTemplate restTemplate) {
        this.eventAppService = eventAppService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hi")
    @RateLimiter(name = "backendA", fallbackMethod = "fallBackHello")
    public String hello() {
        return eventAppService.sayHi("Win");
    }

    public String fallBackHello(Throwable throwable) {
        return "Too Many Calls";
    }

    @GetMapping("/hi/v1")
    @RateLimiter(name = "backendB", fallbackMethod = "fallBackHello")
    public String sayHi() {
        return eventAppService.sayHi("Rose");
    }

    @GetMapping("/hi/circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackCircuitBreaker")
    public String circuitBreaker() {
        int productId = random.nextInt(20) + 1;

        String result = restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, String.class);
        log.info(result);
        return restTemplate.getForObject("https://fakestoreapi.com/products/" + productId, String.class);
//        return eventAppService.sayHi("Rose");
    }

    public String fallbackCircuitBreaker(Throwable throwable) {
        return "Service fakestoreapi error";// throwable.getMessage();
    }

}
