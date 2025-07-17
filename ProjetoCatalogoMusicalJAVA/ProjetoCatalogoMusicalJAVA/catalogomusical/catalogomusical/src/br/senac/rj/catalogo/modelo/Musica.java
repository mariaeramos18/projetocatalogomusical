package br.senac.rj.catalogo.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Musica {
    private int id;
    private String titulo;
    private String duracao;
    private String album;
    private String artista;

    // Construtor vazio
    public Musica() {}

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    // Método para consultar música por ID
    public boolean consultarMusica(int idMusica) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "SELECT * FROM musica WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idMusica);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                this.id = rs.getInt("id");
                this.titulo = rs.getString("titulo");
                this.duracao = rs.getString("duracao");
                this.album = rs.getString("album");
                this.artista = rs.getString("artista");
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar música: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Método para cadastrar nova música
    public boolean cadastrarMusica(int id, String titulo, String duracao, String album, String artista) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "INSERT INTO musica (id, titulo, duracao, album, artista) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, titulo);
            ps.setString(3, duracao);
            ps.setString(4, album);
            ps.setString(5, artista);

            int registrosAfetados = ps.executeUpdate();
            return registrosAfetados > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar música: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Método para atualizar música
    public boolean atualizarMusica(int id, String titulo, String duracao, String album, String artista) {
        if (!consultarMusica(id)) return false;

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "UPDATE musica SET titulo = ?, duracao = ?, album = ?, artista = ? WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, duracao);
            ps.setString(3, album);
            ps.setString(4, artista);
            ps.setInt(5, id);

            int registrosAfetados = ps.executeUpdate();
            return registrosAfetados > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar música: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }

    // Método para excluir música
    public boolean excluirMusica(int id) {
        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "DELETE FROM musica WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            int registrosAfetados = ps.executeUpdate();
            return registrosAfetados > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir música: " + e.toString());
            return false;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
}
