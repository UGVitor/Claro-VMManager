# Claro-VMManager

API REST para gerenciamento de máquinas virtuais, construída com **Spring Boot**, incluindo sistema de autenticação JWT e documentação completa via **Swagger**.

---

## 📋 Índice

- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração](#configuração)
- [Como Executar](#como-executar)
- [Endpoints](#endpoints)
- [Autenticação](#autenticação)
- [Sistema de Auditoria](#sistema-de-auditoria)
- [Documentação API](#documentação-api)

---

## 🛠 Tecnologias

- **Java 17**
- **Spring Boot 4.0.1**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Swagger / OpenAPI (springdoc-openapi)**
- **Maven**
- **Lombok**
- **Bean Validation**

---

## ✨ Funcionalidades

### Autenticação e Autorização
- ✅ Registro de usuários
- ✅ Login com JWT
- ✅ Autenticação baseada em token
- ✅ Criptografia de senhas com BCrypt

### Gerenciamento de Máquinas Virtuais
- ✅ **CRUD completo** de máquinas virtuais
- ✅ Validações de entrada:
    - Nome não nulo, mínimo 5 caracteres
    - CPU, RAM, memória e disco positivos
- ✅ Controle de status (`RUNNING`, `STOPPED`, `SUSPENDED`)
- ✅ Atualização parcial de recursos
- ✅ Data de criação automática

### Sistema de Auditoria
- ✅ **Registro automático** de todas as ações executadas nas VMs
- ✅ Rastreamento de operações:
    - Criação de VMs (`CREATE VM`)
    - Atualização de VMs (`UPDATE VM`)
    - Atualização de status (`UPDATE STATUS VM`)
    - Exclusão de VMs (`DELETE VM`)
- ✅ Histórico completo com:
    - Usuário que executou a ação
    - Nome da VM afetada
    - Tipo de ação realizada
    - Data e hora da execução
- ✅ Consulta de histórico de auditoria via API

---

## 📁 Estrutura do Projeto

```
src/
 └─ main/
     ├─ java/com/claro/vmmanager/
     │   ├─ controllers/          # Endpoints REST
     │   │   ├─ AuthController.java
     │   │   ├─ VirtualMachineController.java
     │   │   └─ VmTaskExecutionController.java
     │   ├─ services/             # Lógica de negócio
     │   │   ├─ VirtualMachineService.java
     │   │   └─ VmTaskExecutionService.java
     │   ├─ models/               # Entidades JPA
     │   │   ├─ User.java
     │   │   ├─ VirtualMachine.java
     │   │   ├─ VmTaskExecution.java
     │   │   └─ enums/
     │   │       └─ Status.java
     │   ├─ repositories/         # Repositórios JPA
     │   │   ├─ UserRepository.java
     │   │   ├─ VirtualMachineRepository.java
     │   │   └─ VmTaskExecutionRepository.java
     │   ├─ dto/                  # Data Transfer Objects
     │   │   ├─ LoginRequestDTO.java
     │   │   ├─ RegisterRequestDTO.java
     │   │   ├─ UserResponseDTO.java
     │   │   ├─ VirtualMachineRequestDTO.java
     │   │   ├─ VirtualMachineResponseDTO.java
     │   │   ├─ VirtualMachineUpdateDTO.java
     │   │   ├─ VirtualMachineUpdateStatusDTO.java
     │   │   └─ VmTaskExecutionResponseDTO.java
     │   └─ infra/                # Configurações de infraestrutura
     │       ├─ security/         # Configurações de segurança
     │       │   ├─ SecurityConfig.java
     │       │   ├─ SecurityFilter.java
     │       │   ├─ TokenService.java
     │       │   └─ CustomUserDetailsService.java
     │       ├─ cors/             # Configuração CORS
     │       │   └─ CorsConfig.java
     │       └─ swagger/          # Configuração Swagger
     │           ├─ SwaggerConfiguration.java
     │           └─ SwaggerWebMvcConfig.java
     └─ resources/
         └─ application.properties
```

---

## ⚙️ Configuração

### Propriedades da Aplicação

O arquivo `application.properties` contém as configurações principais:

```properties
spring.application.name=vmmanager

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/VMManagerDB
spring.datasource.username=postgres
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Secret Key
api.security.token.secret=chavesupersecreta
```

### Configuração do Banco de Dados

1. **Instale o PostgreSQL** (se ainda não tiver)
2. **Crie o banco de dados:**
   ```sql
   CREATE DATABASE VMManagerDB;
   ```
3. **Atualize as credenciais** no arquivo `application.properties`:
   - `spring.datasource.username`
   - `spring.datasource.password`
   - `spring.datasource.url` (se necessário)

### Configuração de Segurança

⚠️ **Importante:** Altere o valor de `api.security.token.secret` no `application.properties` para uma chave secreta forte em produção.

---

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL instalado e rodando
- Banco de dados `VMManagerDB` criado

### Passos

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd Claro-VMManager
   ```

2. **Configure o banco de dados:**
   - Edite `src/main/resources/application.properties`
   - Ajuste as credenciais do PostgreSQL

3. **Execute a aplicação:**

   **Opção 1 - Via IDE:**
   - Abra o projeto em sua IDE
   - Execute a classe `VmmanagerApplication`
   - Ou use o atalho: `Ctrl + F5`

   **Opção 2 - Via Maven:**
   ```bash
   mvn spring-boot:run
   ```
   
   **Windows (PowerShell):**
   ```powershell
   .\mvnw spring-boot:run
   ```

4. **Acesse a aplicação:**
   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## 🔌 Endpoints

### Autenticação

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `POST` | `/auth/v1/register` | Registra um novo usuário | Não requerida |
| `POST` | `/auth/v1/login` | Autentica e retorna JWT | Não requerida |

### Máquinas Virtuais

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `GET` | `/vm/v1` | Lista todas as VMs | Não requerida* |
| `GET` | `/vm/v1/{id}` | Busca VM por ID | Não requerida* |
| `POST` | `/vm/v1` | Cria uma nova VM | Não requerida* |
| `PATCH` | `/vm/v1/{id}` | Atualiza dados da VM | Não requerida* |
| `PATCH` | `/vm/v1/status/{id}` | Atualiza status da VM | Não requerida* |
| `DELETE` | `/vm/v1/{id}` | Remove uma VM | Não requerida* |

### Auditoria

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `GET` | `/tasks/v1` | Lista todas as execuções de tarefas (histórico de auditoria) | Não requerida* |

*Atualmente configurado como público. Recomenda-se adicionar autenticação em produção.

---

## 🔐 Autenticação

### Registro de Usuário

**Request:**
```json
POST /auth/v1/register
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123"
}
```

**Response:**
```json
{
  "name": "João Silva",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Login

**Request:**
```json
POST /auth/v1/login
Content-Type: application/json

{
  "email": "joao@example.com",
  "password": "senha123"
}
```

**Response:**
```json
{
  "name": "João Silva",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Uso do Token

Para requisições autenticadas, inclua o token no header:
```
Authorization: Bearer <seu_token_jwt>
```

---

## 📊 Sistema de Auditoria

O sistema de auditoria **VmTaskExecution** registra automaticamente todas as operações realizadas nas máquinas virtuais, fornecendo um histórico completo de ações para rastreabilidade e compliance.

### Como Funciona

O sistema registra automaticamente as seguintes ações:

1. **Criação de VM** - Quando uma nova máquina virtual é criada
2. **Atualização de VM** - Quando os recursos (CPU, RAM, memória, disco) ou nome são alterados
3. **Atualização de Status** - Quando o status da VM é modificado (RUNNING, STOPPED, SUSPENDED)
4. **Exclusão de VM** - Quando uma máquina virtual é removida

### Informações Registradas

Cada registro de auditoria contém:
- **Username**: Identificação do usuário que executou a ação (atualmente fixo como "admin")
- **VM Name**: Nome da máquina virtual afetada
- **Action**: Tipo de ação executada
- **Executed At**: Data e hora exata da execução (gerado automaticamente)

### Consulta do Histórico

O histórico completo pode ser consultado através do endpoint `/tasks/v1`, que retorna todas as execuções de tarefas registradas no sistema.

**Exemplo de uso:**
```bash
GET http://localhost:8080/tasks/v1
```

**Benefícios:**
- ✅ Rastreabilidade completa de operações
- ✅ Auditoria para compliance e segurança
- ✅ Histórico para troubleshooting
- ✅ Registro automático sem intervenção manual

---

## 📚 Documentação API

A documentação completa da API está disponível via **Swagger UI** após iniciar a aplicação:

🔗 **[Swagger UI](http://localhost:8080/swagger-ui/index.html)**

O Swagger fornece:
- Lista completa de endpoints
- Modelos de requisição e resposta
- Possibilidade de testar endpoints diretamente
- Esquemas de validação

---

## 📝 Exemplos de Uso

### Criar uma Máquina Virtual

```json
POST /vm/v1
Content-Type: application/json

{
  "name": "VM-Producao-01",
  "cpu": 4,
  "ram": 8.0,
  "memory": 16.0,
  "disk": 100.0
}
```

### Atualizar Status

```json
PATCH /vm/v1/status/1
Content-Type: application/json

{
  "status": "STOPPED"
}
```

### Atualizar Recursos

```json
PATCH /vm/v1/1
Content-Type: application/json

{
  "name": "VM-Producao-01-Updated",
  "cpu": 8,
  "ram": 16.0
}
```

### Consultar Histórico de Auditoria

```json
GET /tasks/v1
```

**Response:**
```json
[
  {
    "username": "admin",
    "vmName": "VM-Producao-01",
    "action": "CREATE VM",
    "executedAt": "2026-01-23T10:30:00"
  },
  {
    "username": "admin",
    "vmName": "VM-Producao-01",
    "action": "UPDATE STATUS VM to STOPPED",
    "executedAt": "2026-01-23T11:15:00"
  },
  {
    "username": "admin",
    "vmName": "VM-Producao-01",
    "action": "UPDATE VM",
    "executedAt": "2026-01-23T12:00:00"
  }
]
```

---

## 🗄️ Modelo de Dados

### VirtualMachine

| Campo | Tipo | Descrição | Validações |
|-------|------|-----------|------------|
| `id` | Long | ID único (gerado automaticamente) | - |
| `name` | String | Nome da VM | Não nulo, mínimo 5 caracteres |
| `cpu` | Integer | Número de CPUs | Não nulo, positivo |
| `ram` | BigDecimal | RAM em GB | Não nulo, positivo |
| `memory` | BigDecimal | Memória em GB | Não nulo, positivo |
| `disk` | BigDecimal | Disco em GB | Não nulo, positivo |
| `status` | Status | Status da VM | RUNNING, STOPPED, SUSPENDED |
| `dataCriacao` | LocalDateTime | Data de criação | Gerado automaticamente |

### User

| Campo | Tipo | Descrição | Validações |
|-------|------|-----------|------------|
| `id` | String (UUID) | ID único | Gerado automaticamente |
| `name` | String | Nome do usuário | Não nulo, não vazio |
| `email` | String | Email do usuário | Não nulo, formato válido |
| `password` | String | Senha (criptografada) | Não nulo, não vazio |

### Status (Enum)

- `RUNNING` - Máquina em execução
- `STOPPED` - Máquina parada
- `SUSPENDED` - Máquina suspensa

### VmTaskExecution (Auditoria)

| Campo | Tipo | Descrição | Validações |
|-------|------|-----------|------------|
| `id` | Long | ID único (gerado automaticamente) | - |
| `username` | String | Nome do usuário que executou a ação | - |
| `vmName` | String | Nome da VM afetada | - |
| `action` | String | Tipo de ação executada | CREATE VM, UPDATE VM, UPDATE STATUS VM, DELETE VM |
| `executedAt` | LocalDateTime | Data e hora da execução | Gerado automaticamente |

**Ações registradas automaticamente:**
- `CREATE VM` - Quando uma nova VM é criada
- `UPDATE VM` - Quando os dados de uma VM são atualizados
- `UPDATE STATUS VM to {STATUS}` - Quando o status de uma VM é alterado
- `DELETE VM` - Quando uma VM é excluída

---

## 🔒 Segurança

- ✅ Senhas criptografadas com BCrypt
- ✅ Autenticação JWT (stateless)
- ✅ Validação de dados de entrada
- ✅ CORS configurado
- ✅ Spring Security implementado

**⚠️ Recomendações para Produção:**
- Alterar a chave secreta JWT (`api.security.token.secret`)
- Habilitar autenticação obrigatória para endpoints de VM e auditoria
- Usar HTTPS
- Implementar rate limiting
- Integrar o sistema de auditoria com o usuário autenticado (atualmente usa "admin" fixo)
- Considerar implementar filtros e paginação no endpoint de auditoria para grandes volumes de dados

---

## 📖 Referências

### Documentação Oficial

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/4.0.1/reference/htmlsingle/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Swagger/OpenAPI](https://swagger.io/docs/)

### Guias

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)

---

## 📄 Licença

Este projeto é um projeto de demonstração.

---

## 👤 Autor

**Vitor Freitas**

- 📧 Email: developer.vitord@gmail.com
- 💼 LinkedIn: [Vitor Dias](https://www.linkedin.com/in/vitor-dias-5450b5194/)
---
