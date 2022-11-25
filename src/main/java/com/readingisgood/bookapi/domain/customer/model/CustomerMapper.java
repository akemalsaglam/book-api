package com.readingisgood.bookapi.domain.customer.model;

import com.readingisgood.bookapi.domain.common.mapper.BaseMapper;
import com.readingisgood.bookapi.domain.customer.CustomerEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper extends BaseMapper<CustomerEntity, CustomerRequest, CustomerResponse> {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Named("mapEntityToResponse")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    CustomerResponse mapEntityToResponse(CustomerEntity customerEntity);

    @Named("mapEntityToRequest")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    CustomerEntity mapRequestToEntity(CustomerRequest customerRequest, @MappingTarget CustomerEntity customerEntity);

    @Named("mapRequestToEntity")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    CustomerEntity mapRequestToEntity(CustomerRequest customerRequest);

    @IterableMapping(qualifiedByName = "mapEntityToResponse")
    @Named("mapEntityListToResponseList")
    List<CustomerResponse> mapEntityListToResponseList(List<CustomerEntity> entity);
}
