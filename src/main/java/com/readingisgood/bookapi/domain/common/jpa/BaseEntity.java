package com.readingisgood.bookapi.domain.common.jpa;

import com.readingisgood.bookapi.domain.common.jpa.auditing.AuditingEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class BaseEntity extends AuditingEntity {

    private String status;
}
