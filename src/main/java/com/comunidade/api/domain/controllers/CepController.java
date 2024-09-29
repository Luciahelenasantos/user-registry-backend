package com.comunidade.api.domain.controllers;

import com.comunidade.api.domain.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cep")
public class CepController {

    @Autowired
    private ViaCepService viaCepService;

    @GetMapping("/{cep}")
    public ResponseEntity<?> buscarEnderecoPorCep(@PathVariable String cep) {
        // O serviço ViaCep retorna um Map<String, Object>
        Map<String, Object> endereco = viaCepService.buscarEnderecoPorCep(cep);

        if (endereco.containsKey("erro")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CEP não encontrado");
        }

        // Retornar o Map com os dados do endereço
        return ResponseEntity.ok(endereco);
    }
}
