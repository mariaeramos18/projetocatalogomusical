package br.senac.rj.catalogo.janelas;

import br.senac.rj.catalogo.AppCatalogoMusical;
import br.senac.rj.catalogo.modelo.Playlist;
import javax.swing.*;
import java.awt.*;

public class JanelaPlaylist extends JFrame {
    // Componentes da UI
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtData;
    private JTextField txtEstilo;
    private JButton botaoConsultar;
    private JButton botaoGravar;
    private JButton botaoAtualizar;
    private JButton botaoExcluir;

    public JanelaPlaylist() {
        setTitle("Playlist management");
        setSize(520, 330);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        Container caixa = getContentPane();
        caixa.setLayout(null);

        // Labels e Campos de Texto
        add(new JLabel("ID:")).setBounds(30, 30, 100, 20);
        txtId = new JTextField();
        txtId.setBounds(160, 30, 50, 20);
        add(txtId);

        add(new JLabel("Name:")).setBounds(30, 60, 100, 20);
        txtNome = new JTextField();
        txtNome.setBounds(160, 60, 200, 20);
        add(txtNome);

        add(new JLabel("Description:")).setBounds(30, 90, 100, 20);
        txtDescricao = new JTextField();
        txtDescricao.setBounds(160, 90, 200, 20);
        add(txtDescricao);

        add(new JLabel("Creation date:")).setBounds(30, 120, 120, 20);
        txtData = new JTextField();
        txtData.setBounds(160, 120, 100, 20);
        add(txtData);

        add(new JLabel("Genre:")).setBounds(30, 150, 100, 20);
        txtEstilo = new JTextField();
        txtEstilo.setBounds(160, 150, 150, 20);
        add(txtEstilo);

        // Botões
        botaoConsultar = new JButton("Consult");
        botaoGravar = new JButton("Register");
        botaoAtualizar = new JButton("Refresh");
        botaoExcluir = new JButton("Delete");
        JButton botaoLimpar = new JButton("Clean");

        botaoConsultar.setBounds(320, 30, 120, 25);
        botaoGravar.setBounds(20, 220, 100, 30);
        botaoAtualizar.setBounds(130, 220, 100, 30);
        botaoExcluir.setBounds(240, 220, 100, 30);
        botaoLimpar.setBounds(350, 220, 100, 30);
        
        add(botaoConsultar);
        add(botaoGravar);
        add(botaoAtualizar);
        add(botaoExcluir);
        add(botaoLimpar);
        
        Playlist playlist = new Playlist();
        limparTudo(); // Define estado inicial

        // Action Listeners
        botaoConsultar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (playlist.consultarPlaylist(id)) {
                    txtNome.setText(playlist.getNome());
                    txtDescricao.setText(playlist.getDescricao());
                    txtData.setText(playlist.getDataCriacao());
                    txtEstilo.setText(playlist.getEstilo());
                    configurarBotoes(false, true, true, true);
                    habilitarCampos(true);
                    txtId.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Playlist not found. Fill in the fields to register.");
                    configurarBotoes(true, false, false, true);
                    habilitarCampos(true);
                }
                botaoConsultar.setEnabled(false);
                txtNome.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID. Enter an integer.");
            }
        });

        botaoGravar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                if (playlist.cadastrarPlaylist(id, txtNome.getText(), txtDescricao.getText(), txtData.getText(), txtEstilo.getText())) {
                    JOptionPane.showMessageDialog(this, "Playlist successfully registered!");
                    
                    AppCatalogoMusical.atualizarTabelaPlaylist();
                    
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register song. Check that the ID already exists.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID. Enter an integer.");
            }
        });

        botaoAtualizar.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(this, "Confirm this playlist's registration?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtId.getText());
                if (playlist.atualizarPlaylist(id, txtNome.getText(), txtDescricao.getText(), txtData.getText(), txtEstilo.getText())) {
                    JOptionPane.showMessageDialog(this, "Playlist successfully registered!");
                    
                    AppCatalogoMusical.atualizarTabelaPlaylist();
                    
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update playlist.");
                }
            }
        });

        botaoExcluir.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(this, "Can you confirm this playlist's exclusion?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(txtId.getText());
                if (playlist.excluirPlaylist(id)) {
                    JOptionPane.showMessageDialog(this, "Playlist successfully deleted!");
                    
                    AppCatalogoMusical.atualizarTabelaPlaylist();
                    
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete the playlist.");
                }
            }
        });

        botaoLimpar.addActionListener(e -> limparTudo());
    }

    public void preencherEabrir(String id, String nome, String descricao, String data, String estilo) {
        limparTudo();
        txtId.setText(id);
        txtNome.setText(nome);
        txtDescricao.setText(descricao);
        txtData.setText(data);
        txtEstilo.setText(estilo);

        txtId.setEnabled(false);
        botaoConsultar.setEnabled(false);
        configurarBotoes(false, true, true, true);
        habilitarCampos(true);
        setVisible(true);
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtData.setText("");
        txtEstilo.setText("");
    }

    private void habilitarCampos(boolean habilitar) {
        txtNome.setEnabled(habilitar);
        txtDescricao.setEnabled(habilitar);
        txtData.setEnabled(habilitar);
        txtEstilo.setEnabled(habilitar);
    }
    
    private void configurarBotoes(boolean gravar, boolean atualizar, boolean excluir, boolean limpar) {
        botaoGravar.setEnabled(gravar);
        botaoAtualizar.setEnabled(atualizar);
        botaoExcluir.setEnabled(excluir);
    }

    public void limparTudo() {
        txtId.setText("");
        limparCampos();
        txtId.setEnabled(true);
        habilitarCampos(false);
        botaoConsultar.setEnabled(true);
        configurarBotoes(false, false, false, true);
        txtId.requestFocus();
    }
}