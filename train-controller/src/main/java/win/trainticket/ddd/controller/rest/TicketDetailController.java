package win.trainticket.ddd.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.trainticket.ddd.application.service.ticket.TicketDetailAppService;
import win.trainticket.ddd.controller.model.enums.ResultUtil;
import win.trainticket.ddd.controller.model.vo.ResultMessage;
import win.trainticket.ddd.domain.model.entity.TicketDetail;

@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketDetailController {

    // Call service Application

    private final TicketDetailAppService ticketDetailAppService;

    public TicketDetailController(TicketDetailAppService ticketDetailAppService) {
        this.ticketDetailAppService = ticketDetailAppService;
    }

    @GetMapping("/{ticketId}/detail/{detailId}")
    public ResultMessage<TicketDetail> getTicketDetail(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("detailId") Long detailId){
        log.info("params, HELLO MEMBER ticketId={}, detailId={}", ticketId, detailId);
        return ResultUtil.data(ticketDetailAppService.getTicketDetailById(ticketId));
    }
}
