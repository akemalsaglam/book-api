package com.readingisgood.bookapi.domain.common.jpa.auditing;


import com.readingisgood.bookapi.domain.common.controller.BaseRequest;
import com.readingisgood.bookapi.domain.common.jpa.BaseEntity;
import com.readingisgood.bookapi.security.SecurityContextUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuditingUtil {

    public static void setCreateAuditInfo(BaseEntity baseEntity) {
        baseEntity.setCreatedByEmail(SecurityContextUtil.getUserEmailFromContext());
        baseEntity.setCreatedTime(ZonedDateTime.now());
    }

    public static void setUpdateAuditInfo(BaseEntity baseEntity) {
        baseEntity.setUpdatedByEmail(SecurityContextUtil.getUserEmailFromContext());
        baseEntity.setUpdatedTime(ZonedDateTime.now());
    }

    public static void setDeleteAuditInfo(BaseEntity baseEntity) {
        baseEntity.setDeletedByEmail(SecurityContextUtil.getUserEmailFromContext());
        baseEntity.setDeletedTime(ZonedDateTime.now());
    }

    public static void preserveCreateAuditInfo(BaseEntity sourceEntity, BaseEntity targetEntity) {
        targetEntity.setCreatedTime(sourceEntity.getCreatedTime());
        targetEntity.setCreatedByEmail(sourceEntity.getCreatedByEmail());
    }

    public static void setCreateAuditInfo(BaseRequest baseRequest) {
        baseRequest.setCreatedByEmail(SecurityContextUtil.getUserEmailFromContext());
        baseRequest.setCreatedTime(ZonedDateTime.now());
    }

    public static void setUpdateAuditInfo(BaseRequest baseRequest) {
        baseRequest.setUpdatedByEmail(SecurityContextUtil.getUserEmailFromContext());
        baseRequest.setUpdatedTime(ZonedDateTime.now());
    }

    public static void setDeleteAuditInfo(BaseRequest baseRequest) {
        baseRequest.setDeletedByEmail(SecurityContextUtil.getUserEmailFromContext());
        baseRequest.setDeletedTime(ZonedDateTime.now());
    }

}
