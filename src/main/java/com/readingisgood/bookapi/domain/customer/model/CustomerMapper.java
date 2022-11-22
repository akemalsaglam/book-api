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
    CustomerResponse mapEntityToResponse(CustomerEntity entity);

    @Named("mapEntityToRequest")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CustomerRequest mapEntityToRequest(CustomerEntity entity);

    @IterableMapping(qualifiedByName = "mapEntityToResponse")
    @Named("mapEntityListToResponseList")
    List<CustomerResponse> mapEntityListToResponseList(List<CustomerEntity> entity);
}
