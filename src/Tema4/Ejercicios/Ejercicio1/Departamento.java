package Tema4.Ejercicios.Ejercicio1;

import java.sql.*;
import java.util.List;

public class Departamento {

    private int idDepartamento;
    private String nombreDepartamento;
    private List<Empleado> empleados;

    public Departamento( String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public int insertar(Connection con) {
        String sql = "INSERT INTO departamento(nombreDepartamento) VALUES (?)";
        PreparedStatement ps = null;
        int id = 0;
        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombreDepartamento);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            System.out.println("Departamento insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error. Departamento no insertado.");
        }
        return id;
    }
}
