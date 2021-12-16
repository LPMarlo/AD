package Tema2.Ejercicios;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

public class Ejercicio12 {

    public static String llamada_funcion =
            "{ ? = call pasar_a_mayusculas(?)}";

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Mensaje:");
        String msg = sc.nextLine();

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://185.224.138.49:3306/u579684516_ACCDAT", "u579684516_ACCDAT", "AccDat_2DAM");

            CallableStatement cst = con.prepareCall(llamada_funcion);

            // Parametro de Salida
            cst.registerOutParameter(1, Types.VARCHAR);

            // Parametro de Entrada
            cst.setString(2, msg);

            // Ejecución
            cst.executeUpdate();

            // Mostrar valor de salida
            System.out.println(cst.getString(1));

            cst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }
}
