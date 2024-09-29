package com.comunidade.api.domain.entities;

import com.comunidade.api.domain.dto.UserDtoResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String nome;

    @Column(length = 14, nullable = false, unique = true)
    private String cpf;

    @Column(length = 200, nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false)
    private String telefone;

    @Column(length = 9, nullable = false)
    private String cep;

    @Column(length = 200, nullable = false)
    private String logradouro;

    @Column(length = 100, nullable = false)
    private String bairro;

    @Column(length = 100, nullable = false)
    private String cidade;

    @Column(length = 2, nullable = false)
    private String estado;

    @Column(length = 10, nullable = false) // Adicionando o campo 'numero'
    private String numero;

    @Column(length = 100) // Adicionando o campo 'complemento'
    private String complemento;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime dataCriacao;

    // Construtor com os parâmetros, incluindo 'numero' e 'complemento'
    public UserEntity(String nome, String cpf, String email, String telefone, String cep,
                       String logradouro, String bairro, String cidade, String estado,
                       String numero, String complemento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.complemento = complemento;
    }

    public UserDtoResponse toDto() {
        return new UserDtoResponse(
                id,
                nome,
                cpf,
                email,
                telefone,
                cep,
                logradouro,
                bairro,
                cidade,
                estado,
                numero,
                complemento,
                dataCriacao
        );
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", cep='" + cep + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
