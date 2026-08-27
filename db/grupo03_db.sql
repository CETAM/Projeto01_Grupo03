-- BANCO DE DADOS BIBLIOTECA_DB
-- Projeto01_Grupo03 - CETAM

DROP DATABASE IF EXISTS grupo03_db;
CREATE DATABASE grupo03_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE grupo03_db;

-- 1. Tabela: autores
CREATE TABLE IF NOT EXISTS autores (
    id_autor BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL UNIQUE
);

-- 2. Tabela: categorias
CREATE TABLE IF NOT EXISTS categorias (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(255)
);

-- 3. Tabela: editoras
CREATE TABLE IF NOT EXISTS editoras (
    id_editora BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL UNIQUE
);

-- 4. Tabela: alunos
CREATE TABLE IF NOT EXISTS alunos (
    id_aluno BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(150) UNIQUE,
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
    data_pagamento DATE,
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


-- Povoamento

USE grupo03_db;

-- Autores
INSERT IGNORE INTO autores (nome) VALUES
('Machado de Assis'),
('Clarice Lispector'),
('Jorge Amado'),
('Graciliano Ramos'),
('José de Alencar'),
('Carlos Drummond de Andrade'),
('Cecília Meireles'),
('Monteiro Lobato'),
('Lima Barreto'),
('Érico Veríssimo'),
('George Orwell'),
('Jane Austen'),
('J. R. R. Tolkien'),
('Agatha Christie'),
('Arthur Conan Doyle'),
('Franz Kafka'),
('Victor Hugo'),
('Fiódor Dostoiévski'),
('Gabriel García Márquez'),
('Antoine de Saint-Exupéry'),
('William Shakespeare'),
('Miguel de Cervantes'),
('Jules Verne'),
('Mary Shelley'),
('Edgar Allan Poe');


-- Categorias
INSERT IGNORE INTO categorias (nome, descricao) VALUES
('Romance', 'Obras literárias de caráter romântico e ficcional.'),
('Ficção Científica', 'Obras envolvendo ciência, tecnologia e futuros possíveis.'),
('Fantasia', 'Histórias com elementos fantásticos e mundos imaginários.'),
('Mistério', 'Narrativas envolvendo investigações e acontecimentos misteriosos.'),
('Suspense', 'Obras caracterizadas por tensão e expectativa.'),
('Literatura Brasileira', 'Obras pertencentes à literatura brasileira.'),
('Clássicos', 'Obras consagradas da literatura nacional e internacional.'),
('Poesia', 'Obras compostas predominantemente por poemas.'),
('Infantojuvenil', 'Literatura destinada ao público infantil e juvenil.'),
('Terror', 'Obras envolvendo medo, horror e elementos sobrenaturais.'),
('Aventura', 'Narrativas envolvendo viagens, explorações e desafios.'),
('Drama', 'Obras relacionadas a conflitos humanos e emocionais.');


-- Editoras
INSERT IGNORE INTO editoras (nome) VALUES
('Companhia das Letras'),
('Editora Record'),
('Rocco'),
('Intrínseca'),
('Sextante'),
('Martin Claret'),
('Principis'),
('Penguin Companhia'),
('L&PM'),
('Globo Livros'),
('DarkSide Books'),
('HarperCollins Brasil'),
('Editora 34'),
('Ática'),
('Moderna');


-- Alunos
INSERT IGNORE INTO alunos (nome, matricula, email, ativo) VALUES
('Ana Beatriz Oliveira', '20260001', 'ana.oliveira@aluno.cetam.edu.br', TRUE),
('Bruno Henrique Souza', '20260002', 'bruno.souza@aluno.cetam.edu.br', TRUE),
('Carla Mendes Silva', '20260003', 'carla.silva@aluno.cetam.edu.br', TRUE),
('Daniel Santos Lima', '20260004', 'daniel.lima@aluno.cetam.edu.br', TRUE),
('Eduarda Costa Pereira', '20260005', 'eduarda.pereira@aluno.cetam.edu.br', TRUE),
('Felipe Rocha Alves', '20260006', 'felipe.alves@aluno.cetam.edu.br', TRUE),
('Gabriela Martins Gomes', '20260007', 'gabriela.gomes@aluno.cetam.edu.br', TRUE),
('Henrique Barbosa Melo', '20260008', 'henrique.melo@aluno.cetam.edu.br', TRUE),
('Isabela Ferreira Reis', '20260009', 'isabela.reis@aluno.cetam.edu.br', TRUE),
('João Pedro Ribeiro', '20260010', 'joao.ribeiro@aluno.cetam.edu.br', TRUE),

('Karen Cristina Lopes', '20260011', 'karen.lopes@aluno.cetam.edu.br', TRUE),
('Lucas Gabriel Castro', '20260012', 'lucas.castro@aluno.cetam.edu.br', TRUE),
('Mariana Nunes Araújo', '20260013', 'mariana.araujo@aluno.cetam.edu.br', TRUE),
('Nathan Vieira Santos', '20260014', 'nathan.santos@aluno.cetam.edu.br', TRUE),
('Olívia Fernandes Lima', '20260015', 'olivia.lima@aluno.cetam.edu.br', TRUE),
('Paulo Henrique Silva', '20260016', 'paulo.silva@aluno.cetam.edu.br', TRUE),
('Queila Rodrigues Costa', '20260017', 'queila.costa@aluno.cetam.edu.br', TRUE),
('Rafael Almeida Sousa', '20260018', 'rafael.sousa@aluno.cetam.edu.br', TRUE),
('Sabrina Martins Rocha', '20260019', 'sabrina.rocha@aluno.cetam.edu.br', TRUE),
('Thiago Alves Ribeiro', '20260020', 'thiago.ribeiro@aluno.cetam.edu.br', TRUE),

('Vitória Carvalho Melo', '20260021', 'vitoria.melo@aluno.cetam.edu.br', TRUE),
('Wesley Gomes Silva', '20260022', 'wesley.silva@aluno.cetam.edu.br', TRUE),
('Yasmin Pereira Costa', '20260023', 'yasmin.costa@aluno.cetam.edu.br', TRUE),
('Arthur Fernandes Lima', '20260024', 'arthur.lima@aluno.cetam.edu.br', TRUE),
('Bianca Rodrigues Souza', '20260025', 'bianca.souza@aluno.cetam.edu.br', TRUE),
('Caio Henrique Martins', '20260026', 'caio.martins@aluno.cetam.edu.br', TRUE),
('Débora Almeida Rocha', '20260027', 'debora.rocha@aluno.cetam.edu.br', TRUE),
('Enzo Gabriel Nunes', '20260028', 'enzo.nunes@aluno.cetam.edu.br', TRUE),
('Fernanda Ribeiro Alves', '20260029', 'fernanda.alves@aluno.cetam.edu.br', TRUE),
('Gustavo Carvalho Reis', '20260030', 'gustavo.reis@aluno.cetam.edu.br', TRUE),

('Heloísa Mendes Castro', '20260031', 'heloisa.castro@aluno.cetam.edu.br', TRUE),
('Igor Santos Pereira', '20260032', 'igor.pereira@aluno.cetam.edu.br', TRUE),
('Juliana Rocha Fernandes', '20260033', 'juliana.fernandes@aluno.cetam.edu.br', TRUE),
('Kauã Henrique Lima', '20260034', 'kaua.lima@aluno.cetam.edu.br', TRUE),
('Larissa Barbosa Souza', '20260035', 'larissa.souza@aluno.cetam.edu.br', TRUE),
('Matheus Almeida Silva', '20260036', 'matheus.silva@aluno.cetam.edu.br', TRUE),
('Natália Gomes Costa', '20260037', 'natalia.costa@aluno.cetam.edu.br', TRUE),
('Otávio Ferreira Rocha', '20260038', 'otavio.rocha@aluno.cetam.edu.br', TRUE),
('Patrícia Martins Lima', '20260039', 'patricia.lima@aluno.cetam.edu.br', TRUE),
('Renan Castro Mendes', '20260040', 'renan.mendes@aluno.cetam.edu.br', TRUE),

('Sophia Rodrigues Reis', '20260041', 'sophia.reis@aluno.cetam.edu.br', TRUE),
('Talita Alves Pereira', '20260042', 'talita.pereira@aluno.cetam.edu.br', TRUE),
('Vinícius Rocha Gomes', '20260043', 'vinicius.gomes@aluno.cetam.edu.br', TRUE),
('William Henrique Costa', '20260044', 'william.costa@aluno.cetam.edu.br', TRUE),
('Alice Fernandes Sousa', '20260045', 'alice.sousa@aluno.cetam.edu.br', TRUE),
('Bernardo Carvalho Lima', '20260046', 'bernardo.lima@aluno.cetam.edu.br', TRUE),
('Camila Santos Rocha', '20260047', 'camila.rocha@aluno.cetam.edu.br', TRUE),
('Diego Ribeiro Silva', '20260048', 'diego.silva@aluno.cetam.edu.br', FALSE),
('Evelyn Martins Costa', '20260049', 'evelyn.costa@aluno.cetam.edu.br', TRUE),
('Fernando Almeida Souza', '20260050', 'fernando.souza@aluno.cetam.edu.br', FALSE);


-- Livros
INSERT IGNORE INTO livros (
    titulo,
    isbn,
    ano,
    quantidade_exemplares,
    disponivel,
    id_autor,
    id_editora,
    id_categoria
)
SELECT
    'Dom Casmurro',
    '9780000000001',
    1899,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Companhia das Letras'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Machado de Assis';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Memórias Póstumas de Brás Cubas',
    '9780000000002',
    1881,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Martin Claret'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Machado de Assis';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Quincas Borba',
    '9780000000003',
    1891,
    3,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Penguin Companhia'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'Machado de Assis';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Helena',
    '9780000000004',
    1876,
    2,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Romance'
WHERE a.nome = 'Machado de Assis';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'A Hora da Estrela',
    '9780000000005',
    1977,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Companhia das Letras'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Clarice Lispector';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Perto do Coração Selvagem',
    '9780000000006',
    1943,
    3,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora Record'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Clarice Lispector';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Capitães da Areia',
    '9780000000007',
    1937,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Companhia das Letras'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Jorge Amado';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Gabriela Cravo e Canela',
    '9780000000008',
    1958,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora Record'
         JOIN categorias c ON c.nome = 'Romance'
WHERE a.nome = 'Jorge Amado';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Vidas Secas',
    '9780000000009',
    1938,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Ática'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Graciliano Ramos';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'São Bernardo',
    '9780000000010',
    1934,
    3,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora Record'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Graciliano Ramos';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Iracema',
    '9780000000011',
    1865,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Martin Claret'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'José de Alencar';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Guarani',
    '9780000000012',
    1857,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Aventura'
WHERE a.nome = 'José de Alencar';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Alguma Poesia',
    '9780000000013',
    1930,
    3,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Companhia das Letras'
         JOIN categorias c ON c.nome = 'Poesia'
WHERE a.nome = 'Carlos Drummond de Andrade';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Romanceiro da Inconfidência',
    '9780000000014',
    1953,
    3,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Globo Livros'
         JOIN categorias c ON c.nome = 'Poesia'
WHERE a.nome = 'Cecília Meireles';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Ou Isto ou Aquilo',
    '9780000000015',
    1964,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Ática'
         JOIN categorias c ON c.nome = 'Infantojuvenil'
WHERE a.nome = 'Cecília Meireles';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Reinações de Narizinho',
    '9780000000016',
    1931,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Globo Livros'
         JOIN categorias c ON c.nome = 'Infantojuvenil'
WHERE a.nome = 'Monteiro Lobato';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Triste Fim de Policarpo Quaresma',
    '9780000000017',
    1915,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Lima Barreto';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Tempo e o Vento',
    '9780000000018',
    1949,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Companhia das Letras'
         JOIN categorias c ON c.nome = 'Literatura Brasileira'
WHERE a.nome = 'Érico Veríssimo';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    '1984',
    '9780000000019',
    1949,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Companhia das Letras'
         JOIN categorias c ON c.nome = 'Ficção Científica'
WHERE a.nome = 'George Orwell';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'A Revolução dos Bichos',
    '9780000000020',
    1945,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Penguin Companhia'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'George Orwell';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Orgulho e Preconceito',
    '9780000000021',
    1813,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Penguin Companhia'
         JOIN categorias c ON c.nome = 'Romance'
WHERE a.nome = 'Jane Austen';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Razão e Sensibilidade',
    '9780000000022',
    1811,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Romance'
WHERE a.nome = 'Jane Austen';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Hobbit',
    '9780000000023',
    1937,
    7,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'HarperCollins Brasil'
         JOIN categorias c ON c.nome = 'Fantasia'
WHERE a.nome = 'J. R. R. Tolkien';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Senhor dos Anéis: A Sociedade do Anel',
    '9780000000024',
    1954,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'HarperCollins Brasil'
         JOIN categorias c ON c.nome = 'Fantasia'
WHERE a.nome = 'J. R. R. Tolkien';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Senhor dos Anéis: As Duas Torres',
    '9780000000025',
    1954,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'HarperCollins Brasil'
         JOIN categorias c ON c.nome = 'Fantasia'
WHERE a.nome = 'J. R. R. Tolkien';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Senhor dos Anéis: O Retorno do Rei',
    '9780000000026',
    1955,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'HarperCollins Brasil'
         JOIN categorias c ON c.nome = 'Fantasia'
WHERE a.nome = 'J. R. R. Tolkien';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Assassinato no Expresso do Oriente',
    '9780000000027',
    1934,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'HarperCollins Brasil'
         JOIN categorias c ON c.nome = 'Mistério'
WHERE a.nome = 'Agatha Christie';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Morte no Nilo',
    '9780000000028',
    1937,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'HarperCollins Brasil'
         JOIN categorias c ON c.nome = 'Mistério'
WHERE a.nome = 'Agatha Christie';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'E Não Sobrou Nenhum',
    '9780000000029',
    1939,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'HarperCollins Brasil'
         JOIN categorias c ON c.nome = 'Mistério'
WHERE a.nome = 'Agatha Christie';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Um Estudo em Vermelho',
    '9780000000030',
    1887,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Mistério'
WHERE a.nome = 'Arthur Conan Doyle';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Cão dos Baskervilles',
    '9780000000031',
    1902,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Mistério'
WHERE a.nome = 'Arthur Conan Doyle';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'A Metamorfose',
    '9780000000032',
    1915,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Companhia das Letras'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'Franz Kafka';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Processo',
    '9780000000033',
    1925,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora 34'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'Franz Kafka';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Os Miseráveis',
    '9780000000034',
    1862,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Martin Claret'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'Victor Hugo';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Crime e Castigo',
    '9780000000035',
    1866,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora 34'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'Fiódor Dostoiévski';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Os Irmãos Karamázov',
    '9780000000036',
    1880,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora 34'
         JOIN categorias c ON c.nome = 'Clássicos'
WHERE a.nome = 'Fiódor Dostoiévski';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Cem Anos de Solidão',
    '9780000000037',
    1967,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora Record'
         JOIN categorias c ON c.nome = 'Romance'
WHERE a.nome = 'Gabriel García Márquez';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Amor nos Tempos do Cólera',
    '9780000000038',
    1985,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Editora Record'
         JOIN categorias c ON c.nome = 'Romance'
WHERE a.nome = 'Gabriel García Márquez';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Pequeno Príncipe',
    '9780000000039',
    1943,
    8,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Rocco'
         JOIN categorias c ON c.nome = 'Infantojuvenil'
WHERE a.nome = 'Antoine de Saint-Exupéry';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Romeu e Julieta',
    '9780000000040',
    1597,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Martin Claret'
         JOIN categorias c ON c.nome = 'Drama'
WHERE a.nome = 'William Shakespeare';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Hamlet',
    '9780000000041',
    1603,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Martin Claret'
         JOIN categorias c ON c.nome = 'Drama'
WHERE a.nome = 'William Shakespeare';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Macbeth',
    '9780000000042',
    1606,
    4,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Martin Claret'
         JOIN categorias c ON c.nome = 'Drama'
WHERE a.nome = 'William Shakespeare';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Dom Quixote',
    '9780000000043',
    1605,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Martin Claret'
         JOIN categorias c ON c.nome = 'Aventura'
WHERE a.nome = 'Miguel de Cervantes';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Vinte Mil Léguas Submarinas',
    '9780000000044',
    1870,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Aventura'
WHERE a.nome = 'Jules Verne';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Viagem ao Centro da Terra',
    '9780000000045',
    1864,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Aventura'
WHERE a.nome = 'Jules Verne';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'A Volta ao Mundo em 80 Dias',
    '9780000000046',
    1872,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Aventura'
WHERE a.nome = 'Jules Verne';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Da Terra à Lua',
    '9780000000047',
    1865,
    3,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'Principis'
         JOIN categorias c ON c.nome = 'Ficção Científica'
WHERE a.nome = 'Jules Verne';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'Frankenstein',
    '9780000000048',
    1818,
    6,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'DarkSide Books'
         JOIN categorias c ON c.nome = 'Terror'
WHERE a.nome = 'Mary Shelley';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Último Homem',
    '9780000000049',
    1826,
    3,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'DarkSide Books'
         JOIN categorias c ON c.nome = 'Ficção Científica'
WHERE a.nome = 'Mary Shelley';

INSERT IGNORE INTO livros (
    titulo, isbn, ano, quantidade_exemplares, disponivel,
    id_autor, id_editora, id_categoria
)
SELECT
    'O Corvo',
    '9780000000050',
    1845,
    5,
    TRUE,
    a.id_autor,
    e.id_editora,
    c.id_categoria
FROM autores a
         JOIN editoras e ON e.nome = 'DarkSide Books'
         JOIN categorias c ON c.nome = 'Terror'
WHERE a.nome = 'Edgar Allan Poe';


-- Empréstimos devolvidos
INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-06-01',
    '2026-06-15',
    '2026-06-13',
    'DEVOLVIDO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000001'
WHERE a.matricula = '20260001';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-06-03',
    '2026-06-17',
    '2026-06-17',
    'DEVOLVIDO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000005'
WHERE a.matricula = '20260002';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-06-05',
    '2026-06-19',
    '2026-06-18',
    'DEVOLVIDO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000007'
WHERE a.matricula = '20260003';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-07-01',
    '2026-07-15',
    '2026-07-14',
    'DEVOLVIDO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000019'
WHERE a.matricula = '20260004';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-07-05',
    '2026-07-19',
    '2026-07-18',
    'DEVOLVIDO',
    1
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000023'
WHERE a.matricula = '20260005';


-- Empréstimos atrasados
INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-07-20',
    '2026-08-03',
    NULL,
    'ATRASADO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000040'
WHERE a.matricula = '20260021';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-07-22',
    '2026-08-05',
    NULL,
    'ATRASADO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000043'
WHERE a.matricula = '20260022';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-07-25',
    '2026-08-08',
    NULL,
    'ATRASADO',
    1
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000044'
WHERE a.matricula = '20260023';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-07-28',
    '2026-08-11',
    NULL,
    'ATRASADO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000048'
WHERE a.matricula = '20260024';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-08-01',
    '2026-08-15',
    NULL,
    'ATRASADO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000050'
WHERE a.matricula = '20260025';


-- Empréstimos ativos
INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-08-20',
    '2026-09-03',
    NULL,
    'ATIVO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000021'
WHERE a.matricula = '20260031';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-08-21',
    '2026-09-04',
    NULL,
    'ATIVO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000027'
WHERE a.matricula = '20260032';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-08-22',
    '2026-09-05',
    NULL,
    'ATIVO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000030'
WHERE a.matricula = '20260033';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-08-23',
    '2026-09-06',
    NULL,
    'ATIVO',
    1
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000035'
WHERE a.matricula = '20260034';

INSERT INTO emprestimos (
    id_aluno,
    id_livro,
    data_emprestimo,
    data_previsao_devolucao,
    data_devolucao,
    status,
    quantidade_renovacoes
)
SELECT
    a.id_aluno,
    l.id_livro,
    '2026-08-24',
    '2026-09-07',
    NULL,
    'ATIVO',
    0
FROM alunos a
         JOIN livros l ON l.isbn = '9780000000039'
WHERE a.matricula = '20260035';


-- Multas pendentes
INSERT IGNORE INTO multas (
    valor,
    data_geracao,
    data_pagamento,
    status,
    id_emprestimo
)
SELECT
    46.00,
    '2026-08-26',
    NULL,
    'PENDENTE',
    emp.id_emprestimo
FROM emprestimos emp
         JOIN alunos a ON a.id_aluno = emp.id_aluno
         JOIN livros l ON l.id_livro = emp.id_livro
WHERE a.matricula = '20260021'
  AND l.isbn = '9780000000040'
  AND emp.status = 'ATRASADO'
ORDER BY emp.id_emprestimo DESC
    LIMIT 1;

INSERT IGNORE INTO multas (
    valor,
    data_geracao,
    data_pagamento,
    status,
    id_emprestimo
)
SELECT
    42.00,
    '2026-08-26',
    NULL,
    'PENDENTE',
    emp.id_emprestimo
FROM emprestimos emp
         JOIN alunos a ON a.id_aluno = emp.id_aluno
         JOIN livros l ON l.id_livro = emp.id_livro
WHERE a.matricula = '20260022'
  AND l.isbn = '9780000000043'
  AND emp.status = 'ATRASADO'
ORDER BY emp.id_emprestimo DESC
    LIMIT 1;

INSERT IGNORE INTO multas (
    valor,
    data_geracao,
    data_pagamento,
    status,
    id_emprestimo
)
SELECT
    36.00,
    '2026-08-26',
    NULL,
    'PENDENTE',
    emp.id_emprestimo
FROM emprestimos emp
         JOIN alunos a ON a.id_aluno = emp.id_aluno
         JOIN livros l ON l.id_livro = emp.id_livro
WHERE a.matricula = '20260023'
  AND l.isbn = '9780000000044'
  AND emp.status = 'ATRASADO'
ORDER BY emp.id_emprestimo DESC
    LIMIT 1;


-- Multas pagas
INSERT IGNORE INTO multas (
    valor,
    data_geracao,
    data_pagamento,
    status,
    id_emprestimo
)
SELECT
    30.00,
    '2026-08-20',
    '2026-08-25',
    'PAGO',
    emp.id_emprestimo
FROM emprestimos emp
         JOIN alunos a ON a.id_aluno = emp.id_aluno
         JOIN livros l ON l.id_livro = emp.id_livro
WHERE a.matricula = '20260024'
  AND l.isbn = '9780000000048'
  AND emp.status = 'ATRASADO'
ORDER BY emp.id_emprestimo DESC
    LIMIT 1;

INSERT IGNORE INTO multas (
    valor,
    data_geracao,
    data_pagamento,
    status,
    id_emprestimo
)
SELECT
    22.00,
    '2026-08-18',
    '2026-08-24',
    'PAGO',
    emp.id_emprestimo
FROM emprestimos emp
         JOIN alunos a ON a.id_aluno = emp.id_aluno
         JOIN livros l ON l.id_livro = emp.id_livro
WHERE a.matricula = '20260025'
  AND l.isbn = '9780000000050'
  AND emp.status = 'ATRASADO'
ORDER BY emp.id_emprestimo DESC
    LIMIT 1;