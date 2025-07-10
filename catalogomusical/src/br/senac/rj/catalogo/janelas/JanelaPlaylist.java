 package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.modelo.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPlaylist {

    public static JFrame criarJanelaPlaylist() {
        JFrame janela = new JFrame("Cadastro de Playlist");
        janela.setSize(520, 330);
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janela.setResizable(false);
        Container caixa = janela.getContentPane();
        caixa.setLayout(null);

        // Labels
        JLabel labelId = new JLabel("ID:");
        JLabel labelNome = new JLabel("Nome:");
        JLabel labelDescricao = new JLabel("Descrição:");
        JLabel labelData = new JLabel("Data de Criação:");
        JLabel labelEstilo = new JLabel("Estilo:");

        labelId.setBounds(30, 30, 100, 20);
        labelNome.setBounds(30, 60, 100, 20);
        labelDescricao.setBounds(30, 90, 100, 20);
        labelData.setBounds(30, 120, 120, 20);
        labelEstilo.setBounds(30, 150, 100, 20);

        // Campos de texto
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtData = new JTextField();
        JTextField txtEstilo = new JTextField();

        txtId.setBounds(160, 30, 50, 20);
        txtNome.setBounds(160, 60, 200, 20);
        txtDescricao.setBounds(160, 90, 200, 20);
        txtData.setBounds(160, 120, 100, 20);
        txtEstilo.setBounds(160, 150, 150, 20);

        txtId.setEnabled(true);

        // Botões
        JButton botaoConsultar = new JButton("Consultar");
        JButton botaoGravar = new JButton("Cadastrar");
        JButton botaoAtualizar = new JButton("Atualizar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoLimpar = new JButton("Limpar");

        botaoConsultar.setBounds(320, 30, 120, 25);
        botaoGravar.setBounds(20, 220, 100, 30);
        botaoAtualizar.setBounds(130, 220, 100, 30);
        botaoExcluir.setBounds(240, 220, 100, 30);
        botaoLimpar.setBounds(350, 220, 100, 30);

        botaoGravar.setEnabled(false);
        botaoAtualizar.setEnabled(false);
        botaoExcluir.setEnabled(false);

        // Adiciona tudo na janela
        janela.add(labelId);
        janela.add(labelNome);
        janela.add(labelDescricao);
        janela.add(labelData);
        janela.add(labelEstilo);
        janela.add(txtId);
        janela.add(txtNome);
        janela.add(txtDescricao);
        janela.add(txtData);
        janela.add(txtEstilo);
        janela.add(botaoConsultar);
        janela.add(botaoGravar);
        janela.add(botaoAtualizar);
        janela.add(botaoExcluir);
        janela.add(botaoLimpar);

        Playlist playlist = new Playlist();

        // CONSULTAR
        botaoConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    if (playlist.consultarPlaylist(id)) {
                        txtNome.setText(playlist.getNome());
                        txtDescricao.setText(playlist.getDescricao());
                        txtData.setText(playlist.getDataCriacao());
                        txtEstilo.setText(playlist.getEstilo());

                        botaoAtualizar.setEnabled(true);
                        botaoExcluir.setEnabled(true);
                        botaoGravar.setEnabled(false);
                        txtId.setEnabled(false);
                        JOptionPane.showMessageDialog(janela, "Playlist encontrada. Você pode atualizar ou excluir.");
                    } else {
                        JOptionPane.showMessageDialog(janela, "Playlist não encontrada. Preencha os dados para cadastrar.");
                        botaoGravar.setEnabled(true);
                        botaoAtualizar.setEnabled(false);
                        botaoExcluir.setEnabled(false);
                    }

                    txtNome.setEnabled(true);
                    txtDescricao.setEnabled(true);
                    txtData.setEnabled(true);
                    txtEstilo.setEnabled(true);
                    txtNome.requestFocus();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "Informe um ID numérico válido.");
                }
            }
        });

        // CADASTRAR
        botaoGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nome = txtNome.getText().trim();
                    String descricao = txtDescricao.getText().trim();
                    String data = txtData.getText().trim();
                    String estilo = txtEstilo.getText().trim();

                    if (nome.isEmpty()) {
                        JOptionPane.showMessageDialog(janela, "Preencha o nome da playlist.");
                        return;
                    }

                    boolean resultado = playlist.cadastrarPlaylist(id, nome, descricao, data, estilo);

                    if (resultado) {
                        JOptionPane.showMessageDialog(janela, "Playlist cadastrada com sucesso!");
                        txtId.setEnabled(false);
                        botaoGravar.setEnabled(false);
                        botaoAtualizar.setEnabled(true);
                        botaoExcluir.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Erro ao cadastrar a playlist.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "ID inválido. Digite um número inteiro.");
                }
            }
        });

        // ATUALIZAR
        botaoAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    String nome = txtNome.getText().trim();
                    String descricao = txtDescricao.getText().trim();
                    String data = txtData.getText().trim();
                    String estilo = txtEstilo.getText().trim();

                    int resposta = JOptionPane.showConfirmDialog(janela,
                            "Confirmar atualização da playlist?", "Confirmação", JOptionPane.YES_NO_OPTION);

                    if (resposta == JOptionPane.YES_OPTION) {
                        boolean resultado = playlist.atualizarPlaylist(id, nome, descricao, data, estilo);
                        if (resultado) {
                            JOptionPane.showMessageDialog(janela, "Playlist atualizada com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(janela, "Erro ao atualizar a playlist.");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "ID inválido.");
                }
            }
        });

        // EXCLUIR
        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela,
                        "Deseja realmente excluir esta playlist?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(txtId.getText());
                    if (playlist.excluirPlaylist(id)) {
                        JOptionPane.showMessageDialog(janela, "Playlist excluída com sucesso.");
                        limparCampos(txtId, txtNome, txtDescricao, txtData, txtEstilo,
                                botaoConsultar, botaoGravar, botaoAtualizar, botaoExcluir);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Erro ao excluir a playlist.");
                    }
                }
            }
        });

        // LIMPAR
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos(txtId, txtNome, txtDescricao, txtData, txtEstilo,
                        botaoConsultar, botaoGravar, botaoAtualizar, botaoExcluir);
            }
        });

        return janela;
    }

    // Método auxiliar para limpar
    private static void limparCampos(JTextField id, JTextField nome, JTextField descricao,
                                     JTextField data, JTextField estilo,
                                     JButton consultar, JButton gravar, JButton atualizar, JButton excluir) {
        id.setText("");
        nome.setText("");
        descricao.setText("");
        data.setText("");
        estilo.setText("");

        id.setEnabled(true);
        nome.setEnabled(false);
        descricao.setEnabled(false);
        data.setEnabled(false);
        estilo.setEnabled(false);

        consultar.setEnabled(true);
        gravar.setEnabled(false);
        atualizar.setEnabled(false);
        excluir.setEnabled(false);

        id.requestFocus();
    }
}