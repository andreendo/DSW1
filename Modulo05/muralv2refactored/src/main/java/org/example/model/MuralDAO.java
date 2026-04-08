package org.example.model;

import io.vavr.control.Try;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MuralDAO {

    private Connection connection;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public MuralDAO(Connection conn) {
        this.connection = conn;
    }

    public List<Mensagem> getMensagens() {
        var mensagens = new ArrayList<Mensagem>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM mensagem ORDER BY id DESC");
        ) {
            while(rs.next()) {
                var id = rs.getInt("id");
                var de = rs.getString("de");
                var para = rs.getString("para");
                var texto = rs.getString("texto");
                var dataEnvio = rs.getString("dataEnvio");
                mensagens.add(new Mensagem(id, de, para, texto, Try.of(() -> sdf.parse(dataEnvio)).getOrElse(new Date())));
            }
        } catch (SQLException e) {

        }

        return mensagens;
    }

    public void addMensagem(String de, String para, String texto) {
        String sql = "INSERT INTO mensagem (de, para, texto, dataEnvio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, de);
            ps.setString(2, para);
            ps.setString(3, texto);
            ps.setString(4, sdf.format(new Date()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void excluirMensagem(Integer id) throws SQLException {
        String sql = "DELETE FROM mensagem WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("ID inexistente");
        }
    }

    public Mensagem getMensagem(int id) throws SQLException {
        String sql = "SELECT * FROM mensagem WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                var de = rs.getString("de");
                var para = rs.getString("para");
                var texto = rs.getString("texto");
                var dataEnvio = rs.getString("dataEnvio");
                return new Mensagem(id, de, para, texto, Try.of(() -> sdf.parse(dataEnvio)).getOrElse(new Date()));
            } else {
                throw new SQLException("ID inexistente");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void atualizar(Mensagem mensagem) throws SQLException {
        String sql = "UPDATE mensagem SET de = ?, para = ?, texto = ?, dataEnvio = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, mensagem.enviadoPor());
        ps.setString(2, mensagem.enviadoPara());
        ps.setString(3, mensagem.texto());
        ps.setString(4, sdf.format(new Date()));
        ps.setInt(5, mensagem.iid());
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Erro na atualização");
        }
    }
}
