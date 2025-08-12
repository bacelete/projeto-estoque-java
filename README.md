# 📦 API RESTful de Controle de Estoque com Spring Boot + JWT

Sistema completo para controle de estoque, com autenticação JWT, desenvolvido com **Spring Boot** no backend e **React** no frontend (em desenvolvimento).

---

## 🚀 Tecnologias Utilizadas

### 🔧 Backend:
- Java 21
- Spring Boot 3.5.3
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- MySQL
- Maven

### 💅 Frontend (em desenvolvimento):
- React.js
- Next.js
- Material UI
- Fetch API (com `credentials: "include"`)

---

## 📚 Funcionalidades

✅ CRUD completo de:
- Usuários (com autenticação e permissões)
- Produtos
- Categorias
- Fornecedores
- Movimentações de estoque

✅ Arquitetura organizada:
- `Model`, `DTO`, `Controller`, `Service`, `Repository`, `Security`, `Exception`

✅ Segurança:
- Autenticação com JWT armazenado em **cookies HTTPOnly**
- Filtros de segurança com `OncePerRequestFilter`
- Permissões de acesso baseadas em roles

✅ Boas práticas:
- DTOs para controle de dados de entrada e saída
- Validação com `javax.validation`
- Tratamento global de exceções com `@ControllerAdvice`
- Integração com banco de dados via Spring Data JPA (MySQL)

---

## 🔐 Autenticação JWT

- Rota de login: `/auth/login`
- Rota de registro: `/auth/register`
- O token é armazenado no **cookie**
- Middleware intercepta e valida o token em cada requisição

---

## 🧪 Exemplos de Endpoints

| Método | Endpoint             | Descrição                  |
|--------|----------------------|----------------------------|
| POST   | `/auth/login`        | Login do usuário           |
| GET    | `/produto/all`       | Listar todos os produtos   |
| POST   | `/produto`           | Criar novo produto         |
| PUT    | `/produto/{id}`      | Atualizar produto          |
| DELETE | `/produto/{id}`      | Deletar produto            |
| GET    | `/categoria/all`     | Listar todas as categorias |

---

## 📁 Estrutura do Projeto (Backend)

