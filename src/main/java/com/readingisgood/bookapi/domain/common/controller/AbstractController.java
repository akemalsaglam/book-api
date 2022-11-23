package com.readingisgood.bookapi.domain.common.controller;

import com.readingisgood.bookapi.domain.common.exception.ResourceNotFoundException;
import com.readingisgood.bookapi.domain.common.jpa.BaseEntity;
import com.readingisgood.bookapi.domain.common.jpa.Status;
import com.readingisgood.bookapi.domain.common.jpa.auditing.AuditingUtil;
import com.readingisgood.bookapi.domain.common.mapper.BaseMapper;
import com.readingisgood.bookapi.domain.common.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Optional<Response> getById(ID id) {
        final Optional<Entity> eventEntity = service.findActiveById(id);
        if (eventEntity.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return eventEntity.map(mapper::mapEntityToResponse);
    }

    @Override
    public List<Response> getAll() {
        return mapper.mapEntityListToResponseList(service.findAll());
    }

    @Override
    public Optional<Response> update(Request request) {
        Optional<Entity> optionalEntity = this.service.findById((ID) request.getId());
        if (optionalEntity.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            Entity entity = this.mapper.mapRequestToEntity(request, optionalEntity.get());
            AuditingUtil.setUpdateAuditInfo(entity);
            Entity updatedEntity = this.service.save(entity);
            return Optional.ofNullable(this.mapper.mapEntityToResponse(updatedEntity));
        }
    }

    @Override
    public Optional<Response> insert(Request eventRequest) {
        Entity eventEntity = mapper.mapRequestToEntity(eventRequest);
        AuditingUtil.setUpdateAuditInfo(eventEntity);
        final Entity insertedEntity = service.save(eventEntity);
        return Optional.ofNullable(mapper.mapEntityToResponse(insertedEntity));
    }

    @Override
    public void deleteById(ID id) {
        service.deleteById(id);
    }

    @Override
    public void softDeleteById(ID id) {
        final Optional<Entity> entity = service.findById(id);
        if (entity.isPresent()) {
            entity.get().setStatus(Status.PASSIVE.toString());
            AuditingUtil.setDeleteAuditInfo(entity.get());
            service.save(entity.get());
        }
        else{
            throw new ResourceNotFoundException();
        }
    }

}


