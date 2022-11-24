package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.domain.common.controller.BaseResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class MonthlyStatisticResponse extends BaseResponse {
    private int monthlyTotalOrderCount;
    private int monthlyTotalOrderedBookCount;
    private int monthlyTotalPurchasedAmount;

}
