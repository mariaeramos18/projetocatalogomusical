package br.senac.rj.catalogo;

import br.senac.rj.catalogo.janelas.JanelaArtista;
import br.senac.rj.catalogo.janelas.JanelaMusica;
import br.senac.rj.catalogo.janelas.JanelaPlaylist;
import br.senac.rj.catalogo.modelo.Conexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppCatalogoMusical {

    // Janelas declaradas como estáticas para serem acessadas pelos listeners
    private static JanelaMusica janelaMusica;
    private static JanelaArtista janelaArtista;
    private static JanelaPlaylist janelaPlaylist;
    
    // Transformando as tabelas em campos estáticos
    private static JTable tabelaMusica;
    private static JTable tabelaArtista;
    private static JTable tabelaPlaylist;

    public static void apresentarMenu() {
        JFrame janelaPrincipal = new JFrame("Music Catalog");
        janelaPrincipal.setSize(700, 400);
        janelaPrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaPrincipal.setResizable(false);

        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");

        JMenuBar menuBar = new JMenuBar();
        JMenu menuAtualizar = new JMenu("Update");
        menuBar.add(menuAtualizar);

        JMenuItem menuArtista = new JMenuItem("Artist");
        JMenuItem menuMusica = new JMenuItem("Music");
        JMenuItem menuPlaylist = new JMenuItem("Playlist");
        menuAtualizar.add(menuArtista);
        menuAtualizar.add(menuMusica);
        menuAtualizar.add(menuPlaylist);

        janelaPrincipal.setJMenuBar(menuBar);

        // Instancia as janelas de gerenciamento
        janelaArtista = new JanelaArtista();
        janelaMusica = new JanelaMusica();
        janelaPlaylist = new JanelaPlaylist();

        // Ação do menu: abre a janela limpa para um novo cadastro
        menuArtista.addActionListener(e -> {
            janelaArtista.limparTudo();
            janelaArtista.setVisible(true);
        });
        menuMusica.addActionListener(e -> {
            janelaMusica.limparTudo();
            janelaMusica.setVisible(true);
        });
        menuPlaylist.addActionListener(e -> {
            janelaPlaylist.limparTudo();
            janelaPlaylist.setVisible(true);
        });

        // ----------- TABELAS COM DADOS DO BANCO DE DADOS ------------
        JTabbedPane abas = new JTabbedPane();

        tabelaMusica = criarTabelaMusica();
        JScrollPane scrollMusica = new JScrollPane(tabelaMusica);
        abas.addTab("Music", scrollMusica);

        tabelaArtista = criarTabelaArtista();
        JScrollPane scrollArtista = new JScrollPane(tabelaArtista);
        abas.addTab("Artists", scrollArtista);

        tabelaPlaylist = criarTabelaPlaylist();
        JScrollPane scrollPlaylist = new JScrollPane(tabelaPlaylist);
        abas.addTab("Playlists", scrollPlaylist);

        janelaPrincipal.add(abas);
        janelaPrincipal.setVisible(true);
    }

    // ---------- TABELA DE MÚSICAS ----------
    private static JTable criarTabelaMusica() {
        String[] colunas = {"ID", "Title", "Duration", "Album", "Artist"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);

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
            System.out.println("Error fetching songs: " + e);
        }
        
        // Adiciona o listener de mouse para abrir a janela de música com dados preenchidos
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Captura o duplo-clique
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String id = target.getValueAt(row, 0).toString();
                        String titulo = target.getValueAt(row, 1).toString();
                        String duracao = target.getValueAt(row, 2).toString();
                        String album = target.getValueAt(row, 3).toString();
                        String artista = target.getValueAt(row, 4).toString();
                        
                        janelaMusica.preencherEabrir(id, titulo, duracao, album, artista);
                    }
                }
            }
        });
        return tabela;
    }

    // ---------- TABELA DE ARTISTAS ----------
    private static JTable criarTabelaArtista() {
        String[] colunas = {"ID", "Name", "Nationality", "Music genre", "Genre"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);
        
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
            System.out.println("Error fetching artists: " + e);
        }

        // Adiciona o listener de mouse para abrir a janela de artista com dados preenchidos
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Captura o duplo-clique
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String id = target.getValueAt(row, 0).toString();
                        String nome = target.getValueAt(row, 1).toString();
                        String nacionalidade = target.getValueAt(row, 2).toString();
                        String generoMusical = target.getValueAt(row, 3).toString();
                        String genero = target.getValueAt(row, 4).toString();
                        
                        janelaArtista.preencherEabrir(id, nome, nacionalidade, generoMusical, genero);
                    }
                }
            }
        });
        return tabela;
    }

    // ---------- TABELA DE PLAYLISTS ----------
    private static JTable criarTabelaPlaylist() {
        String[] colunas = {"ID", "Name", "Description", "Creation date", "Music genre"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(modelo);

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
            System.out.println("Error fetching playlists: " + e);
        }

        // Adiciona o listener de mouse para abrir a janela de playlist com dados preenchidos
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Captura o duplo-clique
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String id = target.getValueAt(row, 0).toString();
                        String nome = target.getValueAt(row, 1).toString();
                        String descricao = target.getValueAt(row, 2).toString();
                        String data = target.getValueAt(row, 3).toString();
                        String estilo = target.getValueAt(row, 4).toString();
                        
                        janelaPlaylist.preencherEabrir(id, nome, descricao, data, estilo);
                    }
                }
            }
        });
        return tabela;
    }

    public static void main(String[] args) {
        // Assegura que a UI seja criada na thread de despacho de eventos
        SwingUtilities.invokeLater(AppCatalogoMusical::apresentarMenu);
    }
    
 // Adicione estes três métodos ao final da classe AppCatalogoMusical.java

    public static void atualizarTabelaMusica() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaMusica.getModel();
        modelo.setRowCount(0); // Clean the table

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
            System.out.println("Error updating songs table: " + e);
        }
    }

    public static void atualizarTabelaArtista() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaArtista.getModel();
        modelo.setRowCount(0); // Clean the table

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
            System.out.println("Error updating artists table: " + e);
        }
    }

    public static void atualizarTabelaPlaylist() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaPlaylist.getModel();
        modelo.setRowCount(0); // Clean the table

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
            System.out.println("Error updating playlist table: " + e);
        }
    }
}
