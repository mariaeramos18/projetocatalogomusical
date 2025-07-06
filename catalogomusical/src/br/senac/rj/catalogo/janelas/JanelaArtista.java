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
        janela.setSize(500, 350);

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
        JButton botaoCadastrar = new JButton("Cadastrar");
        JButton botaoAtualizar = new JButton("Atualizar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoLimpar = new JButton("Limpar");

        botaoConsultar.setBounds(280, 30, 100, 20);
        botaoCadastrar.setBounds(30, 220, 100, 30);
        botaoAtualizar.setBounds(140, 220, 100, 30);
        botaoExcluir.setBounds(250, 220, 100, 30);
        botaoLimpar.setBounds(360, 220, 100, 30);

        // Estado inicial dos botões
        botaoCadastrar.setEnabled(false);
        botaoAtualizar.setEnabled(false);
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
        janela.add(botaoCadastrar);
        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);
        janela.add(botaoLimpar);

        // Objeto Artista
        Artista artista = new Artista();

        // CONSULTAR
        botaoConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(jTextId.getText());
                    if (artista.consultarArtista(id)) {
                        jTextNome.setText(artista.getNome());
                        jTextNacionalidade.setText(artista.getNacionalidade());
                        jTextGeneroMusical.setText(artista.getGeneroMusical());
                        jTextGenero.setText(artista.getGenero());

                        botaoAtualizar.setEnabled(true);
                        botaoExcluir.setEnabled(true);
                        botaoCadastrar.setEnabled(false);
                        botaoLimpar.setEnabled(true);
                        habilitarCampos(true, jTextNome, jTextNacionalidade, jTextGeneroMusical, jTextGenero);
                        jTextId.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Artista não encontrado. Preencha os dados para cadastrar.");
                        limparCampos(jTextNome, jTextNacionalidade, jTextGeneroMusical, jTextGenero);
                        botaoCadastrar.setEnabled(true);
                        botaoAtualizar.setEnabled(false);
                        botaoExcluir.setEnabled(false);
                        botaoLimpar.setEnabled(true);
                        habilitarCampos(true, jTextNome, jTextNacionalidade, jTextGeneroMusical, jTextGenero);
                    }
                    botaoConsultar.setEnabled(false);
                    jTextNome.requestFocus();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "ID inválido. Digite um número inteiro.");
                }
            }
        });

        // CADASTRAR
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    int resposta = JOptionPane.showConfirmDialog(janela,
                            "Confirmar cadastro deste artista?", "Confirmação", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(jTextId.getText());
                        String nome = jTextNome.getText().trim();
                        String nacionalidade = jTextNacionalidade.getText().trim();
                        String generoMusical = jTextGeneroMusical.getText().trim();
                        String genero = jTextGenero.getText().trim();

                        if (artista.cadastrarArtista(id, nome, nacionalidade, generoMusical, genero)) {
                            JOptionPane.showMessageDialog(janela, "Artista cadastrado com sucesso!");
                            limparTudo(jTextId, jTextNome, jTextNacionalidade, jTextGeneroMusical, jTextGenero,
                                    botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                        } else {
                            JOptionPane.showMessageDialog(janela, "Falha ao cadastrar artista.");
                        }
                    }
                }
            }
        });

        // ATUALIZAR
        botaoAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    int resposta = JOptionPane.showConfirmDialog(janela,
                            "Confirmar atualização deste artista?", "Confirmação", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(jTextId.getText());
                        String nome = jTextNome.getText().trim();
                        String nacionalidade = jTextNacionalidade.getText().trim();
                        String generoMusical = jTextGeneroMusical.getText().trim();
                        String genero = jTextGenero.getText().trim();

                        if (artista.atualizarArtista(id, nome, nacionalidade, generoMusical, genero)) {
                            JOptionPane.showMessageDialog(janela, "Artista atualizado com sucesso!");
                            limparTudo(jTextId, jTextNome, jTextNacionalidade, jTextGeneroMusical, jTextGenero,
                                    botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                        } else {
                            JOptionPane.showMessageDialog(janela, "Falha ao atualizar artista.");
                        }
                    }
                }
            }
        });

        // EXCLUIR
        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela,
                        "Confirmar exclusão deste artista?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(jTextId.getText());
                    if (artista.excluirArtista(id)) {
                        JOptionPane.showMessageDialog(janela, "Artista excluído com sucesso!");
                        limparTudo(jTextId, jTextNome, jTextNacionalidade, jTextGeneroMusical, jTextGenero,
                                botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Falha ao excluir artista.");
                    }
                }
            }
        });

        // LIMPAR
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTudo(jTextId, jTextNome, jTextNacionalidade, jTextGeneroMusical, jTextGenero,
                        botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
            }
        });

        return janela;
    }

    // Métodos auxiliares
    private static void limparCampos(JTextField nome, JTextField nacionalidade,
                                     JTextField generoMusical, JTextField genero) {
        nome.setText("");
        nacionalidade.setText("");
        generoMusical.setText("");
        genero.setText("");
    }

    private static void habilitarCampos(boolean habilitar, JTextField nome, JTextField nacionalidade,
                                        JTextField generoMusical, JTextField genero) {
        nome.setEnabled(habilitar);
        nacionalidade.setEnabled(habilitar);
        generoMusical.setEnabled(habilitar);
        genero.setEnabled(habilitar);
    }

    private static void limparTudo(JTextField id, JTextField nome, JTextField nacionalidade,
                                   JTextField generoMusical, JTextField genero,
                                   JButton consultar, JButton cadastrar, JButton atualizar, JButton excluir) {
        id.setText("");
        nome.setText("");
        nacionalidade.setText("");
        generoMusical.setText("");
        genero.setText("");

        id.setEnabled(true);
        nome.setEnabled(false);
        nacionalidade.setEnabled(false);
        generoMusical.setEnabled(false);
        genero.setEnabled(false);

        consultar.setEnabled(true);
        cadastrar.setEnabled(false);
        atualizar.setEnabled(false);
        excluir.setEnabled(false);

        id.requestFocus();
    }

    private static boolean validarCampos() {
        // Aqui você pode adicionar validações reais no futuro
        return true;
    }
}


