package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.security.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/statistics/")
@Validated
@Slf4j
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("customers/{id}")
    public ResponseEntity<Object> getMonthlyStatistics(@PathVariable(value = "id") @Valid UUID customerId) {
        try {
            List<StatisticResponse> statisticResponses = statisticService.getStatistics(customerId);
            log.info("message='getting statistics by user.', userId={}, user={}"
                    , customerId, SecurityContextUtil.getUserEmailFromContext());
            return ResponseEntity.ok().body(statisticResponses);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting monthly orders statistics by customerId.'"
                    , exception);
            throw exception;
        }
    }

}
