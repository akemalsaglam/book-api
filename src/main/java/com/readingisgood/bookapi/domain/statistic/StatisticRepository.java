package com.readingisgood.bookapi.domain.statistic;

import com.readingisgood.bookapi.domain.common.jpa.BaseRepository;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatisticRepository extends BaseRepository<OrderEntity, UUID> {

}
