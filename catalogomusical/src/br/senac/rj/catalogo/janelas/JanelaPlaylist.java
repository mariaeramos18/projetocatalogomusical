package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.modelo.Playlist;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JanelaPlaylist {
    private static JTextField txtId;
    private static JTextField txtNome;
    private static JTextField txtDescricao;
    private static JTextField txtData;
    private static JTextField txtEstilo;

    private static JButton botaoConsultar;
    private static JButton botaoGravar;
    private static JButton botaoAtualizar;
    private static JButton botaoExcluir;
    private static JButton botaoLimpar;

    public static JFrame criarJanelaPlaylist() {
        JFrame janela = new JFrame("Cadastro de Playlist");
        janela.setSize(520, 330);
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janela.setResizable(false);
        janela.setLayout(null);

        JLabel labelId        = new JLabel("ID:");
        JLabel labelNome      = new JLabel("Nome:");
        JLabel labelDescricao = new JLabel("Descrição:");
        JLabel labelData      = new JLabel("Data de Criação:");
        JLabel labelEstilo    = new JLabel("Estilo:");

        labelId.setBounds(30, 30, 100, 20);
        labelNome.setBounds(30, 60, 100, 20);
        labelDescricao.setBounds(30, 90, 100, 20);
        labelData.setBounds(30, 120, 120, 20);
        labelEstilo.setBounds(30, 150, 100, 20);

        txtId        = new JTextField();
        txtNome      = new JTextField();
        txtDescricao = new JTextField();
        txtData      = new JTextField();
        txtEstilo    = new JTextField();

        txtId.setBounds(160, 30, 50, 20);
        txtNome.setBounds(160, 60, 200, 20);
        txtDescricao.setBounds(160, 90, 200, 20);
        txtData.setBounds(160, 120, 100, 20);
        txtEstilo.setBounds(160, 150, 150, 20);

        botaoConsultar = new JButton("Consultar");
        botaoGravar    = new JButton("Cadastrar");
        botaoAtualizar = new JButton("Atualizar");
        botaoExcluir   = new JButton("Excluir");
        botaoLimpar    = new JButton("Limpar");

        botaoConsultar.setBounds(320, 30, 120, 25);
        botaoGravar.setBounds(20, 220, 100, 30);
        botaoAtualizar.setBounds(130, 220, 100, 30);
        botaoExcluir.setBounds(240, 220, 100, 30);
        botaoLimpar.setBounds(350, 220, 100, 30);

        botaoGravar.setEnabled(false);
        botaoAtualizar.setEnabled(false);
        botaoExcluir.setEnabled(false);

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

        botaoConsultar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (playlist.consultarPlaylist(id)) {
                    setCampos(
                        playlist.getId(),
                        playlist.getNome(),
                        playlist.getDescricao(),
                        playlist.getDataCriacao(),
                        playlist.getEstilo()
                    );
                    JOptionPane.showMessageDialog(janela, "Playlist encontrada. Você pode atualizar ou excluir.");
                } else {
                    JOptionPane.showMessageDialog(janela, "Playlist não encontrada. Preencha para cadastrar.");
                    resetarFormParaCadastro();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(janela, "Informe um ID numérico válido.");
            }
        });

        botaoGravar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (txtNome.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(janela, "Preencha o nome da playlist.");
                    return;
                }
                if (playlist.cadastrarPlaylist(
                    id,
                    txtNome.getText(),
                    txtDescricao.getText(),
                    txtData.getText(),
                    txtEstilo.getText()
                )) {
                    JOptionPane.showMessageDialog(janela, "Playlist cadastrada com sucesso!");
                    botaoAtualizar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                    botaoGravar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(janela, "Erro ao cadastrar a playlist.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(janela, "ID inválido. Digite um número inteiro.");
            }
        });

        botaoAtualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (JOptionPane.showConfirmDialog(janela,
                    "Confirmar atualização da playlist?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (playlist.atualizarPlaylist(
                        id,
                        txtNome.getText(),
                        txtDescricao.getText(),
                        txtData.getText(),
                        txtEstilo.getText()
                    )) {
                        JOptionPane.showMessageDialog(janela, "Playlist atualizada com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(janela, "Erro ao atualizar a playlist.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(janela, "ID inválido.");
            }
        });

        botaoExcluir.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(janela,
                "Deseja realmente excluir esta playlist?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtId.getText());
                if (playlist.excluirPlaylist(id)) {
                    JOptionPane.showMessageDialog(janela, "Playlist excluída com sucesso.");
                    resetarForm();
                } else {
                    JOptionPane.showMessageDialog(janela, "Erro ao excluir a playlist.");
                }
            }
        });

        botaoLimpar.addActionListener(e -> resetarForm());

        return janela;
    }

    public static void setCampos(int id, String nome, String descricao, String data, String estilo) {
        txtId.setText(String.valueOf(id));
        txtNome.setText(nome);
        txtDescricao.setText(descricao);
        txtData.setText(data);
        txtEstilo.setText(estilo);

        txtId.setEnabled(false);
        habilitarEdicao(true);

        botaoConsultar.setEnabled(false);
        botaoGravar.setEnabled(false);
        botaoAtualizar.setEnabled(true);
        botaoExcluir.setEnabled(true);
    }

    private static void resetarFormParaCadastro() {
        habilitarEdicao(true);
        txtId.setEnabled(true);
        botaoConsultar.setEnabled(false);
        botaoGravar.setEnabled(true);
        botaoAtualizar.setEnabled(false);
        botaoExcluir.setEnabled(false);
    }

    private static void resetarForm() {
        txtId.setText("");
        txtNome.setText("");
        txtDescricao.setText("");
        txtData.setText("");
        txtEstilo.setText("");

        txtId.setEnabled(true);
        habilitarEdicao(false);

        botaoConsultar.setEnabled(true);
        botaoGravar.setEnabled(false);
        botaoAtualizar.setEnabled(false);
        botaoExcluir.setEnabled(false);
    }

    private static void habilitarEdicao(boolean habilitar) {
        txtNome.setEnabled(habilitar);
        txtDescricao.setEnabled(habilitar);
        txtData.setEnabled(habilitar);
        txtEstilo.setEnabled(habilitar);
    }

    private static void limparCampos(JTextField... campos) {
        for (JTextField campo : campos) campo.setText("");
    }
}
