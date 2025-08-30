# 🚀 Spring Boot Custom Authentication & Authorization

[![SPRING FRAMEWORK](https://img.shields.io/badge/Spring%20framework-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://github.com/BerettaSM/exemplo-readme/blob/main/LICENSE)
[![JAVA](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://github.com/BerettaSM/exemplo-readme/blob/main/LICENSE) 
![GitHub repo size](https://img.shields.io/github/repo-size/BerettaSM/authentication?style=for-the-badge)

> O projeto foi desenvolvido como uma solução do desafio do [Backend-br](https://github.com/backend-br/desafios/blob/master/authentication/PROBLEM.md).

Este projeto é uma implementação de autenticação e autorização em **Spring Boot**, sem uso do **Spring Security** (exceto para o **PasswordEncoder**).
O objetivo é validar tokens de acesso recebidos via header HTTP e controlar o acesso a endpoints protegidos através de anotações personalizadas.

## 🔑 Principais Funcionalidades

- Cadastro e login de usuários

    - **POST /signup** cria um novo usuário (com senha criptografada via BCryptPasswordEncoder).

    - **POST /login** autentica o usuário e gera um token JWT válido.

- Interceptadores personalizados

    - **AuthorizationTokenInterceptor** → Extrai e valida o token do header Authorization e associa o usuário autenticado ao request.

    - **AuthenticationHandlerInterceptor** → Verifica se o endpoint acessado é protegido. Caso seja, checa se existe um usuário autenticado no request.

- Anotações customizadas

    - **@SecuredEndpoint** → marca endpoints que exigem autenticação.

    - **@Authentication** → injeta automaticamente o usuário autenticado nos parâmetros do controller.

- Resolver customizado

    - **AuthenticationResolver** → permite injeção direta do usuário autenticado em métodos de controllers, de forma semelhante ao **@AuthenticationPrincipal** do Spring Security.

## ⚙️ Fluxo de Autenticação

1) O cliente se cadastra via POST /signup.

2) Faz login via POST /login e recebe um JWT.

3) Em cada requisição a endpoints protegidos, o cliente envia o header:

```makefile
Authorization: Bearer <TOKEN>
```

4) O **AuthorizationTokenInterceptor** valida o token e associa o usuário ao request.

5) O **AuthenticationHandlerInterceptor** garante que apenas usuários autenticados acessem métodos anotados com **@SecuredEndpoint**.

## ☕ Tecnologias utilizadas

- Java 21
- Spring Boot (Web, Validation, Data JPA)
- H2 Database (para desenvolvimento)
- JWT (via java-jwt)
- Lombok (para simplificação do código)

## 💻 Pré-requisitos

Caso queira rodar este projeto na sua própria máquina, veja os requisitos abaixo:

- Java `21`

## 🔗 Variáveis de Ambiente

As seguintes variáveis de ambiente podem ser configuradas:

- CLIENT_ID: Um id para a aplicacao (default: myclientid).
- JWT_SECRET: Um secret usado para assinar/verificar os tokens (default: mytokensecret).
- JWT_DURATION_IN_SECONDS: O tempo para o token expirar, em segundos (default: 86400).

## 🚀 Rodando o projeto

Para rodar o projeto, siga estas etapas:

1. Clone o repositório:
```bash
git clone https://github.com/BerettaSM/authentication.git
```

2. Entre na pasta raiz do projeto
```bash
cd authentication/
```

3. Execute o projeto com **Maven**:
```bash
./mvn spring-boot:run
```

## 📡 Endpoints da API


#### ➕ Criar usuário

- Endpoint:
```http
  POST /signup
  Content-Type: application/json
```

- Request Body (JSON):
```json
    {
        "name": "John Doe",
        "email": "user@example.com",
        "password": "!Abc1234"
    }
```

- Exemplo de requisição:

```bash
curl -XPOST \
    -H "Content-Type: application/json" \
    -H "Accept: application/json" \
    -d '{ "name": "John Doe", "email": "user@example.com", "password": "!Abc1234" }' \
    http://localhost:8080/signup
```

- Respostas:

> - **201**: Usuário cadastrado com sucesso.
> - **409**: Email já cadastrado.
> - **422**: Falha de validação (senha não atende aos critérios mínimos, etc.).

- Exemplo de resposta:

```json
    {
        "name": "John Doe",
        "email": "user@example.com"
    }
```

| Parâmetro  | Tipo     | Descrição                                           |
|:-----------|:---------|:----------------------------------------------------|
| `name`     | `string` | **Obrigatório**. Um nome de usuário                 |
| `email`    | `string` | **Obrigatório**. Um email nao cadastrado            |
| `password` | `string` | **Obrigatório**. A senha do usuário                 |

#### 📍 Login

- Endpoint:
```http
  POST /login
  Content-Type: application/json
```

- Request Body (JSON):
```json
    {
        "email": "user@example.com",
        "password": "!Abc1234"
    }
```

- Exemplo de requisição:

```bash
curl -XPOST \
    -H "Content-Type: application/json" \
    -H "Accept: application/json" \
    -d '{ "email": "user@example.com", "password": "!Abc1234" }' \
    http://localhost:8080/login
```

- Respostas:

> - **200**: Sucesso no login.
> - **401**: Senha incorreta/usuário não cadastrado.

- Exemplo de resposta:

```json
    {
        "token_type": "Bearer",
        "access_token": "eyJhbGciOiJIUzI1NiIs...",
        "expires_in": 1756600513
    }
```

#### 📋 Endpoint protegido

- Endpoint:
```http
  GET /secured
  Authorization: Bearer <TOKEN>
```

- Exemplo de requisição:

```bash
curl -XGET \
    -H"Content-Type: application/json" \
    -H"Accept: application/json" \
    -H"Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5c..." \
    localhost:8080/secured
```

- Respostas:

> - **200**: Sucesso.
> - **401**: Usuário não autenticado/token inválido.

- Exemplo de resposta:

```json
    {
        "email": "user@example.com",
        "name": "John Doe",
    }
```

#### 📍 Endpoint público

- Endpoint:
```http
  GET /unsecured
```

- Exemplo de requisição:

```bash
curl -XGET \
    -H"Accept: application/json" \
    localhost:8080/unsecured
```

- Respostas:

> - **200**: Sucesso.

- Exemplo de resposta:

```json
    {
        "name": "Anonymous"
    }
```

## 📄 Licença

Este projeto é licenciado sob os termos da [MIT License](LICENSE).