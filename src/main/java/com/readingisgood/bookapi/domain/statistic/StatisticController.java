package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.security.authentication.ServiceErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/statistics/")
@Validated
@Slf4j
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("customers/{id}")
    public ResponseEntity<Object> getMonthlyStatistics(@PathVariable(value = "id") @Valid UUID customerId) {
        try {
            return ResponseEntity.ok().body(true);
        } catch (Exception exception) {
            log.error("message='error has occurred while getting monthly orders statistics by customerId.'"
                    , exception);
            return ResponseEntity.internalServerError()
                    .body(new ServiceErrorMessage("Bir hata oluştu"));
        }
    }

}
