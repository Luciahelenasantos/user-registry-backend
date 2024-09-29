package com.comunidade.api.infra.utils;

import com.comunidade.api.domain.exceptions.BusinessException;

@FunctionalInterface
public interface GerarResponse<T> {

    T get() throws BusinessException;
}
