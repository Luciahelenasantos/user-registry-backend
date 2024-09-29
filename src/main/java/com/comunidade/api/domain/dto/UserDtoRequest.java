package com.comunidade.api.domain.dto;

import com.comunidade.api.domain.entities.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDtoRequest(

        @NotBlank
        String nome,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
        String cpf,

        @NotBlank
        @Email(message = "Email deve ser válido")
        String email,

        @NotBlank
        @Pattern(regexp = "\\(\\d{2}\\) 9?\\d{4}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX")
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato XXXXX-XXX")
        String cep,

        @NotBlank
        String numero,

        String complemento

) {
    public UserEntity toEntity() {
        return new UserEntity(
                nome,
                cpf,
                email,
                telefone,
                cep,
                null, // logradouro será preenchido depois
                null, // bairro será preenchido depois
                null, // cidade será preenchido depois
                null, // estado será preenchido depois
                numero,
                complemento
        );
    }
}
