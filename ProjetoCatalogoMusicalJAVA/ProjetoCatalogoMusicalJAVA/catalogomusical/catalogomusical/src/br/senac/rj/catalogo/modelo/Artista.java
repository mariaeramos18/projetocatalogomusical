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
    private String generoMusical;

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

    public String getGeneroMusical() {
        return generoMusical;
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

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    // Método para cadastrar artista
    public boolean cadastrarArtista(int id, String nome, String nacionalidade, String generoMusical, String genero) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            
            // Verifique se o ID já existe
            if (idExiste(id)) {
                System.out.println("Erro: ID " + id + " já existe na base de dados");
                return false;
            }
            
            String sql = "INSERT INTO artista (id, nome, nacionalidade, generomusical, genero) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nome.trim());
            ps.setString(3, nacionalidade.trim());
            ps.setString(4, generoMusical.trim());
            ps.setString(5, genero.trim());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar artista: " + erro.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Método auxiliar para verificar se ID existe
    private boolean idExiste(int id) throws SQLException {
        Connection conexao = Conexao.conectaBanco();
        try {
            String sql = "SELECT id FROM artista WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
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
                this.generoMusical = rs.getString("generomusical");
                this.genero = rs.getString("genero");
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
    public boolean atualizarArtista(int id, String nome, String nacionalidade, String generomusical, String genero) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "UPDATE artista SET nome=?, nacionalidade=?, generoMusical=?, genero=? WHERE id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, nacionalidade);
            ps.setString(3, generomusical);
            ps.setString(4, genero);
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
