package com.readingisgood.bookapi.domain.common.jpa.auditing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class AuditingEntity implements Serializable {

    @Email
    private String createdByEmail;
    private ZonedDateTime createdTime;

    @Email
    private String updatedByEmail;
    private ZonedDateTime updatedTime;

    @Email
    private String deletedByEmail;
    private ZonedDateTime deletedTime;

}
