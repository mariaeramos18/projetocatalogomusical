--  Apagando o banco de dados se já existir
DROP DATABASE IF EXISTS catalogo_musical;

-- Criando o banco de dados
CREATE DATABASE catalogo_musical;
USE catalogo_musical;

-- Criando tabela artista
CREATE TABLE artista (
    id INT(11) NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    nacionalidade VARCHAR(50),
    generomusical VARCHAR(20)
);

-- Criando tabela musica
CREATE TABLE musica (
    id INT(11) NOT NULL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    duracao VARCHAR(10) NOT NULL,
    album VARCHAR(100),
    artista VARCHAR(100) NOT NULL
);

-- Criando tabela playlist
CREATE TABLE playlist (
    id INT(11) NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(100),
    data_criacao DATE,
    estilo VARCHAR(20)
);

-- Inserindo dados na tabela artista (com ID definidos manualmente)
INSERT INTO artista (id, nome, genero, nacionalidade, generomusical)
VALUES
    (1, 'Thiaguinho', 'Pagode', 'Brasileiro', 'Pagode'),
    (2, 'Belo', 'Pagode', 'Brasileiro', 'Pagode'),
    (3, 'Matuê', 'RAP', 'Brasileiro', 'Rap');

-- Inserindo dados na tabela musica (com ID definidos manualmente)
INSERT INTO musica (id, titulo, duracao, album, artista)
VALUES
    (1, 'Me Faz Feliz', '00:37:40', 'Tardezinha', 'Thiaguinho'),
    (2, 'Tatuagem', '01:01:00', 'Mistério', 'Belo'),
    (3, 'Maria', '00:42:32', '333', 'Matuê');

-- Inserindo dados na tabela playlist (com ID definidos manualmente)
INSERT INTO playlist (id, nome, descricao, data_criacao, estilo)
VALUES
    (1, 'Minhas Favoritas', 'playlist favorita', '2025-07-09', 'Pagode'),
    (2, 'Os Melhores Rap', 'O MELHOR RAPPER', '2025-07-09', 'Rap');