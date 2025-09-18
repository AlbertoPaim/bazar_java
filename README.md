# 📦 Bazar API_BACKEND

API RESTful para gerenciamento de um **bazar online**, desenvolvida em **Spring Boot** com autenticação via **JWT** e controle de permissões com **Spring Security**.
Imagens são armazenadas em nuvem usando a **Cloudinary API**, otimizando o banco de dados Postgres com apenas URLs.

---

## 🔑 Funcionalidades

* **Autenticação & Autorização**

    * Registro e login de usuários.
    * Controle de acesso com **roles** (`ADMIN` e `CLIENT`).
    * Autenticação via **JWT**.

* **Gerenciamento de Itens**

    * Apenas **ADMINs** podem criar, atualizar e deletar itens.
    * Listagem de todos os itens (`GET /items`) e consulta por ID (`GET /items/{id}`) são **públicos**.

* **Gerenciamento de Usuários**

    * Registro de novos usuários (`/auth/register`).
    * Login para geração de token JWT (`/auth/login`).

* **Gerenciamento de Imagens**

    * Upload de imagens via **Cloudinary**.
    * URLs otimizadas armazenadas no banco.

---

## 📂 Entidades

### 🔹 User

* `id`
* `name`
* `login`
* `password` (criptografada com BCrypt)
* `role` (`ADMIN` ou `CLIENT`)

### 🔹 Item

* `id`
* `name`
* `description`
* `price`
* `category` (ENUM de categorias)
* `available`
* `images` (lista de URLs)

### 🔹 ImageItem

* `id`
* `imageUrl`
* `cloudinaryId` (Para gerenciar as imagens diretamente em nuvem)
* `item`

---

## 🔐 Rotas

### Autenticação

| Método | Endpoint         | Descrição              |
| ------ | ---------------- | ---------------------- |
| `POST` | `/auth/register` | Registrar novo usuário |
| `POST` | `/auth/login`    | Login e retorno de JWT |

### Itens

| Método   | Endpoint      | Permissão | Descrição             |
| -------- |---------------| --------- | --------------------- |
| `GET`    | `/itens`      | Público   | Listar todos os itens |
| `GET`    | `/itens/{id}` | Público   | Buscar item por ID    |
| `POST`   | `/itens`      | ADMIN     | Criar novo item       |
| `PUT`    | `/itens/{id}` | ADMIN     | Atualizar item        |
| `DELETE` | `/itens/{id}` | ADMIN     | Deletar item          |

---

## 🚀 Tecnologias usadas

* Spring Boot
* Spring Security
* JWT
* PostgreSQL
* Cloudinary API
* Maven

---
