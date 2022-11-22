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
    BookResponse mapEntityToResponse(BookEntity entity);

    @Named("mapCreateRequestToRequest")
    BookRequest mapCreateRequestToRequest(BookCreateRequest bookCreateRequest);

    @Named("mapUpdateRequestToRequest")
    BookRequest mapUpdateRequestToRequest(BookUpdateRequest bookUpdateRequest);

    @Named("mapEntityToRequest")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookRequest mapEntityToRequest(BookEntity entity);

    @IterableMapping(qualifiedByName = "mapEntityToResponse")
    @Named("mapEntityListToResponseList")
    List<BookResponse> mapEntityListToResponseList(List<BookEntity> entity);
}
