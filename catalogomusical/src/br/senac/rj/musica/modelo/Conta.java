package br.senac.rj.musica.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Conta {
	private int agencia;
	private int numero;
	private String titular;
	protected double saldo;
	public static int totalContas;

	Conta() {
		this.titular = "";
		Conta.totalContas++;
	}

	Conta(int numAgencia, int numConta) {
		this();
		this.agencia = numAgencia;
		this.numero = numConta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void deposita(double valor) {
		this.saldo = this.saldo + valor;
	}

	public abstract boolean saca(double valor);

	public boolean cadastrarConta(int numAgencia, int numConta, String titular) {
		// Define a conexao
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "insert into conta set agencia=?, numero=?, titular=?, saldo=0;";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parametros da consulta
			ps.setInt(1, numAgencia); // Substitui o primeiro parametro da consulta pela agencia informada
			ps.setInt(2, numConta); // Substitui o segundo parametro da consulta pela conta informada
			ps.setString(3, titular); // Substitui o terceiro parametro da consulta pelo titular informado
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Nao foi feito o cadastro!!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar a conta: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	public boolean consultarConta(int numAgencia, int numConta) {
		// Define a conexao
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "select * from conta where agencia=? and numero=?";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parametros da consulta
			ps.setInt(1, numAgencia); // Substitui o primeiro parametro da consulta pela agencia informada
			ps.setInt(2, numConta); // Substitui o segundo parametro da consulta pela conta informada
			// Executa a consulta, resultando em um objeto da classe ResultSet
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // Verifica senao esta antes do primeiro registro
				System.out.println("Contanao cadastrada!");
				return false; // Contanao cadastrada
			} else {
				// Efetua a leitura do registro da tabela
				while (rs.next()) {
					this.agencia = rs.getInt("agencia");
					this.numero = rs.getInt("numero");
					this.titular = rs.getString("titular");
					this.saldo = rs.getDouble("saldo");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar a conta: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	public boolean atualizarConta(int numAgencia, int numConta, String titular) {
		if (!consultarConta(numAgencia, numConta))
			return false;
		else {
			// Define a conexao
			Connection conexao = null;
			try {
				// Define a conexao
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "update conta set titular=?, saldo=? where agencia=? and numero=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parametros da atualização
				ps.setString(1, titular);
				ps.setDouble(2, saldo);
				ps.setInt(3, numAgencia);
				ps.setInt(4, numConta);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("N�o foi feita a atualização!");
				else
					System.out.println("atualização realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar a conta: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
}
