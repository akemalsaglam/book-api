package com.readingisgood.bookapi.domain.common.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface BaseController<
        Request extends BaseRequest,
        Response extends BaseResponse,
        ID> {

    Optional<Response> update(@Valid Request requestModel);

    Optional<Response> insert(@Valid Request requestModel);

    Optional<Response> getById(@Valid ID id);

    List<Response> getAll();

}
