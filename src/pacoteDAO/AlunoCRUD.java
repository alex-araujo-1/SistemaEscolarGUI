/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pacoteDAO;

import alunos.Aluno;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author pc
 */
public class AlunoCRUD {

    PreparedStatement st;

    private Conexao conexao;
    private Connection conn;

    public AlunoCRUD() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_escolar", "root", "desenvolvedor");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }

    public int cadastrar(Aluno alunoSalvar) {
        int status;

        try {

            st = conn.prepareStatement("INSERT INTO aluno VALUES(?,?,?,?,?,?,?)");

            st.setInt(1, alunoSalvar.getId());
            st.setString(2, alunoSalvar.getNomeAluno());
            st.setString(3, alunoSalvar.getCpf());
            st.setString(4, alunoSalvar.getEmail());
            st.setInt(5, (alunoSalvar.getTelefone()));
            st.setString(6, alunoSalvar.getEndereco());
            st.setString(7, alunoSalvar.getNascimento());
            status = st.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Erro de conexão: " + ex.getMessage());
            return ex.getErrorCode();
        }

    }

    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {

        }
    }

    public void excluir(int id) {

        String sql = "DELETE FROM aluno WHERE id = ?";
        try {

            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
        }

    }

    public List<Aluno> getAluno(String nomeAluno) {
        String sql = "SELECT * FROM aluno WHERE nomeAluno LIKE ?";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            stmt.setString(1, "%" + nomeAluno + "%");

            ResultSet rs = stmt.executeQuery();

            List<Aluno> listaAlunos = new ArrayList<>();

            while (rs.next()) {
                Aluno aluno = new Aluno();

                aluno.setId(rs.getInt("id"));
                aluno.setNomeAluno(rs.getString("nomeAluno"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getInt("telefone"));
                aluno.setEndereco(rs.getString("endereco"));
                aluno.setNascimento(rs.getString("nascimento"));

                listaAlunos.add(aluno);

            }

            return listaAlunos;

        } catch (SQLException e) {
            return null;
        }

    }

    public void atualizar(Aluno aluno) {

        String sql = "UPDATE aluno SET nomeAluno=?, cpf=?, email=?, telefone=?, endereco=?, nascimento=?  WHERE id=?";
        try {

            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            stmt.setString(1, aluno.getNomeAluno());
            stmt.setString(2, aluno.getCpf());
            stmt.setString(3, aluno.getEmail());
            stmt.setInt(4, aluno.getTelefone());
            stmt.setString(5, aluno.getEndereco());
            stmt.setString(6, aluno.getNascimento());
            stmt.setInt(7, aluno.getId());

            stmt.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar aluno, verifique os dados!");
            System.out.println("Erro ao editar aluno: " + e.getMessage());
        }
    }

    public Aluno getAlunoNome(String nomealuno) {
        String sql = "SELECT * FROM aluno WHERE nomeAluno LIKE ?";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);

            stmt.setString(1, nomealuno);

            ResultSet rs = stmt.executeQuery();

            Aluno aluno = new Aluno();
            rs.next();

            aluno.setId(rs.getInt("id"));
            aluno.setNomeAluno(nomealuno);
            aluno.setNomeAluno(rs.getString("nomeAluno"));
            aluno.setCpf(rs.getString("cpf"));
            aluno.setEmail(rs.getString("email"));
            aluno.setTelefone(rs.getInt("telefone"));
            aluno.setEndereco(rs.getString("endereco"));
            aluno.setNascimento(rs.getString("nascimento"));

            return aluno;

        } catch (SQLException e) {
            System.out.println("erro: " + e.getMessage());
            return null;
        }
    }

}
