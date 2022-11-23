package com.readingisgood.bookapi.domain.common.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseRequest implements Serializable {
    private Object id;
}
