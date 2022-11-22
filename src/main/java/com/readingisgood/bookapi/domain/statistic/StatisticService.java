package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StatisticService extends BaseDomainService<OrderEntity, UUID> {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        super(statisticRepository);
        this.statisticRepository = statisticRepository;
    }


}
