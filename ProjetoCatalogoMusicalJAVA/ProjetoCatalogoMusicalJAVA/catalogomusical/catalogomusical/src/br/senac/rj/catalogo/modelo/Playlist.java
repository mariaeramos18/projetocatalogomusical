package br.senac.rj.catalogo.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Playlist {
    private int id;
    private String nome;
    private String descricao;
    private String dataCriacao;
    private String estilo;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    // Consultar
    public boolean consultarPlaylist(int id) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "SELECT * FROM playlist WHERE id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.id = rs.getInt("id");
                this.nome = rs.getString("nome");
                this.descricao = rs.getString("descricao");
                this.dataCriacao = rs.getString("data_criacao");
                this.estilo = rs.getString("estilo");
                return true;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao consultar playlist: " + erro);
        } finally {
            Conexao.fechaConexao(conexao);
        }
        return false;
    }

    // Cadastrar
    public boolean cadastrarPlaylist(int id, String nome, String descricao, String dataCriacao, String estilo) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "INSERT INTO playlist (id, nome, descricao, data_criacao, estilo) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nome.trim());
            ps.setString(3, descricao.trim());
            ps.setString(4, dataCriacao.trim());
            ps.setString(5, estilo.trim());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar playlist: " + erro.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Atualizar
    public boolean atualizarPlaylist(int id, String nome, String descricao, String dataCriacao, String estilo) {
        if (!consultarPlaylist(id)) return false;

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "UPDATE playlist SET nome=?, descricao=?, data_criacao=?, estilo=? WHERE id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, descricao);
            ps.setString(3, dataCriacao);
            ps.setString(4, estilo);
            ps.setInt(5, id);
            int totalAfetado = ps.executeUpdate();
            return totalAfetado > 0;
        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar playlist: " + erro);
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Excluir
    public boolean excluirPlaylist(int id) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "DELETE FROM playlist WHERE id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            int totalAfetado = ps.executeUpdate();
            return totalAfetado > 0;
        } catch (SQLException erro) {
            System.out.println("Erro ao excluir playlist: " + erro);
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
 // Na classe Playlist
    public boolean existePlaylist(int id) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "SELECT id FROM playlist WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar playlist: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
}
