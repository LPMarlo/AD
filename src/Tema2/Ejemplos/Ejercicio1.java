package Tema2.Ejemplos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class Ejercicio1 {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        // crea una conexión
        Connection conn1 = null;
        try (PrintWriter pw = new PrintWriter("ficheros\\alumnos.sql");){
            String url1 = "jdbc:mysql://localhost:3306/PruebaConexionBD";
            String user = "root";
            String password = "";
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement stmt = conn1.createStatement();
            String sql = "SELECT id, nombre, apellido1, apellido2, email, edad FROM alumnos;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String insert = "INSERT INTO (id, nombre, apellido1, apellido2, email, edad) VALUES ("+rs.getString(1)+", '"+
                        rs.getString(2)+"', '"+rs.getString(3)+"', '"+rs.getString(4)+"', '"+
                        rs.getString(5)+"', '"+rs.getString(6)+"');";
                pw.println(insert);
                System.out.println(insert);
            }

            System.out.println();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("ERROR");
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
