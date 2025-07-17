package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.modelo.Musica;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaMusica {
    public static JFrame criarJanelaMusica() {
        JFrame janela = new JFrame("Gerenciamento de Músicas:");
        janela.setResizable(false);
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janela.setSize(500, 350);

        Container caixa = janela.getContentPane();
        caixa.setLayout(null);

        // Labels
        JLabel labelId = new JLabel("ID:");
        JLabel labelTitulo = new JLabel("Título:");
        JLabel labelDuracao = new JLabel("Duração:");
        JLabel labelAlbum = new JLabel("Álbum:");
        JLabel labelArtista = new JLabel("Artista:");

        labelId.setBounds(30, 30, 150, 20);
        labelTitulo.setBounds(30, 60, 100, 20);
        labelDuracao.setBounds(30, 90, 100, 20);
        labelAlbum.setBounds(30, 120, 150, 20);
        labelArtista.setBounds(30, 150, 150, 20);

        // Campos de texto
        JTextField jTextId = new JTextField();
        JTextField jTextTitulo = new JTextField();
        JTextField jTextDuracao = new JTextField();
        JTextField jTextAlbum = new JTextField();
        JTextField jTextArtista = new JTextField();

        jTextId.setBounds(200, 30, 50, 20);
        jTextTitulo.setBounds(200, 60, 200, 20);
        jTextDuracao.setBounds(200, 90, 200, 20);
        jTextAlbum.setBounds(200, 120, 200, 20);
        jTextArtista.setBounds(200, 150, 200, 20);

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

        // Estado inicial
        botaoCadastrar.setEnabled(false);
        botaoAtualizar.setEnabled(false);
        botaoExcluir.setEnabled(false);
        habilitarCampos(false, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista);

        // Adiciona componentes
        janela.add(labelId);
        janela.add(labelTitulo);
        janela.add(labelDuracao);
        janela.add(labelAlbum);
        janela.add(labelArtista);

        janela.add(jTextId);
        janela.add(jTextTitulo);
        janela.add(jTextDuracao);
        janela.add(jTextAlbum);
        janela.add(jTextArtista);

        janela.add(botaoConsultar);
        janela.add(botaoCadastrar);
        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);
        janela.add(botaoLimpar);

        Musica musica = new Musica();

        // CONSULTAR
        botaoConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(jTextId.getText());
                    if (musica.consultarMusica(id)) {
                        jTextTitulo.setText(musica.getTitulo());
                        jTextDuracao.setText(musica.getDuracao());
                        jTextAlbum.setText(musica.getAlbum());
                        jTextArtista.setText(musica.getArtista());

                        botaoAtualizar.setEnabled(true);
                        botaoExcluir.setEnabled(true);
                        botaoCadastrar.setEnabled(false);
                        habilitarCampos(true, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista);
                        jTextId.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Música não encontrada. Preencha os dados para cadastrar.");
                        limparCampos(jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista);
                        botaoCadastrar.setEnabled(true);
                        botaoAtualizar.setEnabled(false);
                        botaoExcluir.setEnabled(false);
                        habilitarCampos(true, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista);
                    }
                    botaoConsultar.setEnabled(false);
                    botaoLimpar.setEnabled(true);
                    jTextTitulo.requestFocus();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "ID inválido. Digite um número inteiro.");
                }
            }
        });

        // CADASTRAR
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validarCampos(jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista)) {
                    int resposta = JOptionPane.showConfirmDialog(janela,
                            "Confirmar cadastro desta música?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(jTextId.getText());
                        String titulo = jTextTitulo.getText().trim();
                        String duracao = jTextDuracao.getText().trim();
                        String album = jTextAlbum.getText().trim();
                        String artista = jTextArtista.getText().trim();

                        if (musica.cadastrarMusica(id, titulo, duracao, album, artista)) {
                            JOptionPane.showMessageDialog(janela, "Música cadastrada com sucesso!");
                            limparTudo(jTextId, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista,
                                    botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                        } else {
                            JOptionPane.showMessageDialog(janela, "Erro ao cadastrar música.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(janela, "Preencha todos os campos corretamente.");
                }
            }
        });

        // ATUALIZAR
        botaoAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validarCampos(jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista)) {
                    int resposta = JOptionPane.showConfirmDialog(janela,
                            "Confirmar atualização desta música?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(jTextId.getText());
                        String titulo = jTextTitulo.getText().trim();
                        String duracao = jTextDuracao.getText().trim();
                        String album = jTextAlbum.getText().trim();
                        String artista = jTextArtista.getText().trim();

                        if (musica.atualizarMusica(id, titulo, duracao, album, artista)) {
                            JOptionPane.showMessageDialog(janela, "Música atualizada com sucesso!");
                            limparTudo(jTextId, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista,
                                    botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                        } else {
                            JOptionPane.showMessageDialog(janela, "Erro ao atualizar música.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(janela, "Preencha todos os campos corretamente.");
                }
            }
        });

        // EXCLUIR
        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela,
                        "Confirmar exclusão desta música?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(jTextId.getText());
                    if (musica.excluirMusica(id)) {
                        JOptionPane.showMessageDialog(janela, "Música excluída com sucesso!");
                        limparTudo(jTextId, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista,
                                botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Erro ao excluir música.");
                    }
                }
            }
        });

        // LIMPAR
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparTudo(jTextId, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista,
                        botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
            }
        });

        return janela;
    }

    // Métodos auxiliares
    private static void limparCampos(JTextField titulo, JTextField duracao,
                                     JTextField album, JTextField artista) {
        titulo.setText("");
        duracao.setText("");
        album.setText("");
        artista.setText("");
    }

    private static void habilitarCampos(boolean habilitar, JTextField titulo, JTextField duracao,
                                        JTextField album, JTextField artista) {
        titulo.setEnabled(habilitar);
        duracao.setEnabled(habilitar);
        album.setEnabled(habilitar);
        artista.setEnabled(habilitar);
    }

    private static void limparTudo(JTextField id, JTextField titulo, JTextField duracao,
                                   JTextField album, JTextField artista,
                                   JButton consultar, JButton cadastrar, JButton atualizar, JButton excluir) {
        id.setText("");
        titulo.setText("");
        duracao.setText("");
        album.setText("");
        artista.setText("");

        id.setEnabled(true);
        habilitarCampos(false, titulo, duracao, album, artista);

        consultar.setEnabled(true);
        cadastrar.setEnabled(false);
        atualizar.setEnabled(false);
        excluir.setEnabled(false);

        id.requestFocus();
    }

    private static boolean validarCampos(JTextField titulo, JTextField duracao,
                                         JTextField album, JTextField artista) {
        return !titulo.getText().trim().isEmpty() &&
               !duracao.getText().trim().isEmpty() &&
               !album.getText().trim().isEmpty() &&
               !artista.getText().trim().isEmpty();
    }
}
