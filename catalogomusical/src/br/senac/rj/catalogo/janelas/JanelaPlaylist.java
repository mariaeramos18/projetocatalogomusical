package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.modelo.Playlist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPlaylist {

    public static JFrame criarJanelaPlaylist() {
        JFrame janela = new JFrame("Cadastro de Playlist");
        janela.setSize(450, 350);
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
        JButton botaoGravar = new JButton("Gravar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoLimpar = new JButton("Limpar");

        botaoConsultar.setBounds(250, 30, 100, 20);
        botaoGravar.setBounds(30, 200, 100, 25);
        botaoExcluir.setBounds(140, 200, 100, 25);
        botaoLimpar.setBounds(250, 200, 100, 25);

        botaoGravar.setEnabled(false);
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
        janela.add(botaoExcluir);
        janela.add(botaoLimpar);

        Playlist playlist = new Playlist();

        // Ação do botão CONSULTAR
        botaoConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    if (playlist.consultarPlaylist(id)) {
                        txtNome.setText(playlist.getNome());
                        txtDescricao.setText(playlist.getDescricao());
                        txtData.setText(playlist.getDataCriacao());
                        txtEstilo.setText(playlist.getEstilo());

                        botaoGravar.setEnabled(true);
                        botaoExcluir.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Playlist não encontrada. Será incluída nova.");
                        botaoGravar.setEnabled(true);
                        botaoExcluir.setEnabled(false);
                    }

                    txtId.setEnabled(false);
                    txtNome.setEnabled(true);
                    txtDescricao.setEnabled(true);
                    txtData.setEnabled(true);
                    txtEstilo.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(janela, "Informe um ID válido.");
                }
            }
        });

        // Ação do botão GRAVAR
        botaoGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela, "Deseja salvar os dados?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    try {
                        if (txtId.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(janela, "O ID é obrigatório.");
                            return;
                        }
                        
                        int id = Integer.parseInt(txtId.getText());
                        String nome = txtNome.getText();
                        String descricao = txtDescricao.getText();
                        String data = txtData.getText();
                        String estilo = txtEstilo.getText();

                        if (nome.isEmpty()) {
                            JOptionPane.showMessageDialog(janela, "Preencha o nome da playlist.");
                            return;
                        }

                        boolean existe = playlist.consultarPlaylist(id);
                        boolean resultado;
                        
                        if (existe) {
                            resultado = playlist.atualizarPlaylist(id, nome, descricao, data, estilo);
                            JOptionPane.showMessageDialog(janela, 
                                resultado ? "Playlist atualizada com sucesso!" : "Erro ao atualizar.");
                        } else {
                            resultado = playlist.cadastrarPlaylist(id, nome, descricao, data, estilo);
                            JOptionPane.showMessageDialog(janela, 
                                resultado ? "Playlist cadastrada com sucesso!" : "Erro ao cadastrar.");
                        }
                        
                        if (resultado) {
                            txtId.setEnabled(false);
                        }
                        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(janela, "ID inválido. Digite um número inteiro.");
                    }
                }
            }
        });

        // Ação do botão EXCLUIR
        botaoExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int resposta = JOptionPane.showConfirmDialog(janela, "Deseja excluir a playlist?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    int id = Integer.parseInt(txtId.getText());
                    if (playlist.excluirPlaylist(id)) {
                        JOptionPane.showMessageDialog(janela, "Playlist excluída com sucesso.");
                        botaoGravar.setEnabled(false);
                        botaoExcluir.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Erro ao excluir playlist.");
                    }
                }
            }
        });

        // Ação do botão LIMPAR
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtId.setText("");
                txtNome.setText("");
                txtDescricao.setText("");
                txtData.setText("");
                txtEstilo.setText("");
                txtId.setEnabled(true);
                botaoGravar.setEnabled(false);
                botaoExcluir.setEnabled(false);
                txtId.requestFocus();
            }
        });

        return janela;
    }
}
