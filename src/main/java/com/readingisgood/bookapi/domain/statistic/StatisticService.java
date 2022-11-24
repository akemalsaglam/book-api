package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StatisticService extends BaseDomainService<OrderEntity, UUID> {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        super(statisticRepository);
        this.statisticRepository = statisticRepository;
    }

    public List<Integer> getMonthlyTotalOrderCount(String customerId) {
        return statisticRepository.getMonthlyTotalOrderCount(customerId);
    }

    public List<Integer> getMonthlyTotalOrderedBookCount(String customerId) {
        return statisticRepository.getMonthlyTotalOrderedBookCount(customerId);
    }

    public List<Integer> getMonthlyTotalPurchasedAmount(String customerId) {
        return statisticRepository.getMonthlyTotalPurchasedAmount(customerId);
    }


}
