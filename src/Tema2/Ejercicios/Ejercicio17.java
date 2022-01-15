package Tema2.Ejercicios;

import java.sql.*;

public class Ejercicio17 {

    private static String datos_conexion = "jdbc:mysql://localhost:3306/concesionario";
    private static String usuario = "root";
    private static String password = "";

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(datos_conexion, usuario, password);
            DatabaseMetaData dbmd = con.getMetaData();

            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url = dbmd.getURL();
            String usuario = dbmd.getUserName();

            System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:");
            System.out.println("===================================");
            System.out.printf("Nombre : %s %n", nombre);
            System.out.printf("Driver : %s %n", driver);
            System.out.printf("URL    : %s %n", url);
            System.out.printf("Usuario: %s %n", usuario);
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
