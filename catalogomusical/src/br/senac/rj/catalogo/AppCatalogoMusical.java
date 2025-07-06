package br.senac.rj.catalogo;

import br.senac.rj.catalogo.janelas.JanelaArtista;
import br.senac.rj.catalogo.janelas.JanelaMusica;
import br.senac.rj.catalogo.janelas.JanelaPlaylist;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppCatalogoMusical {

    public static void apresentarMenu() {
        JFrame janelaPrincipal = new JFrame("Catálogo Musical");
        janelaPrincipal.setSize(500, 300);
        janelaPrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaPrincipal.setResizable(false);

        // Configura os botões de diálogo para português
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");

        // Cria barra de menu
        JMenuBar menuBar = new JMenuBar();
        janelaPrincipal.setJMenuBar(menuBar);

        // Menu "Atualizar"
        JMenu menuAtualizar = new JMenu("Atualizar");
        menuBar.add(menuAtualizar);

        // Itens de menu
        JMenuItem menuArtista = new JMenuItem("Artista");
        JMenuItem menuMusica = new JMenuItem("Música");
        JMenuItem menuPlaylist = new JMenuItem("Playlist");

        menuAtualizar.add(menuArtista);
        menuAtualizar.add(menuMusica);
        menuAtualizar.add(menuPlaylist);

        // Criação das janelas correspondentes
        JFrame janelaArtista = JanelaArtista.criarJanelaArtista();
        JFrame janelaMusica = JanelaMusica.criarJanelaMusica();
        JFrame janelaPlaylist = JanelaPlaylist.criarJanelaPlaylist();

        // Ações dos menus
        menuArtista.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                janelaArtista.setVisible(true);
            }
        });

        menuMusica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                janelaMusica.setVisible(true);
            }
        });

        menuPlaylist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                janelaPlaylist.setVisible(true);
            }
        });

        // Exibe a janela principal
        janelaPrincipal.setVisible(true);
    }

    public static void main(String[] args) {
        apresentarMenu();
    }
}
