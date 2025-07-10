package br.senac.rj.catalogo;

import br.senac.rj.catalogo.janelas.JanelaArtista;
import br.senac.rj.catalogo.janelas.JanelaMusica;
import br.senac.rj.catalogo.janelas.JanelaPlaylist;
import br.senac.rj.catalogo.modelo.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppCatalogoMusical {

    public static void apresentarMenu() {
        JFrame janelaPrincipal = new JFrame("Catálogo Musical");
        janelaPrincipal.setSize(700, 400);
        janelaPrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaPrincipal.setResizable(false);

        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");

        JMenuBar menuBar = new JMenuBar();
        JMenu menuAtualizar = new JMenu("Atualizar");
        menuBar.add(menuAtualizar);

        JMenuItem menuArtista = new JMenuItem("Artista");
        JMenuItem menuMusica = new JMenuItem("Música");
        JMenuItem menuPlaylist = new JMenuItem("Playlist");
        menuAtualizar.add(menuArtista);
        menuAtualizar.add(menuMusica);
        menuAtualizar.add(menuPlaylist);

        // Remove o botão "Mudar Cor" e o espaçamento extra
        janelaPrincipal.setJMenuBar(menuBar);

        JFrame janelaArtista = JanelaArtista.criarJanelaArtista();
        JFrame janelaMusica = JanelaMusica.criarJanelaMusica();
        JFrame janelaPlaylist = JanelaPlaylist.criarJanelaPlaylist();

        menuArtista.addActionListener(e -> janelaArtista.setVisible(true));
        menuMusica.addActionListener(e -> janelaMusica.setVisible(true));
        menuPlaylist.addActionListener(e -> janelaPlaylist.setVisible(true));

        // ----------- TABELAS COM DADOS DO BANCO DE DADOS ------------
        JTabbedPane abas = new JTabbedPane();

        JTable tabelaMusica = criarTabelaMusica();
        JScrollPane scrollMusica = new JScrollPane(tabelaMusica);
        abas.addTab("Músicas", scrollMusica);

        JTable tabelaArtista = criarTabelaArtista();
        JScrollPane scrollArtista = new JScrollPane(tabelaArtista);
        abas.addTab("Artistas", scrollArtista);

        JTable tabelaPlaylist = criarTabelaPlaylist();
        JScrollPane scrollPlaylist = new JScrollPane(tabelaPlaylist);
        abas.addTab("Playlists", scrollPlaylist);

        janelaPrincipal.add(abas);
        janelaPrincipal.setVisible(true);
    }

    // ---------- TABELA DE MÚSICAS ----------
    private static JTable criarTabelaMusica() {
        String[] colunas = {"ID", "Título", "Duração", "Álbum", "Artista"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        try (Connection con = Conexao.conectaBanco();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM musica")) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("duracao"),
                    rs.getString("album"),
                    rs.getString("artista")
                };
                modelo.addRow(linha);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar músicas: " + e);
        }

        return new JTable(modelo);
    }

    // ---------- TABELA DE ARTISTAS ----------
    private static JTable criarTabelaArtista() {
        String[] colunas = {"ID", "Nome", "Nacionalidade", "Gênero Musical", "Gênero"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        try (Connection con = Conexao.conectaBanco();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM artista")) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("nacionalidade"),
                    rs.getString("generomusical"),
                    rs.getString("genero")
                };
                modelo.addRow(linha);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar artistas: " + e);
        }

        return new JTable(modelo);
    }

    // ---------- TABELA DE PLAYLISTS ----------
    private static JTable criarTabelaPlaylist() {
        String[] colunas = {"ID", "Nome", "Descrição", "Data Criação", "Estilo"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        try (Connection con = Conexao.conectaBanco();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM playlist")) {

            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("data_criacao"),
                    rs.getString("estilo")
                };
                modelo.addRow(linha);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar playlists: " + e);
        }

        return new JTable(modelo);
    }

    public static void main(String[] args) {
        apresentarMenu();
    }
}
