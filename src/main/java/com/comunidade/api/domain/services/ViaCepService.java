package com.comunidade.api.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ViaCepService {

    private final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public Map<String, Object> buscarEnderecoPorCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(VIA_CEP_URL, Map.class, cep);
    }
}
