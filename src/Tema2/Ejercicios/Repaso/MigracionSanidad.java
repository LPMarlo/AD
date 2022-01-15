package Tema2.Ejercicios.Repaso;

import java.sql.*;

public class MigracionSanidad {

    private static final String datos_conexion = "jdbc:mysql://localhost:3306/sanidad";
    private static final String datos_conexion_externa = "jdbc:mysql://localhost:3306/sanidad_externa";
    private static final String usuario = "root";
    private static final String password = "";

    public static void main(String[] args) {
        Connection con = null;
        Connection conExt = null;
        try {
            con = DriverManager.getConnection(datos_conexion, usuario, password);
            conExt = DriverManager.getConnection(datos_conexion_externa, usuario, password);

            try {
                String sql = "SELECT codigoCentro, nombre FROM centro_externo";
                Statement st = conExt.createStatement();
                ResultSet rs = st.executeQuery(sql);

                String insert = "INSERT INTO centro(codigoCentro, descCentro) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(insert);

                int codigoCentro;
                String descCentro;
                while (rs.next()) {
                    codigoCentro = rs.getInt(1);
                    descCentro = rs.getString(2);

                    ps.setInt(1, codigoCentro);
                    ps.setString(2, descCentro);
                    ps.addBatch();
                }
                ps.executeBatch();
                System.out.println("Inserción realizada y transacción consolidada (commit realizado).");
            } catch (SQLException e) {
                System.out.println("Error en la inserción: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error de conexión.");
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
