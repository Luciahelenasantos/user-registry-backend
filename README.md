# Sistema de Gerenciamento de Usuários

Este projeto consiste em uma API de backend desenvolvida em Java com Spring Boot para o gerenciamento de usuários. A aplicação inclui operações de criação, consulta, atualização e exclusão (CRUD) de usuários e está configurada para ser executada em um ambiente Docker, oferecendo fácil configuração e deploy. A API também possui documentação interativa disponível via Swagger.
- O Desenho de Arquitetura está na raiz do projeto no arquivo: Desenho de Arquitetura.png

## Funcionalidades

- Cadastro de novos usuários.
- Consulta de todos os usuários por ID.
- Consulta de usuários por e-mail
- Consulta de usuários por cpf
- Atualização de dados de um usuário.
- Exclusão de um usuário.
- Consumo da API Externa ViaCEP.
- Documentação da API com Swagger.

## Pré-requisitos

### Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:

- [Git](https://git-scm.com)
- [Docker](https://www.docker.com)

### Tecnologias Utilizadas

- **Backend**: Java 17, Spring Boot 3.2.8
- **Banco de Dados**: PostgreSQL 16.3
- **Documentação da API**: Swagger OpenAPI 3.0
- **API Externa**: ViaCEP para busca de endereços.

## Configuração e Execução

### Passos para rodar o projeto em Docker:

1. Clone o repositório:

   ```bash
   git clone https://github.com/Luciahelenasantos/user-registry-backend.git
   cd user-registry-backend
   
2. Execute o Docker Compose para subir a aplicação e o banco de dados:

    ```bash
    docker-compose up --build
    ```
    Isso irá criar dois containers:
   - Um para a aplicação Spring Boot rodando na porta 8080.
   - Outro para o PostgreSQL rodando na porta 5432.


3. Acesse a documentação da API com Swagger:
    - Abra seu navegador e vá até http://localhost:8080/swagger-ui.html para visualizar e testar os endpoints da API.

## Consumo da API Externa ViaCEP
A aplicação está integrada com a API ViaCEP para obter os dados de endereço a partir do CEP informado pelos usuários no momento do cadastro.
Abaixo está a rota usada para buscar os dados do CEP:

### Endpoint ViaCEP
- GET /cep/{cep}: Busca os dados de endereço fornecidos pela API ViaCEP para o CEP informado.

## Endpoints da API
Aqui estão os principais endpoints da API para gerenciamento de usuários:

- GET /users: Retorna a lista de todos os usuários cadastrados.
- GET /users/{id}: Retorna os detalhes de um usuário específico.
- GET /users/email/{email}: Retorna os detalhes de um usuário específico.
- GET /users/cpf/{cpf}: Retorna os detalhes de um usuário específico.
- POST /users: Cria um novo usuário.
- PUT /users/{id}: Atualiza os dados de um usuário.
- DELETE /users/{id}: Remove um usuário.
