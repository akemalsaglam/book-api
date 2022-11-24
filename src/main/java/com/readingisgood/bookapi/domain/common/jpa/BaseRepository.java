package com.readingisgood.bookapi.domain.common.jpa;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity, ID> extends PagingAndSortingRepository<Entity, ID> {

    Optional<Entity> findByIdIsAndStatusIs(ID id, String status);
}
