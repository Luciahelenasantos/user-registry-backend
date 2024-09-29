package com.comunidade.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserDtoResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String cep,
        String logradouro,
        String bairro,
        String cidade,
        String estado,
        String numero, // Adicionando o campo 'numero'
        String complemento, // Adicionando o campo 'complemento'

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        LocalDateTime dataCriacao
) {
}
