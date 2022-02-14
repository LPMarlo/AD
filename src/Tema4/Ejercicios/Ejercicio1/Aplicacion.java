package Tema4.Ejercicios.Ejercicio1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Aplicacion {
    public static void main(String[] args) {
        Departamento informatica = new Departamento("Informática");
        List<Empleado> empleados1 = List.of(
                new Empleado("Perico", "Rodríguez", "Suárez"),
                new Empleado("María", "Jiménez", "Páez"),
                new Empleado("Jesús", "Ruiz", "Ruiz")
        );
        Departamento comecial = new Departamento("Comercial");
        List<Empleado> empleados2 = List.of(
                new Empleado("Gema", "Sánchez", "Rodríguez"),
                new Empleado("Andrea", "Ortiz", "Márquez")
        );

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oficina", "root", "");

            int idInformatica = informatica.insertar(con);
            for (Empleado empleado : empleados1) empleado.insertar(con, idInformatica);

            int idComercial = comecial.insertar(con);
            for (Empleado empleado : empleados2) empleado.insertar(con, idComercial);

            con.close();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("Error.");
                }
            }
        }
    }
}
