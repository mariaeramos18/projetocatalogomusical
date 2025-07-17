package br.senac.rj.catalogo.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe usada para abrir e fechar conexões com um banco de dados MySQL
 */
public class Conexao {	

    public static Connection conectaBanco() {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Registra o driver JDBC do MySQL
            String url = "jdbc:mysql://localhost/catalogo_musical";
            String user = "root";
            String password = "";
            conexao = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException erro) {
            System.out.println("Driver não encontrado: " + erro);
        } catch (SQLException erro) {
            System.out.println("Erro de conexão ao banco de dados: " + erro.toString());
        } catch (Exception erro) {
            System.out.println("Erro não identificado: " + erro.toString());
        }
        return conexao;
    }
	
    /**
     * Encerra a conexão com o banco de dados após seu uso
     * @param conexao conexão a ser fechada
     */
    public static void fechaConexao(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) { // Verifica se conexão está aberta
                conexao.close();
            }
        } catch (Exception erro) {
            System.out.println("Erro ao fechar a conexão: " + erro.toString());
        }
    }
}
