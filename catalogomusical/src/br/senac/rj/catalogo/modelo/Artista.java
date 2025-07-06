package br.senac.rj.catalogo.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Artista {
    private int id;
    private String nome;
    private String nacionalidade;
    private String genero;
    private String dataNascimento;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public String getGenero() {
        return genero;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    // Método para cadastrar artista
    public boolean cadastrarArtista(String nome, String nacionalidade, String genero, String dataNascimento) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "INSERT INTO artista (nome, nacionalidade, genero, data_nascimento) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, nacionalidade);
            ps.setString(3, genero);
            ps.setString(4, dataNascimento);
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar artista: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Método para consultar artista por ID
    public boolean consultarArtista(int id) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "SELECT * FROM artista WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.id = rs.getInt("id");
                this.nome = rs.getString("nome");
                this.nacionalidade = rs.getString("nacionalidade");
                this.genero = rs.getString("genero");
                this.dataNascimento = rs.getString("data_nascimento");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar artista: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Método para atualizar artista
    public boolean atualizarArtista(int id, String nome, String nacionalidade, String genero, String dataNascimento) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "UPDATE artista SET nome=?, nacionalidade=?, genero=?, data_nascimento=? WHERE id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, nacionalidade);
            ps.setString(3, genero);
            ps.setString(4, dataNascimento);
            ps.setInt(5, id);
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar artista: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Método para excluir artista
    public boolean excluirArtista(int id) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "DELETE FROM artista WHERE id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir artista: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
}
