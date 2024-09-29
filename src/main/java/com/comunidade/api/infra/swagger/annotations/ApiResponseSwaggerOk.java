package com.comunidade.api.infra.swagger.annotations;

import com.comunidade.api.infra.swagger.annotations.responses.ApiResponseBadRequestJson;
import com.comunidade.api.infra.swagger.annotations.responses.ApiResponseNotFoundJson;
import com.comunidade.api.infra.swagger.annotations.responses.ApiResponseOkJson;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ METHOD })
@ApiResponseOkJson
@ApiResponseNotFoundJson
@ApiResponseBadRequestJson
public @interface ApiResponseSwaggerOk {}