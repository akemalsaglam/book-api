package com.readingisgood.bookapi.domain.order.model;

import com.readingisgood.bookapi.domain.common.mapper.BaseMapper;
import com.readingisgood.bookapi.domain.order.OrderEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity, OrderRequest, OrderResponse> {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Named("mapEntityToResponse")
    OrderResponse mapEntityToResponse(OrderEntity entity);

    @IterableMapping(qualifiedByName = "mapEntityToResponse")
    @Named("mapEntityListToResponseList")
    List<OrderResponse> mapEntityListToResponseList(List<OrderEntity> entity);
}
