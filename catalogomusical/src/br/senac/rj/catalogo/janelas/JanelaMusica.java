package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.modelo.Musica;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaMusica {
    public static JFrame criarJanelaMusica() {
        JFrame janela = new JFrame("Gerenciamento de Musicas:");
        janela.setResizable(false);
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janela.setSize(500, 350);

        Container caixa = janela.getContentPane();
        caixa.setLayout(null);

        // Labels
        JLabel labelId = new JLabel("ID:");
        JLabel labelTitulo = new JLabel("Titulo:");
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

        // Estado inicial dos botões
        botaoCadastrar.setEnabled(false);
        botaoAtualizar.setEnabled(false);
        botaoExcluir.setEnabled(false);

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

        // Objeto musica
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
                        botaoLimpar.setEnabled(true);
                        habilitarCampos(true, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista);
                        jTextId.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Música não encontrado. Preencha os dados para cadastrar.");
                        limparCampos(jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista);
                        botaoCadastrar.setEnabled(true);
                        botaoAtualizar.setEnabled(false);
                        botaoExcluir.setEnabled(false);
                        botaoLimpar.setEnabled(true);
                        habilitarCampos(true, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista);
                    }
                    botaoConsultar.setEnabled(false);
                    jTextTitulo.requestFocus();
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
                            "Confirmar cadastro deste música?", "Confirmação", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(jTextId.getText());
                        String nome = jTextTitulo.getText().trim();
                        String nacionalidade = jTextDuracao.getText().trim();
                        String generoMusical = jTextAlbum.getText().trim();
                        String genero = jTextArtista.getText().trim();

                        if (musica.cadastrarMusica(id, nome, nacionalidade, generoMusical, genero)) {
                            JOptionPane.showMessageDialog(janela, "Música cadastrado com sucesso!");
                            limparTudo(jTextId, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista,
                                    botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                        } else {
                            JOptionPane.showMessageDialog(janela, "Falha ao cadastrar música.");
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
                            "Confirmar atualização deste música?", "Confirmação", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        int id = Integer.parseInt(jTextId.getText());
                        String nome = jTextTitulo.getText().trim();
                        String nacionalidade = jTextDuracao.getText().trim();
                        String generoMusical = jTextAlbum.getText().trim();
                        String genero = jTextArtista.getText().trim();

                        if (musica.atualizarMusica(id, nome, nacionalidade, generoMusical, genero)) {
                            JOptionPane.showMessageDialog(janela, "Música atualizado com sucesso!");
                            limparTudo(jTextId, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista,
                                    botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                        } else {
                            JOptionPane.showMessageDialog(janela, "Falha ao atualizar musica.");
                        }
                    }
                }
            }
        });

        // EXCLUIR
        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela,
                        "Confirmar exclusão deste música?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(jTextId.getText());
                    if (musica.excluirMusica(id)) {
                        JOptionPane.showMessageDialog(janela, "Música excluído com sucesso!");
                        limparTudo(jTextId, jTextTitulo, jTextDuracao, jTextAlbum, jTextArtista,
                                botaoConsultar, botaoCadastrar, botaoAtualizar, botaoExcluir);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Falha ao excluir musica.");
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