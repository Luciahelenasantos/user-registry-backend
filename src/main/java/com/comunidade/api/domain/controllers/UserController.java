package com.comunidade.api.domain.controllers;

import com.comunidade.api.domain.dto.UserDtoRequest;
import com.comunidade.api.domain.services.UserService;
import com.comunidade.api.infra.utils.SpringControllerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Rotas para gerenciamento dos usuários")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários")
    public ResponseEntity<?> listarUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.listarUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    public ResponseEntity<?> buscarUserPorId(@PathVariable Long id) {
        return SpringControllerUtils.response(HttpStatus.OK, () -> userService.buscarUserPorId(id));
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Buscar usuário por CPF")
    public ResponseEntity<?> buscarUserPorCpf(@PathVariable String cpf) {
        return SpringControllerUtils.response(HttpStatus.OK, () -> userService.buscarUserPorCpf(cpf));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por Email")
    public ResponseEntity<?> buscarUserPorEmail(@PathVariable String email) {
        return SpringControllerUtils.response(HttpStatus.OK, () -> userService.buscarUserPorEmail(email));
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo usuário")
    public ResponseEntity<?> cadastrarUser(@RequestBody UserDtoRequest adminDto) {
        return SpringControllerUtils.response(HttpStatus.CREATED, () -> userService.cadastrarUser(adminDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um usuário pelo ID")
    public ResponseEntity<?> atualizarUser(@PathVariable Long id, @RequestBody UserDtoRequest adminDto) {
        return SpringControllerUtils.response(HttpStatus.OK, () -> userService.atualizarUser(id, adminDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um usuário pelo ID")
    public ResponseEntity<?> excluirUser(@PathVariable Long id) {
        return SpringControllerUtils.response(HttpStatus.NO_CONTENT, () -> {
            userService.excluirUser(id);
            return null;
        });
    }
}
