package com.readingisgood.bookapi.domain.book.model;

import com.readingisgood.bookapi.domain.book.BookEntity;
import com.readingisgood.bookapi.domain.common.mapper.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<BookEntity, BookRequest, BookResponse> {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Named("mapEntityToResponse")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    BookResponse mapEntityToResponse(BookEntity bookEntity);

    @Named("mapCreateRequestToRequest")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    BookRequest mapCreateRequestToRequest(BookCreateRequest bookCreateRequest);

    @Named("mapUpdateRequestToRequest")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    BookRequest mapUpdateRequestToRequest(BookUpdateRequest bookUpdateRequest);

    @Named("mapRequestToEntity")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    BookEntity mapRequestToEntity(BookRequest bookRequest);

    @Named("mapRequestToEntity")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    BookEntity mapRequestToEntity(BookRequest bookRequest, @MappingTarget BookEntity bookEntity);

    @Named("mapEntityToRequest")
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    BookRequest mapEntityToRequest(BookEntity bookEntity);

    @IterableMapping(qualifiedByName = "mapEntityToResponse")
    @Named("mapEntityListToResponseList")
    List<BookResponse> mapEntityListToResponseList(List<BookEntity> entity);
}
