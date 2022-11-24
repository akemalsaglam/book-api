package com.readingisgood.bookapi.domain.common.service;

import com.readingisgood.bookapi.domain.common.jpa.BaseEntity;
import com.readingisgood.bookapi.domain.common.jpa.BaseRepository;
import com.readingisgood.bookapi.domain.common.jpa.Status;

import java.util.List;
import java.util.Optional;


public class BaseDomainService<E extends BaseEntity, Id> implements BaseService<E, Id> {

    private final BaseRepository<E, Id> repository;

    public BaseDomainService(BaseRepository<E, Id> repository) {
        this.repository = repository;
    }


    @SuppressWarnings("unchecked")
    @Override
    public E save(E entity) {
        return repository.save(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> findAll() {
        return (List<E>) repository.findAll();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<E> findById(Id id) {
        return repository.findById(id);
    }

    @Override
    public Optional<E> findActiveById(Id id) {
        return repository.findByIdIsAndStatusIs(id, Status.ACTIVE.value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteById(Id id) {
        repository.deleteById(id);
    }
}
