package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.security.SecurityContextUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StatisticServiceTest {

    @Mock
    StatisticRepository statisticRepository;

    StatisticService statisticService;

    static MockedStatic<SecurityContextUtil> mockedSecurityContextUtil;

    @BeforeAll
    public static void before() {
        mockedSecurityContextUtil = Mockito.mockStatic(SecurityContextUtil.class);
        mockedSecurityContextUtil.when(SecurityContextUtil::getUserEmailFromContext).thenReturn("ali@mail.com");
    }

    @AfterAll
    public static void close() {
        mockedSecurityContextUtil.close();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        statisticService = new StatisticService(statisticRepository);
    }

    @Test
    void getStatistics_ShouldReturnStatisticsResponse() {

        when(statisticRepository.getMonthlyTotalOrderCount(any())).thenReturn(Arrays.asList(1,0,0,0,0,0,0,0,0,0,0,0));
        when(statisticRepository.getMonthlyTotalOrderedBookCount(any())).thenReturn(Arrays.asList(1,0,0,0,0,0,0,0,3,0,0,0));
        when(statisticRepository.getMonthlyTotalPurchasedAmount(any())).thenReturn(Arrays.asList(1,0,0,0,5,0,0,0,0,0,0,0));

        final List<StatisticResponse> statistics = statisticService.getStatistics(UUID.randomUUID());
        Assertions.assertEquals(1, statistics.get(0).getStatistics().getMonthlyTotalOrderCount());
        Assertions.assertEquals(3, statistics.get(8).getStatistics().getMonthlyTotalOrderedBookCount());
        Assertions.assertEquals(5, statistics.get(4).getStatistics().getMonthlyTotalPurchasedAmount());
    }
}