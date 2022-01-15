package Tema2.Ejercicios;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Ejercicio18 {

    private static String datos_conexion = "jdbc:mysql://localhost:3306/concesionario";
    private static String usuario = "root";
    private static String password = "";

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(datos_conexion, usuario, password);
            DatabaseMetaData dbmd = con.getMetaData();
            boolean admiteProcesamientoLotes = dbmd.supportsBatchUpdates();

            System.out.println("La BD " + (admiteProcesamientoLotes?"sí":"no") + " admite procesamiento por lotes.");
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
