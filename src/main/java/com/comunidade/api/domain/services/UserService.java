package com.comunidade.api.domain.services;

import com.comunidade.api.domain.dto.UserDtoRequest;
import com.comunidade.api.domain.dto.UserDtoResponse;
import com.comunidade.api.domain.entities.UserEntity;
import com.comunidade.api.domain.exceptions.BusinessException;
import com.comunidade.api.domain.exceptions.EntidadeNaoEncontrada;
import com.comunidade.api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ViaCepService viaCepService;

    @Autowired
    public UserService(UserRepository adminRepository, ViaCepService viaCepService) {
        this.userRepository = adminRepository;
        this.viaCepService = viaCepService;
    }

    // Buscar User por ID
    private UserEntity findById(Long id) throws BusinessException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontrada("Usuário com ID " + id + " não encontrado"));
    }

    // Buscar User por CPF
    public UserDtoResponse buscarUserPorCpf(String cpf) throws BusinessException {
        UserEntity user = userRepository.findByCpf(cpf);
        if (user == null) {
            throw new EntidadeNaoEncontrada("Usuário com CPF " + cpf + " não encontrado");
        }
        return user.toDto();
    }

    // Buscar User por Email
    public UserDtoResponse buscarUserPorEmail(String email) throws BusinessException {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntidadeNaoEncontrada("Usuário com Email " + email + " não encontrado");
        }
        return user.toDto();
    }

    // Verificar se há duplicidade de CPF ou email
    private void verificaDuplicidade(UserDtoRequest dto, UserEntity userExistente) throws BusinessException {
        if (userExistente != null) {
            if (!userExistente.getCpf().equals(dto.cpf()) && userRepository.existsByCpf(dto.cpf())) {
                throw new BusinessException("CPF já cadastrado.");
            }
            if (!userExistente.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
                throw new BusinessException("Email já cadastrado.");
            }
        } else {
            if (userRepository.existsByCpf(dto.cpf())) {
                throw new BusinessException("CPF já cadastrado.");
            }
            if (userRepository.existsByEmail(dto.email())) {
                throw new BusinessException("Email já cadastrado.");
            }
        }
    }

    // Validar todos os dados do UserDtoRequest
    private void validarUserDto(UserDtoRequest dto, UserEntity userExistente) throws BusinessException {
        if (dto.nome().isBlank()) throw new BusinessException("Nome não pode ser vazio.");
        if (dto.cpf().isBlank() || !dto.cpf().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new BusinessException("CPF inválido ou vazio. Deve estar no formato XXX.XXX.XXX-XX.");
        }
        if (dto.email().isBlank() || !dto.email().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new BusinessException("Email inválido ou vazio. Formato esperado: exemplo@dominio.com.");
        }
        if (dto.cep().isBlank() || !dto.cep().matches("\\d{5}-\\d{3}")) {
            throw new BusinessException("CEP inválido ou vazio. Deve estar no formato XXXXX-XXX.");
        }

        // Verifica duplicidade de CPF e email
        verificaDuplicidade(dto, userExistente);
    }

    // Listar todos os users
    public List<UserDtoResponse> listarUsers() {
        return userRepository.findAll().stream().map(UserEntity::toDto).toList();
    }

    // Buscar User por ID
    public UserDtoResponse buscarUserPorId(Long id) throws BusinessException {
        UserEntity user = findById(id);
        return user.toDto();
    }

    // Cadastrar um novo User
    public UserDtoResponse cadastrarUser(UserDtoRequest userDto) throws BusinessException {
        validarUserDto(userDto, null);  // Valida os dados do novo usuário

        // Buscar dados de endereço pela API ViaCep
        Map<String, Object> endereco = viaCepService.buscarEnderecoPorCep(userDto.cep());

        // Verificar se o CEP foi encontrado e popular os campos de endereço
        if (endereco.containsKey("erro")) {
            throw new BusinessException("CEP inválido ou não encontrado.");
        }

        UserEntity novoUser = userDto.toEntity();

        // Popular os campos de endereço com os dados da API ViaCEP
        novoUser.setLogradouro((String) endereco.get("logradouro"));
        novoUser.setBairro((String) endereco.get("bairro"));
        novoUser.setCidade((String) endereco.get("localidade"));
        novoUser.setEstado((String) endereco.get("uf"));
        novoUser.setNumero(userDto.numero());
        novoUser.setComplemento(userDto.complemento()); // Adicionando complemento

        UserEntity userSalvo = userRepository.save(novoUser);
        return userSalvo.toDto();
    }

    // Atualizar User existente
    public UserDtoResponse atualizarUser(Long id, UserDtoRequest userDto) throws BusinessException {
        UserEntity userExistente = findById(id);
        validarUserDto(userDto, userExistente);  // Valida os dados atualizados

        // Buscar dados de endereço pela API ViaCep
        Map<String, Object> endereco = viaCepService.buscarEnderecoPorCep(userDto.cep());

        // Verificar se o CEP foi encontrado e popular os campos de endereço
        if (endereco.containsKey("erro")) {
            throw new BusinessException("CEP inválido ou não encontrado.");
        }

        // Atualizar os dados do User
        userExistente.setNome(userDto.nome());
        userExistente.setCpf(userDto.cpf());
        userExistente.setEmail(userDto.email());
        userExistente.setTelefone(userDto.telefone());
        userExistente.setCep(userDto.cep());
        userExistente.setNumero(userDto.numero());
        userExistente.setComplemento(userDto.complemento()); // Adicionando complemento

        // Atualizar os campos de endereço com os dados obtidos da API ViaCep
        userExistente.setLogradouro((String) endereco.get("logradouro"));
        userExistente.setBairro((String) endereco.get("bairro"));
        userExistente.setCidade((String) endereco.get("localidade"));
        userExistente.setEstado((String) endereco.get("uf"));

        UserEntity userAtualizado = userRepository.save(userExistente);
        return userAtualizado.toDto();
    }


    // Excluir User por ID
    public void excluirUser(Long id) throws BusinessException {
        UserEntity user = findById(id);
        userRepository.delete(user);
    }
}
