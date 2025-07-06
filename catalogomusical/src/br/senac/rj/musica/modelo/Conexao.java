package br.senac.rj.musica.modelo;

import java.sql.Connection; // representa uma conexão ativa com o banco
import java.sql.DriverManager; // gerencia os drivers de banco e estabelece conexões
import java.sql.SQLException;  // trata erros relacionados ao banco de dados

/**
 * @Class Classe usada para abrir e fechar onexões com um banco de dados MySQL
 */

public class Conexao {	
	public static Connection conectaBanco() { //estabelece uma conexão com o banco
		Connection conexao = null; //inicializa a variavel conexão como null
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //Carrega o driver JDBC do MySQL, necessário p/ registra o driver JDBC com DriverManager
			// 3 próximas linhas: 
			String url = "jdbc:mysql://localhost/banco"; // URL do banco de dados // /banco é o nome do banco de dados
			String user = "root"; // nome do usuário do banco, usuário padrão do MySQL
			String password = ""; // senha do banco
			conexao = DriverManager.getConnection(url, user, password); // tenta estabelecer a conexão
			//Próximas linhas de catch:  tratemento de erros
		} catch (ClassNotFoundException erro) { //se o driver não for encontrado
			System.out.println("Driver não encontrado: " + erro);
		} catch (SQLException erro) { // houver erro de login, senha ou URL
			System.out.println("Erro de conexão ao banco de dados: " + erro.toString());
		} catch (Exception erro) { // qualquer outro erro inesperado
			System.out.println("Erro não identificado: " + erro.toString());
		} 
		return conexao; //retorna a conexão (ou null se der erro)
	}
	
	/**
	 * @method fechaConexao() encerra a conexão com o banco de dados após seu uso
	 * @param conexao
	 */
	public static void fechaConexao(Connection conexao) {
		//public: o método pode ser chamado de qualquer lugar
		//static: não precisa criar um objeto da classe Conexao para usar esse método
		//void: não retorna nada
		// recebe uma conexão como parametro (a que foi aberta antes com conectaBanco())
		try {
			conexao.close();
		} catch (Exception erro) {
			System.out.println("Erro ao fechar a conexão: " + erro.toString());
		}
	}
}