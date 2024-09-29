package com.comunidade.api.domain.repositories;

import com.comunidade.api.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByCpf(String cpf); // Buscar User por CPF
    UserEntity findByEmail(String email); // Buscar User por email
    boolean existsByCpf(String cpf); // Verificar se o CPF já existe
    boolean existsByEmail(String email); // Verificar se o email já existe
}
