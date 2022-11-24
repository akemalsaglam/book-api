package com.readingisgood.bookapi.domain.orderbook.model;

import com.readingisgood.bookapi.domain.common.mapper.BaseMapper;
import com.readingisgood.bookapi.domain.orderbook.OrderBookEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderBookMapper extends BaseMapper<OrderBookEntity, OrderBookRequest, OrderBookResponse> {

    OrderBookMapper INSTANCE = Mappers.getMapper(OrderBookMapper.class);

    @Named("mapEntityToResponse")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    OrderBookResponse mapEntityToResponse(OrderBookEntity customerEntity);

    @Named("mapEntityToRequest")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    OrderBookRequest mapEntityToRequest(OrderBookEntity customerEntity);

    @Named("mapEntityToRequest")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    OrderBookEntity mapRequestToEntity(OrderBookRequest customerRequest, @MappingTarget OrderBookEntity customerEntity);

    @Named("mapRequestToEntity")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    OrderBookEntity mapRequestToEntity(OrderBookRequest customerRequest);


    @IterableMapping(qualifiedByName = "mapEntityToResponse")
    @Named("mapEntityListToResponseList")
    List<OrderBookResponse> mapEntityListToResponseList(List<OrderBookEntity> entity);
}
