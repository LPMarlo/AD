package Tema2.Ejercicios.Repaso;

import java.sql.*;
import java.util.Scanner;

public class sanidad {
    private static final String datos_conexion = "jdbc:mysql://localhost:3306/sanidad";
    private static final String usuario = "root";
    private static final String password = "";

    private static final String insertAsegurado = "INSERT INTO asegurado(nombre, edad) VALUES(?, ?)";
    private static final String insertVisita = "INSERT INTO visitacentro(codigoCentro, idAsegurado) VALUES(?, ?)";
    private static final String selectPacientes = "SELECT * FROM asegurado a RIGHT JOIN visitacentro v on a.idAsegurado = v.idAsegurado GROUP BY a.idAsegurado order by a.idAsegurado;";
    private static final String crearTablaCentro = "CREATE TABLE `centro` (`codigoCentro` int(5) NOT NULL, `descCentro` varchar(25) DEFAULT NULL, PRIMARY KEY (`codigoCentro`))";
    private static final String codigoCentroFK = "ALTER TABLE visitacentro ADD CONSTRAINT codigoCentro_FK FOREIGN KEY (codigoCentro) REFERENCES centro(codigoCentro);";

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(datos_conexion, usuario, password);
            //crearTablaCentro(con);
            String respuesta = "SI";
            while (respuesta.equals("SI")) {
                cargarDatos(con);

                System.out.println("¿Desea introducir otro asegurado?");
                respuesta = sc.nextLine().toUpperCase();
            }
            listarPacientes(con);

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

    private static void crearTablaCentro(Connection con) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(crearTablaCentro);
            st.executeUpdate(codigoCentroFK);
            st.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarPacientes(Connection con) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectPacientes);
            while (rs.next()) {
                System.out.println("ID: " + rs.getString(1));
                System.out.println("\tNombre: " + rs.getString(2));
                System.out.println("\tEdad: " + rs.getString(3));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cargarDatos(Connection con) {
        System.out.println("\n*NUEVO ASEGURADO*");
        System.out.println("Nombre: ");
        String nombre = sc.nextLine();

        System.out.println("Edad:");
        int edad = Integer.parseInt(sc.nextLine());

        try {
            con.setAutoCommit(false);
            PreparedStatement psInsert = con.prepareStatement(insertAsegurado, Statement.RETURN_GENERATED_KEYS);

            psInsert.setString(1, nombre);
            psInsert.setInt(2, edad);

            psInsert.executeUpdate();
            ResultSet rs = psInsert.getGeneratedKeys();

            rs.next();
            int idAsegurado = rs.getInt(1);
            crearVisita(con, idAsegurado);
            con.commit();
            System.out.println("Inserción realizada y transacción consolidada (commit realizado).");
        } catch (SQLException e) {
            System.out.println("Error en la inserción: " + e.getMessage());
            try {
                con.rollback();
                System.out.println("Rollback realizado");
            } catch (SQLException re) {
                System.out.println("Error en el rollback: " + re.getMessage());
                System.out.println("Error realizando Rollback");
            }
        }
    }

    private static void crearVisita(Connection con, int idAsegurado) throws SQLException {
        PreparedStatement psInsert;
        String respuesta = "SI";
        while (respuesta.equals("SI")) {
            System.out.println("¿Desea registrar una visita?:");
            respuesta = sc.nextLine().toUpperCase();

            if (respuesta.equals("SI")) {
                System.out.println("Código del centro:");
                String codigoCentro = sc.nextLine().toUpperCase();

                psInsert = con.prepareStatement(insertVisita);
                psInsert.setString(1, codigoCentro);
                psInsert.setInt(2, idAsegurado);
                psInsert.executeUpdate();
                psInsert.close();
            }
        }

    }

}
