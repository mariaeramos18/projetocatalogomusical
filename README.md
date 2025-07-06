# Catálogo Musical - Java Swing + MySQL

Este projeto implementa uma aplicação desktop utilizando **Java Swing** com conexão a banco de dados **MySQL**. A aplicação possui janelas independentes (via `JFrame`) para gerenciamento de:

- Artistas
- Músicas
- Playlists

Cada janela permite operações de **CRUD** (Criar, Consultar, Atualizar, Excluir) e possui controle dinâmico da interface (habilitação/desabilitação de campos e botões) com confirmação de ações críticas.

---

## 📌 Funcionalidades

  1. Interface gráfica intuitiva com `JFrame`  
  2. Menu principal com chamada de janelas por tipo de dado  
  3. Consulta de registros por ID  
  4. Cadastro de novos registros  
  5. Atualização de dados existentes  
  6. Exclusão de registros com confirmação  
  7. Controle de habilitação dos botões conforme a ação  
  8. Validação de campos obrigatórios

---

## 🗃️ Estrutura das Tabelas

Cada tabela possui no mínimo **4 campos**.  
Exemplo de tabelas utilizadas:

### 🎤 `artista`
- `id` (INT)
- `nome` (VARCHAR)
- `nacionalidade` (VARCHAR)
- `genero_musical` (VARCHAR)
- `genero` (VARCHAR)

### 🎵 `musica`
- `id` (INT)
- `titulo` (VARCHAR)
- `artista` (VARCHAR)
- `duracao` (VARCHAR)
- `genero` (VARCHAR)

### 📀 `playlist`
- `id` (INT)
- `nome` (VARCHAR)
- `descricao` (VARCHAR)
- `data_criacao` (VARCHAR)
- `estilo` (VARCHAR)

---

## ⚙️ Tecnologias

- Java 8+
- Java Swing (JFrame, JLabel, JTextField, JButton, JMenuBar, etc.)
- JDBC (Java Database Connectivity)
- MySQL
