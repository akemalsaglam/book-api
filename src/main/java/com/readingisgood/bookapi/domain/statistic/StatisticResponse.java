package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatisticResponse extends BaseResponse {
    private int month;
    private MonthlyStatisticResponse statistics;

}
