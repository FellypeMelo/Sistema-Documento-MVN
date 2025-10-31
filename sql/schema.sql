-- Create database
CREATE DATABASE IF NOT EXISTS sistema_documento
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE sistema_documento;

-- Create tables with best practices:
-- 1. Use InnoDB engine for transaction support
-- 2. Use proper data types and sizes
-- 3. Add indexes for frequently queried columns
-- 4. Use foreign key constraints for referential integrity
-- 5. Add appropriate comments for documentation

-- Users table
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL COMMENT 'Stored as bcrypt hash',
    tipo ENUM('ALUNO', 'ADMIN') NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
    matricula VARCHAR(20),
    curso VARCHAR(50),
    UNIQUE INDEX idx_email (email),
    INDEX idx_tipo_ativo (tipo, ativo)
) ENGINE=InnoDB COMMENT 'Stores user information for students and administrators';

-- Document types table
CREATE TABLE documentos_tipo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    prazo_base INT NOT NULL COMMENT 'Base processing time in hours',
    ativo BOOLEAN DEFAULT TRUE,
    INDEX idx_ativo (ativo)
) ENGINE=InnoDB COMMENT 'Stores available document types and their base processing times';

-- Document requests table
CREATE TABLE solicitacoes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_aluno BIGINT NOT NULL,
    id_documento_tipo BIGINT NOT NULL,
    data_solicitacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    urgencia ENUM('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
    status ENUM('SOLICITADO', 'VISUALIZADO', 'EM_PRODUCAO', 'CONCLUIDO') 
        DEFAULT 'SOLICITADO',
    prioridade INT COMMENT 'Calculated priority score',
    observacoes TEXT,
    data_conclusao DATETIME,
    data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_aluno) 
        REFERENCES usuarios(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (id_documento_tipo) 
        REFERENCES documentos_tipo(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    INDEX idx_status_prioridade (status, prioridade DESC),
    INDEX idx_aluno_status (id_aluno, status),
    INDEX idx_data_solicitacao (data_solicitacao)
) ENGINE=InnoDB COMMENT 'Stores document requests and their current status';

-- Status history table
CREATE TABLE status_historico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_solicitacao BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    responsavel VARCHAR(100),
    observacao TEXT,
    FOREIGN KEY (id_solicitacao) 
        REFERENCES solicitacoes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_solicitacao_data (id_solicitacao, data_hora)
) ENGINE=InnoDB COMMENT 'Tracks status changes for document requests';

-- Priority configuration table
CREATE TABLE config_prioridades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo_urgencia ENUM('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
    peso_base INT NOT NULL COMMENT 'Base priority weight',
    multiplicador_tempo INT NOT NULL COMMENT 'Time multiplier for priority calculation',
    UNIQUE INDEX idx_tipo_urgencia (tipo_urgencia)
) ENGINE=InnoDB COMMENT 'Configuration for priority calculation strategy';

-- Insert initial data

-- Insert admin user
INSERT INTO usuarios (nome, email, senha, tipo, matricula, curso) VALUES
('Admin Sistema', 'admin@instituicao.edu.br', 
 '$2a$12$1234567890123456789012uQcMGUmqYVJvxe4REqYzI5GxrR3K1yS', -- senha: admin123
 'ADMIN', NULL, NULL);

-- Insert example students
INSERT INTO usuarios (nome, email, senha, tipo, matricula, curso) VALUES
('João Silva', 'joao.silva@instituicao.edu.br',
 '$2a$12$1234567890123456789012uA8wz6RyR1qK9KzK1K4J5K9K4J5K9K4', -- senha: aluno123
 'ALUNO', '2023001', 'Engenharia de Software'),
('Maria Santos', 'maria.santos@instituicao.edu.br',
 '$2a$12$1234567890123456789012uB9x8z7SyS2rL1L5K6L5K6L5K6L5K6L5', -- senha: aluno123
 'ALUNO', '2023002', 'Ciência da Computação');

-- Insert document types
INSERT INTO documentos_tipo (nome, descricao, prazo_base) VALUES
('Histórico Escolar', 'Documento com todas as disciplinas cursadas e notas', 72),
('Atestado de Matrícula', 'Comprovação de vínculo com a instituição', 24),
('Programa de Disciplina', 'Ementa e programa de uma disciplina específica', 48);

-- Insert priority configurations
INSERT INTO config_prioridades (tipo_urgencia, peso_base, multiplicador_tempo) VALUES
('ALTA', 100, 10),
('MEDIA', 50, 5),
('BAIXA', 25, 2);