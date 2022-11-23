package com.readingisgood.bookapi.domain.order;

import com.readingisgood.bookapi.domain.common.mapper.BaseMapper;
import com.readingisgood.bookapi.domain.customer.model.CustomerRequest;
import com.readingisgood.bookapi.domain.customer.model.CustomerUpdateRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity, OrderRequest, OrderResponse> {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Named("mapEntityToResponse")
    OrderResponse mapEntityToResponse(OrderEntity entity);

    @Named("mapEntityToRequest")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderRequest mapEntityToRequest(OrderEntity entity);

    @Named("mapCreateRequestToRequest")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    OrderRequest mapCreateRequestToRequest(OrderCreateRequest orderCreateRequest);

    @IterableMapping(qualifiedByName = "mapEntityToResponse")
    @Named("mapEntityListToResponseList")
    List<OrderResponse> mapEntityListToResponseList(List<OrderEntity> entity);
}
