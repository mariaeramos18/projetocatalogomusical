package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.modelo.Musica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaMusica {

    public static JFrame criarJanelaMusica() {
        JFrame janela = new JFrame("Cadastro de Música");
        janela.setSize(450, 350);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Container caixa = janela.getContentPane();
        caixa.setLayout(null);

        // Labels
        JLabel lblId = new JLabel("ID:");
        JLabel lblTitulo = new JLabel("Título:");
        JLabel lblDuracao = new JLabel("Duração:");
        JLabel lblAlbum = new JLabel("Álbum:");
        JLabel lblArtista = new JLabel("Artista:");

        lblId.setBounds(30, 20, 80, 20);
        lblTitulo.setBounds(30, 60, 80, 20);
        lblDuracao.setBounds(30, 100, 80, 20);
        lblAlbum.setBounds(30, 140, 80, 20);
        lblArtista.setBounds(30, 180, 80, 20);

        // Campos de texto
        JTextField txtId = new JTextField();
        JTextField txtTitulo = new JTextField();
        JTextField txtDuracao = new JTextField();
        JTextField txtAlbum = new JTextField();
        JTextField txtArtista = new JTextField();

        txtId.setBounds(120, 20, 60, 20);
        txtTitulo.setBounds(120, 60, 250, 20);
        txtDuracao.setBounds(120, 100, 100, 20);
        txtAlbum.setBounds(120, 140, 250, 20);
        txtArtista.setBounds(120, 180, 250, 20);

        // Botões
        JButton btnConsultar = new JButton("Consultar");
        JButton btnGravar = new JButton("Gravar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        btnConsultar.setBounds(200, 20, 100, 20);
        btnGravar.setBounds(30, 240, 80, 25);
        btnAtualizar.setBounds(120, 240, 100, 25);
        btnExcluir.setBounds(230, 240, 80, 25);
        btnLimpar.setBounds(320, 240, 80, 25);

        // Adiciona os componentes à janela
        janela.add(lblId);       janela.add(txtId);
        janela.add(lblTitulo);   janela.add(txtTitulo);
        janela.add(lblDuracao);  janela.add(txtDuracao);
        janela.add(lblAlbum);    janela.add(txtAlbum);
        janela.add(lblArtista);  janela.add(txtArtista);

        janela.add(btnConsultar);
        janela.add(btnGravar);
        janela.add(btnAtualizar);
        janela.add(btnExcluir);
        janela.add(btnLimpar);

        // Objeto da classe Musica
        Musica musica = new Musica();

        // Ações dos botões
        btnConsultar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (musica.consultarMusica(id)) {
                    txtTitulo.setText(musica.getTitulo());
                    txtDuracao.setText(musica.getDuracao());
                    txtAlbum.setText(musica.getAlbum());
                    txtArtista.setText(musica.getArtista());
                    JOptionPane.showMessageDialog(janela, "Música encontrada!");
                } else {
                    JOptionPane.showMessageDialog(janela, "Música não encontrada.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(janela, "Digite um ID válido.");
            }
        });

        btnGravar.addActionListener(e -> {
            String titulo = txtTitulo.getText().trim();
            String duracao = txtDuracao.getText().trim();
            String album = txtAlbum.getText().trim();
            String artista = txtArtista.getText().trim();

            if (titulo.isEmpty() || duracao.isEmpty() || artista.isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Preencha os campos obrigatórios: Título, Duração e Artista.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(janela, "Deseja cadastrar esta música?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean sucesso = musica.cadastrarMusica(titulo, duracao, album, artista);
                JOptionPane.showMessageDialog(janela, sucesso ? "Música cadastrada!" : "Erro ao cadastrar música.");
            }
        });

        btnAtualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String titulo = txtTitulo.getText().trim();
                String duracao = txtDuracao.getText().trim();
                String album = txtAlbum.getText().trim();
                String artista = txtArtista.getText().trim();

                if (titulo.isEmpty() || duracao.isEmpty() || artista.isEmpty()) {
                    JOptionPane.showMessageDialog(janela, "Preencha os campos obrigatórios.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(janela, "Deseja atualizar esta música?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean sucesso = musica.atualizarMusica(id, titulo, duracao, album, artista);
                    JOptionPane.showMessageDialog(janela, sucesso ? "Música atualizada!" : "Erro ao atualizar música.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(janela, "Digite um ID válido.");
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                int confirm = JOptionPane.showConfirmDialog(janela, "Deseja excluir esta música?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean sucesso = musica.excluirMusica(id);
                    JOptionPane.showMessageDialog(janela, sucesso ? "Música excluída!" : "Erro ao excluir música.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(janela, "Digite um ID válido.");
            }
        });

        btnLimpar.addActionListener(e -> {
            txtId.setText("");
            txtTitulo.setText("");
            txtDuracao.setText("");
            txtAlbum.setText("");
            txtArtista.setText("");
            txtId.requestFocus();
        });

        return janela;
    }
}
