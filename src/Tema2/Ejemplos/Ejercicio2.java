package Tema2.Ejemplos;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class Ejercicio2 {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Conexión
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PruebaConexionBD", "root", "");
            Statement stQ = con.createStatement();
            Statement stD = con.createStatement();

            // Solicitud de datos
            System.out.println("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            //Consulta
            String sql = "SELECT id FROM alumnos WHERE id=" + id + ";";
            ResultSet rs = stQ.executeQuery(sql);

            // Comprobación si la consulta devuelve algún registro
            if (!rs.next()) System.out.println("No está registrado.");

            // Si devuelve un registro, se elimina
            else {
                stD.executeUpdate("DELETE FROM alumnos WHERE id=" + id + ";");
                System.out.println("Eliminado correctamente.");
            }

            // Cerrar flujos
            rs.close();
            stQ.close();
            stD.close();
        } catch (SQLException e) {
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
