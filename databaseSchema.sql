-- Create database
drop database if exists sistema_documento;

CREATE DATABASE sistema_documento CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sistema_documento;

-- Create tables with best practices:
-- 1. Use InnoDB engine for transaction support
-- 2. Use proper data types and sizes
-- 3. Add indexes for frequently queried columns
-- 4. Use foreign key constraints for referential integrity
-- 5. Add appropriate comments for documentation
-- Users table
DROP TABLE IF EXISTS usuarios;

CREATE TABLE
    usuarios (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        nome VARCHAR(100) NOT NULL,
        email VARCHAR(100) NOT NULL,
        senha VARCHAR(255) NOT NULL COMMENT 'Stored as bcrypt hash',
        tipo ENUM ('ALUNO', 'ADMIN') NOT NULL,
        ativo BOOLEAN DEFAULT TRUE,
        data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP,
        matricula VARCHAR(20),
        curso VARCHAR(50),
        UNIQUE INDEX idx_email (email),
        INDEX idx_tipo_ativo (tipo, ativo)
    ) ENGINE = InnoDB COMMENT 'Stores user information for students and administrators';

-- Document types table
DROP TABLE IF EXISTS documentos_tipo;

CREATE TABLE
    documentos_tipo (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        nome VARCHAR(100) NOT NULL,
        descricao TEXT,
        prazo_base INT NOT NULL COMMENT 'Base processing time in hours',
        ativo BOOLEAN DEFAULT TRUE,
        INDEX idx_ativo (ativo)
    ) ENGINE = InnoDB COMMENT 'Stores available document types and their base processing times';

-- Document requests table
DROP TABLE IF EXISTS solicitacoes;

CREATE TABLE
    solicitacoes (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        id_aluno BIGINT NOT NULL,
        id_documento_tipo BIGINT NOT NULL,
        data_solicitacao DATETIME DEFAULT CURRENT_TIMESTAMP,
        urgencia ENUM ('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
        status ENUM (
            'SOLICITADO',
            'VISUALIZADO',
            'EM_PRODUCAO',
            'CONCLUIDO'
        ) DEFAULT 'SOLICITADO',
        prioridade INT COMMENT 'Calculated priority score',
        observacoes TEXT,
        data_conclusao DATETIME,
        data_atualizacao DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (id_aluno) REFERENCES usuarios (id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (id_documento_tipo) REFERENCES documentos_tipo (id) ON DELETE RESTRICT ON UPDATE CASCADE,
        INDEX idx_status_prioridade (status, prioridade DESC),
        INDEX idx_aluno_status (id_aluno, status),
        INDEX idx_data_solicitacao (data_solicitacao)
    ) ENGINE = InnoDB COMMENT 'Stores document requests and their current status';

-- Status history table
DROP TABLE IF EXISTS status_historico;

CREATE TABLE
    status_historico (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        id_solicitacao BIGINT NOT NULL,
        status VARCHAR(50) NOT NULL,
        data_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
        responsavel VARCHAR(100),
        observacao TEXT,
        FOREIGN KEY (id_solicitacao) REFERENCES solicitacoes (id) ON DELETE CASCADE ON UPDATE CASCADE,
        INDEX idx_solicitacao_data (id_solicitacao, data_hora)
    ) ENGINE = InnoDB COMMENT 'Tracks status changes for document requests';

-- Priority configuration table
DROP TABLE IF EXISTS config_prioridades;

CREATE TABLE
    config_prioridades (
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        tipo_urgencia ENUM ('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
        peso_base INT NOT NULL COMMENT 'Base priority weight',
        multiplicador_tempo INT NOT NULL COMMENT 'Time multiplier for priority calculation',
        UNIQUE INDEX idx_tipo_urgencia (tipo_urgencia)
    ) ENGINE = InnoDB COMMENT 'Configuration for priority calculation strategy';

-- Insert initial data
-- Insert admin user
INSERT INTO
    usuarios (nome, email, senha, tipo, matricula, curso)
VALUES
    (
        'Admin Sistema',
        'admin@instituicao.edu.br',
        'admin123',
        'ADMIN',
        NULL,
        NULL
    );

-- Insert example students
INSERT INTO
    usuarios (nome, email, senha, tipo, matricula, curso)
VALUES
    (
        'João Silva',
        'joao.silva@instituicao.edu.br',
        'aluno123',
        'ALUNO',
        '2023001',
        'Engenharia de Software'
    ),
    (
        'Maria Santos',
        'maria.santos@instituicao.edu.br',
        'aluno123',
        'ALUNO',
        '2023002',
        'Ciência da Computação'
    );

-- Insert document types
INSERT INTO
    documentos_tipo (nome, descricao, prazo_base)
VALUES
    (
        'Histórico Escolar',
        'Documento com todas as disciplinas cursadas e notas',
        72
    ),
    (
        'Atestado de Matrícula',
        'Comprovação de vínculo com a instituição',
        24
    ),
    (
        'Programa de Disciplina',
        'Ementa e programa de uma disciplina específica',
        48
    );

-- Insert priority configurations
INSERT INTO
    config_prioridades (tipo_urgencia, peso_base, multiplicador_tempo)
VALUES
    ('ALTA', 100, 10),
    ('MEDIA', 50, 5),
    ('BAIXA', 25, 2);

-- Insert more students
INSERT INTO
    usuarios (nome, email, senha, tipo, matricula, curso)
VALUES
    (
        'Ana Costa',
        'ana.costa@instituicao.edu.br',
        'aluno123',
        'ALUNO',
        '2023003',
        'Engenharia Civil'
    ),
    (
        'Pedro Oliveira',
        'pedro.oliveira@instituicao.edu.br',
        'aluno123',
        'ALUNO',
        '2023004',
        'Administração'
    ),
    (
        'Carla Rodrigues',
        'carla.rodrigues@instituicao.edu.br',
        'aluno123',
        'ALUNO',
        '2023005',
        'Psicologia'
    ),
    (
        'Rafael Souza',
        'rafael.souza@instituicao.edu.br',
        'aluno123',
        'ALUNO',
        '2023006',
        'Medicina'
    ),
    (
        'Juliana Lima',
        'juliana.lima@instituicao.edu.br',
        'aluno123',
        'ALUNO',
        '2023007',
        'Direito'
    );

-- Insert another admin
INSERT INTO
    usuarios (nome, email, senha, tipo, matricula, curso)
VALUES
    (
        'Coordenação Geral',
        'coordenacao@instituicao.edu.br',
        'admin123',
        'ADMIN',
        NULL,
        NULL
    );

-- Insert more document types
INSERT INTO
    documentos_tipo (nome, descricao, prazo_base)
VALUES
    (
        'Declaração de Conclusão',
        'Declaração de conclusão de curso',
        120
    ),
    ('Diploma', 'Diploma de graduação', 360),
    ('Ementa do Curso', 'Ementa completa do curso', 72),
    (
        'Comprovante de Colação',
        'Comprovante de colação de grau',
        168
    ),
    (
        'Transferência',
        'Documentação para transferência',
        96
    );

-- Insert document requests with calculated priorities
INSERT INTO
    solicitacoes (
        id_aluno,
        id_documento_tipo,
        data_solicitacao,
        urgencia,
        status,
        prioridade,
        observacoes
    )
VALUES
    (
        2,
        1,
        '2024-01-15 09:30:00',
        'ALTA',
        'CONCLUIDO',
        100,
        'Necessário para processo seletivo de estágio'
    ),
    (
        2,
        2,
        '2024-01-20 14:15:00',
        'MEDIA',
        'EM_PRODUCAO',
        50,
        'Comprovante para banco'
    ),
    (
        3,
        1,
        '2024-01-18 11:00:00',
        'BAIXA',
        'VISUALIZADO',
        25,
        'Para arquivo pessoal'
    ),
    (
        3,
        3,
        '2024-01-22 16:45:00',
        'ALTA',
        'SOLICITADO',
        100,
        'Urgente para intercâmbio'
    ),
    (
        4,
        2,
        '2024-01-19 10:20:00',
        'MEDIA',
        'CONCLUIDO',
        50,
        'Apresentar na empresa'
    ),
    (
        5,
        1,
        '2024-01-21 13:30:00',
        'ALTA',
        'EM_PRODUCAO',
        100,
        'Prazo para bolsa de estudos'
    ),
    (
        6,
        4,
        '2024-01-17 15:00:00',
        'BAIXA',
        'SOLICITADO',
        25,
        'Documentação pessoal'
    ),
    (
        7,
        3,
        '2024-01-23 08:45:00',
        'MEDIA',
        'VISUALIZADO',
        50,
        'Necessário para pós-graduação'
    );

-- Insert status history for the requests
INSERT INTO
    status_historico (
        id_solicitacao,
        status,
        data_hora,
        responsavel,
        observacao
    )
VALUES
    (
        1,
        'SOLICITADO',
        '2024-01-15 09:30:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        1,
        'VISUALIZADO',
        '2024-01-15 10:15:00',
        'Admin Sistema',
        'Solicitação em análise'
    ),
    (
        1,
        'EM_PRODUCAO',
        '2024-01-15 14:30:00',
        'Admin Sistema',
        'Documento em produção'
    ),
    (
        1,
        'CONCLUIDO',
        '2024-01-16 11:20:00',
        'Coordenação Geral',
        'Documento finalizado e disponível'
    ),
    (
        2,
        'SOLICITADO',
        '2024-01-20 14:15:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        2,
        'VISUALIZADO',
        '2024-01-20 16:00:00',
        'Coordenação Geral',
        'Solicitação validada'
    ),
    (
        2,
        'EM_PRODUCAO',
        '2024-01-22 09:15:00',
        'Coordenação Geral',
        'Iniciada produção do documento'
    ),
    (
        3,
        'SOLICITADO',
        '2024-01-18 11:00:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        3,
        'VISUALIZADO',
        '2024-01-19 08:30:00',
        'Admin Sistema',
        'Aguardando disponibilidade'
    ),
    (
        4,
        'SOLICITADO',
        '2024-01-22 16:45:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        5,
        'SOLICITADO',
        '2024-01-19 10:20:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        5,
        'VISUALIZADO',
        '2024-01-19 11:10:00',
        'Admin Sistema',
        'Processamento rápido solicitado'
    ),
    (
        5,
        'EM_PRODUCAO',
        '2024-01-19 14:00:00',
        'Admin Sistema',
        'Documento em elaboração'
    ),
    (
        5,
        'CONCLUIDO',
        '2024-01-20 09:45:00',
        'Coordenação Geral',
        'Documento concluído'
    ),
    (
        6,
        'SOLICITADO',
        '2024-01-21 13:30:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        6,
        'VISUALIZADO',
        '2024-01-21 14:20:00',
        'Coordenação Geral',
        'Urgência confirmada'
    ),
    (
        6,
        'EM_PRODUCAO',
        '2024-01-22 08:15:00',
        'Coordenação Geral',
        'Produção iniciada'
    ),
    (
        7,
        'SOLICITADO',
        '2024-01-17 15:00:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        8,
        'SOLICITADO',
        '2024-01-23 08:45:00',
        'Sistema',
        'Solicitação registrada automaticamente'
    ),
    (
        8,
        'VISUALIZADO',
        '2024-01-23 10:30:00',
        'Admin Sistema',
        'Em processo de validação'
    );