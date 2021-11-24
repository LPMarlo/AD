package Tema2.Ejemplos;

import java.sql.*;
import java.util.Scanner;

public class Ejercicio3 {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("ID: ");
        String id = sc.nextLine();
        consultaEmail(id);
    }

    private static void consultaEmail(String id) {
        // Conexión
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/PruebaConexionBD", "root", "");

            String sql = "SELECT email FROM alumnos WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, Integer.parseInt(id));
            ResultSet rs = pst.executeQuery();

            // Comprobación si la consulta devuelve algún registro
            boolean hayFilas = false;
            while (rs.next()) {
                System.out.println("Email: " + rs.getString(1));
                hayFilas = true;
            }
            if (!hayFilas) System.out.println("ID no válido.");

            // Cerrar flujos
            rs.close();
            pst.close();
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
