package com.readingisgood.bookapi.domain.common.controller;

import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.jpa.BaseEntity;
import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.common.mapper.BaseMapper;
import com.readingisgood.bookapi.domain.common.service.BaseService;

import java.util.List;
import java.util.Optional;

public class AbstractController<Entity extends BaseEntity,
        Request extends BaseRequest, Response extends BaseResponse, ID>
        implements BaseController<Request, Response, ID> {

    private final BaseService<Entity, ID> service;
    private final BaseMapper<Entity, Request, Response> mapper;

    public AbstractController(BaseService<Entity, ID> service,
                              BaseMapper<Entity, Request, Response> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public Optional<Response> getById(ID id) throws ResourceNotFoundException {
        if (id == null) throw new ResourceNotFoundException();
        final Optional<Entity> eventEntity = service.findActiveById(id);
        if (eventEntity.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return eventEntity.map(mapper::mapEntityToResponse);
    }

    @Override
    public List<Response> getAll() {
        return mapper.mapEntityListToResponseList(service.findAll());
    }

    @Override
    public Optional<Response> update(Request request) throws ResourceNotFoundException {
        Optional<Entity> optionalEntity = this.service.findById((ID) request.getId());
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            Entity entity = this.mapper.mapRequestToEntity(request, optionalEntity.get());
            Entity updatedEntity = this.service.save(entity);
            return Optional.ofNullable(this.mapper.mapEntityToResponse(updatedEntity));
        }
    }

    @Override
    public Optional<Response> insert(Request eventRequest) {
        Entity eventEntity = mapper.mapRequestToEntity(eventRequest);
        final Entity insertedEntity = service.save(eventEntity);
        return Optional.ofNullable(mapper.mapEntityToResponse(insertedEntity));
    }

    @Override
    public void softDeleteById(ID id) throws ResourceNotFoundException {
        final Optional<Entity> entity = service.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(Status.PASSIVE.toString());
            service.save(entity.get());
        } else {
            throw new ResourceNotFoundException();
        }
    }

}


