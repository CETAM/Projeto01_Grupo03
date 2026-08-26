-- BANCO DE DADOS BIBLIOTECA_DB
-- Projeto01_Grupo03 - CETAM

CREATE DATABASE IF NOT EXISTS grupo03_db;
USE grupo03_db;

-- 1. Tabela: autores
CREATE TABLE IF NOT EXISTS autores (
    id_autor BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL
);

-- 2. Tabela: categorias
CREATE TABLE IF NOT EXISTS categorias (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

-- 3. Tabela: editoras
CREATE TABLE IF NOT EXISTS editoras (
    id_editora BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL
);

-- 4. Tabela: alunos
CREATE TABLE IF NOT EXISTS alunos (
    id_aluno BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(150),
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- 5. Tabela: livros
CREATE TABLE IF NOT EXISTS livros (
    id_livro BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    ano INT NOT NULL,
    quantidade_exemplares INT NOT NULL DEFAULT 1,
    disponivel BOOLEAN NOT NULL DEFAULT TRUE,
    id_autor BIGINT NOT NULL,
    id_editora BIGINT NOT NULL,
    id_categoria BIGINT,
    CONSTRAINT fk_livro_autor FOREIGN KEY (id_autor) REFERENCES autores(id_autor),
    CONSTRAINT fk_livro_editora FOREIGN KEY (id_editora) REFERENCES editoras(id_editora),
    CONSTRAINT fk_livros_categorias FOREIGN KEY (id_categoria) REFERENCES categorias (id_categoria) ON DELETE SET NULL ON UPDATE CASCADE
);

-- 6. Tabela: emprestimos
CREATE TABLE IF NOT EXISTS emprestimos (
    id_emprestimo BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_aluno BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_previsao_devolucao DATE,
    data_devolucao DATE,
    status VARCHAR(20) NOT NULL,
    quantidade_renovacoes INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_emprestimo_aluno FOREIGN KEY (id_aluno) REFERENCES alunos(id_aluno),
    CONSTRAINT fk_emprestimo_livro FOREIGN KEY (id_livro) REFERENCES livros(id_livro),
    CONSTRAINT chk_status_emprestimo CHECK (status IN ('ATIVO', 'DEVOLVIDO', 'ATRASADO'))
);

-- 7. Tabela: multas
CREATE TABLE IF NOT EXISTS multas (
    id_multa BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(10, 2) NOT NULL,
    data_geracao DATE,
    status VARCHAR(20) NOT NULL,
    id_emprestimo BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_multa_emprestimo FOREIGN KEY (id_emprestimo) REFERENCES emprestimos(id_emprestimo),
    CONSTRAINT chk_status_multa CHECK (status IN ('PENDENTE', 'PAGO'))
);

-- 8. Tabela: configuracoes_sistema
CREATE TABLE IF NOT EXISTS configuracoes_sistema (
    id_config BIGINT PRIMARY KEY,
    dias_prazo_emprestimo INT NOT NULL DEFAULT 14,
    dias_prazo_renovacao INT NOT NULL DEFAULT 14,
    limite_livros_simultaneos INT NOT NULL DEFAULT 3,
    maximo_renovacoes_permitidas INT NOT NULL DEFAULT 2,
    valor_multa_por_dia DECIMAL(10, 2) NOT NULL DEFAULT 2.00,
    dias_tolerancia_atraso INT NOT NULL DEFAULT 0,
    bloquear_emprestimo_com_multa_pendente BOOLEAN NOT NULL DEFAULT FALSE,
    nome_instituicao VARCHAR(150) NOT NULL DEFAULT 'Sistema de Biblioteca',
    texto_rodape_relatorio VARCHAR(255) NOT NULL DEFAULT 'Sistema de Controle de Biblioteca - Relatório Oficial'
);

-- Carga inicial de configurações padrão (se não existir)
INSERT INTO configuracoes_sistema (
    id_config,
    dias_prazo_emprestimo,
    dias_prazo_renovacao,
    limite_livros_simultaneos,
    maximo_renovacoes_permitidas,
    valor_multa_por_dia,
    dias_tolerancia_atraso,
    bloquear_emprestimo_com_multa_pendente,
    nome_instituicao,
    texto_rodape_relatorio
) VALUES (
    1,
    14,
    14,
    3,
    2,
    2.00,
    0,
    FALSE,
    'Sistema de Biblioteca',
    'Sistema de Controle de Biblioteca - Relatório Oficial'
) ON DUPLICATE KEY UPDATE id_config = id_config;
