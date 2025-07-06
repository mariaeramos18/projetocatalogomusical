package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.modelo.Artista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaArtista {
    public static JFrame criarJanelaArtista() {
        JFrame janela = new JFrame("Gerenciamento de Artistas");
        janela.setResizable(false);
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janela.setSize(450, 320);

        Container caixa = janela.getContentPane();
        caixa.setLayout(null);

        // Labels
        JLabel labelId = new JLabel("ID:");
        JLabel labelNome = new JLabel("Nome:");
        JLabel labelNacionalidade = new JLabel("Nacionalidade:");
        JLabel labelGeneroMusical = new JLabel("Gênero Musical:");
        JLabel labelGenero = new JLabel("Gênero:");

        labelId.setBounds(30, 30, 150, 20);
        labelNome.setBounds(30, 60, 100, 20);
        labelNacionalidade.setBounds(30, 90, 100, 20);
        labelGeneroMusical.setBounds(30, 120, 150, 20);
        labelGenero.setBounds(30, 150, 150, 20);

        // Campos de texto
        JTextField jTextId = new JTextField();
        JTextField jTextNome = new JTextField();
        JTextField jTextNacionalidade = new JTextField();
        JTextField jTextGeneroMusical = new JTextField();
        JTextField jTextGenero = new JTextField();

        jTextId.setBounds(200, 30, 50, 20);
        jTextNome.setBounds(200, 60, 200, 20);
        jTextNacionalidade.setBounds(200, 90, 200, 20);
        jTextGeneroMusical.setBounds(200, 120, 200, 20);
        jTextGenero.setBounds(200, 150, 200, 20);

        // Botões
        JButton botaoConsultar = new JButton("Consultar");
        JButton botaoGravar = new JButton("Gravar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoLimpar = new JButton("Limpar");

        botaoConsultar.setBounds(280, 30, 100, 20);
        botaoGravar.setBounds(30, 200, 100, 30);
        botaoExcluir.setBounds(160, 200, 100, 30);
        botaoLimpar.setBounds(290, 200, 100, 30);

        botaoGravar.setEnabled(false);
        botaoExcluir.setEnabled(false);

        // Adiciona componentes
        janela.add(labelId);
        janela.add(labelNome);
        janela.add(labelNacionalidade);
        janela.add(labelGeneroMusical);
        janela.add(labelGenero);

        janela.add(jTextId);
        janela.add(jTextNome);
        janela.add(jTextNacionalidade);
        janela.add(jTextGeneroMusical);
        janela.add(jTextGenero);

        janela.add(botaoConsultar);
        janela.add(botaoGravar);
        janela.add(botaoExcluir);
        janela.add(botaoLimpar);

        // Objeto Artista
        Artista artista = new Artista();

        // Ações dos botões
        botaoConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(jTextId.getText());
                    if (artista.consultarArtista(id)) {
                        jTextNome.setText(artista.getNome());
                        jTextNacionalidade.setText(artista.getNacionalidade());
                        jTextGeneroMusical.setText(artista.getGeneroMusical());
                        jTextGenero.setText(artista.getGenero());

                        botaoGravar.setEnabled(true);
                        botaoExcluir.setEnabled(true);
                        jTextId.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Artista não encontrado. Cadastre abaixo.");
                        jTextNome.setText("");
                        jTextNacionalidade.setText("");
                        jTextGeneroMusical.setText("");
                        jTextGenero.setText("");
                        botaoGravar.setEnabled(true);
                        botaoExcluir.setEnabled(false);
                    }

                    jTextNome.setEnabled(true);
                    jTextNacionalidade.setEnabled(true);
                    jTextGeneroMusical.setEnabled(true);
                    jTextGenero.setEnabled(true);
                    jTextNome.requestFocus();

                    botaoConsultar.setEnabled(false);
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(janela, "ID inválido.");
                }
            }
        });

        botaoGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela, "Deseja salvar as informações?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(jTextId.getText());
                    String nome = jTextNome.getText().trim();
                    String nacionalidade = jTextNacionalidade.getText().trim();
                    String generoMusical = jTextGeneroMusical.getText().trim();
                    String genero = jTextGenero.getText().trim();

                    if (nome.isEmpty()) {
                        JOptionPane.showMessageDialog(janela, "Preencha o nome do artista.");
                        return;
                    }

                    if (!artista.consultarArtista(id)) {
                        if (artista.cadastrarArtista(nome, nacionalidade, generoMusical, genero))
                            JOptionPane.showMessageDialog(janela, "Artista cadastrado com sucesso.");
                        else
                            JOptionPane.showMessageDialog(janela, "Erro ao cadastrar artista.");
                    } else {
                        if (artista.atualizarArtista(id, nome, nacionalidade, generoMusical, genero))
                            JOptionPane.showMessageDialog(janela, "Artista atualizado com sucesso.");
                        else
                            JOptionPane.showMessageDialog(janela, "Erro ao atualizar artista.");
                    }
                }
            }
        });

        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela, "Deseja excluir o artista?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(jTextId.getText());
                    if (artista.excluirArtista(id))
                        JOptionPane.showMessageDialog(janela, "Artista excluído com sucesso.");
                    else
                        JOptionPane.showMessageDialog(janela, "Erro ao excluir artista.");
                    botaoExcluir.setEnabled(false);
                }
            }
        });

        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jTextId.setText("");
                jTextNome.setText("");
                jTextNacionalidade.setText("");
                jTextGeneroMusical.setText("");
                jTextGenero.setText("");

                jTextId.setEnabled(true);
                jTextNome.setEnabled(false);
                jTextNacionalidade.setEnabled(false);
                jTextGeneroMusical.setEnabled(false);
                jTextGenero.setEnabled(false);

                botaoConsultar.setEnabled(true);
                botaoGravar.setEnabled(false);
                botaoExcluir.setEnabled(false);
            }
        });

        return janela;
    }
}

