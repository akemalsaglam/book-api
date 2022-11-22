package com.readingisgood.bookapi.domain.common.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<Entity extends BaseEntity, ID> extends JpaRepository<Entity, ID> {

    List<Entity> findByStatusIs(String status);

    Optional<Entity> findByIdIsAndStatusIs(ID id, String status);
}
