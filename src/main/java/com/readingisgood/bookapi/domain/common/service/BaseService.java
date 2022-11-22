package com.readingisgood.bookapi.domain.common.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<Entity, ID> {

    Entity save(Entity entity);

    Entity saveAndFlush(Entity entity);

    List<Entity> findAll();

    List<Entity> findAllActive();

    Optional<Entity> findById(ID id);

    Optional<Entity> findActiveById(ID id);

    void deleteById(ID id);

}
