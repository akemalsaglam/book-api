package com.readingisgood.bookapi.domain.common.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<Entity, ID> {

    Entity save(Entity entity);

    List<Entity> findAll();

    Optional<Entity> findById(ID id);

    Optional<Entity> findActiveById(ID id);

}
