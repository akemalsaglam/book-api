package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.domain.common.service.BaseDomainService;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class StatisticService extends BaseDomainService<OrderEntity, UUID> {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        super(statisticRepository);
        this.statisticRepository = statisticRepository;
    }

    public List<StatisticResponse> getStatistics(UUID customerId) {
        final List<Integer> monthlyTotalOrderCount = getMonthlyTotalOrderCount(customerId.toString());
        final List<Integer> monthlyTotalOrderedBookCount = getMonthlyTotalOrderedBookCount(customerId.toString());
        final List<Integer> monthlyTotalPurchasedAmount = getMonthlyTotalPurchasedAmount(customerId.toString());
        List<StatisticResponse> statisticResponses = new ArrayList<>();
        IntStream.range(1, 13).forEach(monthIndex -> {
            StatisticResponse statisticResponse = new StatisticResponse();
            final MonthlyStatisticResponse monthlyStatisticResponse = MonthlyStatisticResponse.builder()
                    .monthlyTotalOrderCount(monthlyTotalOrderCount.get(monthIndex - 1))
                    .monthlyTotalOrderedBookCount(monthlyTotalOrderedBookCount.get(monthIndex - 1))
                    .monthlyTotalPurchasedAmount(monthlyTotalPurchasedAmount.get(monthIndex - 1)).build();
            statisticResponse.setMonth(monthIndex);
            statisticResponse.setStatistics(monthlyStatisticResponse);
            statisticResponses.add(statisticResponse);
        });
        return statisticResponses;
    }

    private List<Integer> getMonthlyTotalOrderCount(String customerId) {
        return statisticRepository.getMonthlyTotalOrderCount(customerId);
    }

    private List<Integer> getMonthlyTotalOrderedBookCount(String customerId) {
        return statisticRepository.getMonthlyTotalOrderedBookCount(customerId);
    }

    private List<Integer> getMonthlyTotalPurchasedAmount(String customerId) {
        return statisticRepository.getMonthlyTotalPurchasedAmount(customerId);
    }


}
