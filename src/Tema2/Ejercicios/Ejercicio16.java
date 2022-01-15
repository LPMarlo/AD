package Tema2.Ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ejercicio16 {

    private static String datos_conexion = "jdbc:mysql://localhost:3306/concesionario";
    private static String usuario = "root";
    private static String password = "";

    private static String sentInsert =
            "INSERT INTO marca(DESCMARCA) VALUES(?) ";

    public static void main(String[] args) {
        Connection con = null;
        try {
            // Se conecta con la base de datos. En este caso es externa y el usuario nos viene dado. Es un usuario �nicamente con funci�n de ejecuci�n de funciones
            con = DriverManager.getConnection(datos_conexion, usuario, password);

            try {
                PreparedStatement psInsert = con.prepareStatement(sentInsert);
                con.setAutoCommit(false);

                psInsert.setString(1, "Renault");
                psInsert.executeUpdate();
                psInsert.setString(1, "Chevrolet");
                psInsert.executeUpdate();
                psInsert.setString(1, "Fiat");
                psInsert.executeUpdate();
                psInsert.setString(1, "Hyundai");
                psInsert.executeUpdate();

                con.commit();

                System.out.println("Inserci�n realizada y transacci�n consolidada (commit realizado).");
            } catch (SQLException e) {
                System.out.println("Error en la inserci�n: " + e.getMessage());
                try {
                    con.rollback();
                    System.out.println("Rollback realizado");
                }catch(SQLException re) {
                    System.out.println("Error en el rollback: " + re.getMessage());
                    System.out.println("Error realizando Rollback");
                }
            }

        } catch (Exception e) {
            System.err.println("Error de conexi�n.");
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
