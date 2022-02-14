package Tema4.Ejercicios.Ejercicio1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Empleado {
    private int idEmpleado;
    private String nombreEmpleado;
    private String ape1Empleado;
    private String ape2Empleado;

    public Empleado(String nombreEmpleado, String ape1Empleado, String ape2Empleado) {
        this.nombreEmpleado = nombreEmpleado;
        this.ape1Empleado = ape1Empleado;
        this.ape2Empleado = ape2Empleado;
    }

    public void insertar(Connection con, int idDepartamento) {
        String sql = "INSERT INTO empleado(nombreEmpleado, ape1Empleado, ape2Empleado, idDepartamento) VALUES (?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreEmpleado);
            ps.setString(2, ape1Empleado);
            ps.setString(3, ape2Empleado);
            ps.setInt(4, idDepartamento);
            ps.executeUpdate();
            System.out.println("Empleado insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error. Empleado no insertado.");
        }
    }
}
