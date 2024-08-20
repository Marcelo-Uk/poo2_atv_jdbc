package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {

	Connection con;
	String sql;
	PreparedStatement pst;

	public void salvar(Aluno a) throws SQLException {
	    try {
	        con = db.getConexao();
	        sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?, ?, ?)";
	        pst = con.prepareStatement(sql);
	        pst.setString(1, a.getNome());
	        pst.setString(2, a.getSexo());
	        Date dataSql = Date.valueOf(a.getDt_nasc());
	        pst.setDate(3, dataSql);
	        pst.executeUpdate();
	        System.out.println("\nCadastro do aluno realizado com sucesso!");

	    } catch (Exception e) {
	        System.out.println("Erro: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        if (pst != null) {
	            pst.close();
	        }
	        if (con != null) {
	            db.closeConexao();
	        }
	    }
	}

	public List<Aluno> listar() {
		List<Aluno> alunos = new ArrayList<>();
		ResultSet rs = null;
		try {
			con = db.getConexao();
			sql = "SELECT * FROM aluno";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			while (rs.next()) {
				Aluno aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setSexo(rs.getString("sexo"));
				aluno.setDt_nasc(rs.getDate("dt_nasc").toLocalDate());
				alunos.add(aluno);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					db.closeConexao();
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
		return alunos;
	}

	public void apagar(int id) {
		try {
			con = db.getConexao();
			sql = "DELETE FROM aluno WHERE id = ?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Aluno com id " + id + " foi deletado com sucesso.");
			} else {
				System.out.println("Nenhum aluno encontrado com id " + id + ".");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					db.closeConexao();
				}
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	public void alterar(Aluno a) {
	    try {
	        con = db.getConexao();
	        sql = "UPDATE aluno SET nome = ?, sexo = ?, dt_nasc = ? WHERE id = ?";
	        pst = con.prepareStatement(sql);
	        pst.setString(1, a.getNome());
	        pst.setString(2, a.getSexo());
	        Date dataSql = Date.valueOf(a.getDt_nasc());
	        pst.setDate(3, dataSql);
	        pst.setInt(4, a.getId());

	        int rowsAffected = pst.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Dados do aluno com id " + a.getId() + " foram atualizados com sucesso.");
	        } else {
	            System.out.println("Nenhum aluno encontrado com id " + a.getId() + ".");
	        }
	    } catch (Exception e) {
	        System.out.println("Erro ao alterar aluno: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pst != null && !pst.isClosed()) {
	                pst.close();
	            }
	            if (con != null && !con.isClosed()) {
	                db.closeConexao();
	            }
	        } catch (SQLException e) {
	            System.out.println("Erro ao fechar recursos: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	}

}
