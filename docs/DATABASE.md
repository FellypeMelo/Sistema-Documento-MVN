# Database Schema Documentation

## Overview
This document describes the database schema for the Document Request Management System. The system uses MySQL 8.0 and consists of 5 main tables that handle users, document types, requests, status history, and priority configurations.

## Tables

### usuarios
Stores information about system users (students and administrators).

```sql
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('ALUNO', 'ADMINISTRADOR') NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acesso TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE
);
```

**Columns:**
- `id`: Unique identifier for each user
- `nome`: User's full name
- `email`: User's email address (used for login)
- `senha`: Hashed password
- `tipo_usuario`: User type (ALUNO or ADMINISTRADOR)
- `data_criacao`: Account creation timestamp
- `ultimo_acesso`: Last login timestamp
- `ativo`: Account status flag

### documentos_tipo
Defines the types of documents that can be requested.

```sql
CREATE TABLE documentos_tipo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    prazo_medio INT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);
```

**Columns:**
- `id`: Unique identifier for document type
- `nome`: Name of the document type
- `descricao`: Detailed description
- `prazo_medio`: Average processing time (in days)
- `ativo`: Status flag

### solicitacoes
Main table for document requests.

```sql
CREATE TABLE solicitacoes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    id_documento_tipo BIGINT NOT NULL,
    data_solicitacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO') DEFAULT 'PENDENTE',
    prioridade ENUM('ALTA', 'MEDIA', 'BAIXA'),
    observacoes TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_documento_tipo) REFERENCES documentos_tipo(id)
);
```

**Columns:**
- `id`: Unique identifier for request
- `id_usuario`: Reference to requesting user
- `id_documento_tipo`: Reference to document type
- `data_solicitacao`: Request creation timestamp
- `status`: Current request status
- `prioridade`: Calculated priority level
- `observacoes`: Additional notes

### status_historico
Tracks status changes for requests.

```sql
CREATE TABLE status_historico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_solicitacao BIGINT NOT NULL,
    status ENUM('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO') NOT NULL,
    data_mudanca TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_admin BIGINT,
    observacao TEXT,
    FOREIGN KEY (id_solicitacao) REFERENCES solicitacoes(id),
    FOREIGN KEY (id_admin) REFERENCES usuarios(id)
);
```

**Columns:**
- `id`: Unique identifier for status change
- `id_solicitacao`: Reference to request
- `status`: New status value
- `data_mudanca`: Change timestamp
- `id_admin`: Administrator who made the change
- `observacao`: Notes about the status change

### config_prioridades
Configures priority calculation rules.

```sql
CREATE TABLE config_prioridades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo_documento_id BIGINT NOT NULL,
    regra VARCHAR(50) NOT NULL,
    peso INT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (tipo_documento_id) REFERENCES documentos_tipo(id)
);
```

**Columns:**
- `id`: Unique identifier for configuration
- `tipo_documento_id`: Reference to document type
- `regra`: Priority rule identifier
- `peso`: Weight for priority calculation
- `ativo`: Configuration status

## Relationships

1. **usuarios → solicitacoes**
   - One-to-Many: One user can have multiple requests
   - Foreign Key: `solicitacoes.id_usuario` → `usuarios.id`

2. **documentos_tipo → solicitacoes**
   - One-to-Many: One document type can have multiple requests
   - Foreign Key: `solicitacoes.id_documento_tipo` → `documentos_tipo.id`

3. **solicitacoes → status_historico**
   - One-to-Many: One request can have multiple status changes
   - Foreign Key: `status_historico.id_solicitacao` → `solicitacoes.id`

4. **usuarios → status_historico**
   - One-to-Many: One admin can make multiple status changes
   - Foreign Key: `status_historico.id_admin` → `usuarios.id`

5. **documentos_tipo → config_prioridades**
   - One-to-Many: One document type can have multiple priority rules
   - Foreign Key: `config_prioridades.tipo_documento_id` → `documentos_tipo.id`

## Indexes

### usuarios
- PRIMARY KEY (`id`)
- UNIQUE KEY `uk_email` (`email`)

### documentos_tipo
- PRIMARY KEY (`id`)

### solicitacoes
- PRIMARY KEY (`id`)
- INDEX `idx_usuario` (`id_usuario`)
- INDEX `idx_documento_tipo` (`id_documento_tipo`)
- INDEX `idx_status` (`status`)

### status_historico
- PRIMARY KEY (`id`)
- INDEX `idx_solicitacao` (`id_solicitacao`)
- INDEX `idx_admin` (`id_admin`)

### config_prioridades
- PRIMARY KEY (`id`)
- INDEX `idx_tipo_documento` (`tipo_documento_id`)

## Constraints

1. **Delete Rules:**
   - All foreign keys use `RESTRICT` for `ON DELETE`
   - Prevents orphaned records

2. **Data Integrity:**
   - Non-null constraints on critical fields
   - Enum constraints for status and priorities
   - Unique constraint on user email

3. **Status Flow:**
   - Status changes must follow valid transitions
   - Enforced by application logic

## Example Queries

1. Get all pending requests for a user:
```sql
SELECT s.*, dt.nome as tipo_documento
FROM solicitacoes s
JOIN documentos_tipo dt ON s.id_documento_tipo = dt.id
WHERE s.id_usuario = ? AND s.status = 'PENDENTE';
```

2. Get request history with admin details:
```sql
SELECT sh.*, u.nome as admin_nome
FROM status_historico sh
LEFT JOIN usuarios u ON sh.id_admin = u.id
WHERE sh.id_solicitacao = ?
ORDER BY sh.data_mudanca DESC;
```

3. Calculate request priority:
```sql
SELECT s.id, s.data_solicitacao, 
       SUM(cp.peso) as prioridade_total
FROM solicitacoes s
JOIN config_prioridades cp ON s.id_documento_tipo = cp.tipo_documento_id
WHERE s.id = ? AND cp.ativo = TRUE
GROUP BY s.id;
```