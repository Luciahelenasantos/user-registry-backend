package com.comunidade.api.infra.utils;

import com.comunidade.api.infra.handler.MessageErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.comunidade.api.domain.exceptions.BusinessException;
import com.comunidade.api.domain.exceptions.EntidadeNaoEncontrada;

public class SpringControllerUtils {

    /**
     * Gera uma ResponseEntity para Controllers, com tratamento padrão de erros
     *
     * @param httpStatus Status HTTP a ser retornado quando a operação for bem-sucedida
     * @param acao       Ação que deve ser executada
     * @return ResponseEntity de acordo com o resultado da ação
     */
    public static ResponseEntity<?> response(HttpStatus httpStatus, GerarResponse<?> acao) {
        try {
            if (httpStatus == HttpStatus.NO_CONTENT) {
                acao.get();
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.status(httpStatus).body(acao.get());
        } catch (EntidadeNaoEncontrada e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(MessageErrorHandler.create(e.getMessage()));
        } catch (BusinessException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MessageErrorHandler.create(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageErrorHandler.create(ex.getMessage()));
        }
    }
}
