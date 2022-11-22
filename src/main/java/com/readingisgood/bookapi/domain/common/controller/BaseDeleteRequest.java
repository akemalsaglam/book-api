package com.readingisgood.bookapi.domain.common.controller;

import com.readingisgood.bookapi.security.SecurityContextUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseDeleteRequest extends BaseRequest {
    private final @Email String deletedByEmail = SecurityContextUtil.getUserEmailFromContext();
    private final ZonedDateTime deletedTime = ZonedDateTime.now();
}
