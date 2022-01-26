package Tema2.Prueba;

import java.sql.*;
import java.util.Scanner;

public class GestionAccdatffinity {

    private static final String datos_conexion = "jdbc:mysql://localhost:3306/sanidad";
    private static final String usuario = "root";
    private static final String password = "";
    private static final String crearTablaTemas = "drop table if exists temas; " +
            "CREATE TABLE temas ( codigoTema varchar(20) NULL CONSTRAINT temas_pk PRIMARY KEY (codigoTema) ENGINE=InnoDB " +
            "DEFAULT CHARSET=utf8mb4 " +
            "COLLATE=utf8mb4_general_ci;";
    private static final String alterTable = "ALTER TABLE peliculas ADD CONSTRAINT codigoTema_FK FOREIGN KEY (nombreTema) REFERENCES temas(codigoTema);";
    private static final String cargarTemas = "INSERT INTO TEMAS";
    public static final String QUERY_NOMBRES_TEMA = "select nombreTema from peliculas group by nombreTema;";
    public static final String INSERT_CODIGO_TEMA = "INSERT INTO temas(codigoTema) values (?);";

    public static void main(String[] args) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(datos_conexion, usuario, password);
            creaYcargaTemas(con);
            cargaUsuariosYOpiniones(con);
            registraNuevoUsuario(con);
            registraValoracion(con);
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

    private static void registraValoracion(Connection con) {
        System.out.println("Nombre Usuario: ");
        String nombreUsu = sc.nextLine();

        System.out.println("Nombre Pelicula: ");
        String nombrePelicula = sc.nextLine();

        System.out.println("Valoracion: ");
        int valoracion = Integer.parseInt(sc.nextLine());


        try {
            boolean todoOk = true;
            PreparedStatement psQuery = con.prepareStatement("SELECT nombreUsu from usuarios where nombreUsu = ?");
            psQuery.setString(1, nombreUsu);
            ResultSet rs = psQuery.executeQuery();

            if (!rs.next()) {
                System.out.println("No existe el usuario: " + nombreUsu);
                todoOk = false;
            }

            psQuery = con.prepareStatement("SELECT codigoPeli from peliculas where codigoPeli = ?");
            psQuery.setString(1, nombrePelicula);
            rs = psQuery.executeQuery();

            if (!rs.next()) {
                System.out.println("No existe la película " + nombrePelicula);
                todoOk = false;
            }

            psQuery = con.prepareStatement("SELECT nombreUsu, codigoPeli from opiniones where codigoPeli = ? and nombreUsu =?");
            psQuery.setString(1, nombrePelicula);
            psQuery.setString(2, nombreUsu);
            rs = psQuery.executeQuery();

            if (rs.next()) {
                System.out.println("Ya hay una valoración del usuario " + nombreUsu + " de la pelicula " + nombrePelicula + ".");
                todoOk = false;
            }

            if (todoOk) {
                PreparedStatement psInsert = con.prepareStatement("INSERT INTO opiniones(nombreUsu, codigoPeli, valoracion) VALUES (?,?,?);");
                psInsert.setString(1, nombreUsu);
                psInsert.setString(2, nombrePelicula);
                psInsert.setInt(3, valoracion);
                psInsert.executeUpdate();

                con.commit();
                System.out.println("Su opinion ha sido guardada correctamente.");
            }


        } catch (SQLException e) {
            System.out.println("Error en el registro: " + e.getMessage());
            try {
                con.rollback();
                System.out.println("Rollback realizado.");
            } catch (SQLException re) {
                System.out.println("Error en el rollback: " + re.getMessage());
                System.out.println("Error realizando Rollback");
            }
        }
    }

    private static Scanner sc = new Scanner(System.in);
    private static void registraNuevoUsuario(Connection con) {
        System.out.println("Nombre Usuario: ");
        String nombreUsu = sc.nextLine();

        System.out.println("Nombre Completo: ");
        String nombreLargo = sc.nextLine();

        System.out.println("Edad: ");
        int edad = Integer.parseInt(sc.nextLine());


        try {
            con.setAutoCommit(false);

            PreparedStatement psQuery = con.prepareStatement("SELECT nombreUsu from usuarios where nombreUsu = ?;");
            ResultSet rs = psQuery.executeQuery();
            boolean existe = rs.next();

            while (existe) {
                System.out.println("Usuario ya registrado. Introduce otro: ");
                nombreUsu = sc.nextLine();

                psQuery = con.prepareStatement("SELECT nombreUsu from usuarios where nombreUsu = ?;");
                rs = psQuery.executeQuery();
                existe = rs.next();
            }

            PreparedStatement psInsert = con.prepareStatement("INSERT INTO usuarios(nombreUsu, nombreLargo, edad)  values (?,?,?)");
            psInsert.setString(1, nombreUsu);
            psInsert.setString(2, nombreLargo);
            psInsert.setInt(3, edad);
            psInsert.executeUpdate();

            con.commit();
            System.out.println("Usuario registrado satisfactoriamente.");
        } catch (SQLException e) {
            System.out.println("Error en el registro: " + e.getMessage());
            try {
                con.rollback();
                System.out.println("Usuario no válido.");
            } catch (SQLException re) {
                System.out.println("Error en el rollback: " + re.getMessage());
                System.out.println("Error realizando Rollback");
            }
        }
    }

    private static void cargaUsuariosYOpiniones(Connection con) {
        try {
            con.setAutoCommit(false);
            PreparedStatement psInsert = con.prepareStatement("INSERT INTO usuarios(NOMBREUSU, NOMBRELARGO, EDAD) values (?,?,?)");
            PreparedStatement psInsert2 = con.prepareStatement("INSERT INTO opiniones(nombreUsu, codigoPeli, valoracion) values (?,?,?)");

            psInsert.setString(1, "gianpac");
            psInsert.setString(2, "Gian Luca Pacceli");
            psInsert.setInt(3, 18);
            psInsert.executeUpdate();

            psInsert2.setString(1, "gianpac");
            psInsert2.setInt(2, 1);
            psInsert2.setInt(3, 9);
            psInsert2.executeUpdate();

            psInsert2.setString(1, "gianpac");
            psInsert2.setInt(2, 2);
            psInsert2.setInt(3, 10);
            psInsert2.executeUpdate();

            psInsert2.setString(1, "gianpac");
            psInsert2.setInt(2, 3);
            psInsert2.setInt(3, 6);
            psInsert2.executeUpdate();

            con.commit();

            psInsert.setString(1, "pietar");
            psInsert.setString(2, "Prieto Armani");
            psInsert.setInt(3, 20);
            psInsert.executeUpdate();

            psInsert2.setString(1, "pietar");
            psInsert2.setInt(2, 2);
            psInsert2.setInt(3, 10);
            psInsert2.executeUpdate();

            psInsert2.setString(1, "pietar");
            psInsert2.setInt(2, 4);
            psInsert2.setInt(3, 9);
            psInsert2.executeUpdate();

            psInsert2.setString(1, "pietar");
            psInsert2.setInt(2, 5);
            psInsert2.setInt(3, 6);
            psInsert2.executeUpdate();

            con.commit();

            psInsert.setString(1, "mieche");
            psInsert.setString(2, "Miguel Echevarría");
            psInsert.setInt(3, 18);
            psInsert.executeUpdate();

            psInsert2.setString(1, "mieche");
            psInsert2.setInt(2, 1);
            psInsert2.setInt(3, 9);
            psInsert2.executeUpdate();

            psInsert2.setString(1, "mieche");
            psInsert2.setInt(2, 2);
            psInsert2.setInt(3, 5);
            psInsert2.executeUpdate();

            psInsert2.setString(1, "mieche");
            psInsert2.setInt(2, 4);
            psInsert2.setInt(3, 4);
            psInsert2.executeUpdate();

            psInsert2.setString(1, "mieche");
            psInsert2.setInt(2, 5);
            psInsert2.setInt(3, 1);
            psInsert2.executeUpdate();

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

    private static void creaYcargaTemas(Connection con) {
        try {
            con.setAutoCommit(false);
            PreparedStatement psQuery = con.prepareStatement(crearTablaTemas);
            psQuery.executeUpdate();

            psQuery = con.prepareStatement(QUERY_NOMBRES_TEMA);
            ResultSet rs = psQuery.executeQuery();

            PreparedStatement psInsert = con.prepareStatement(INSERT_CODIGO_TEMA);
            while (rs.next()) {
                String codigoTema = rs.getString(1);
                psInsert.setString(1, codigoTema);
                psInsert.addBatch();
            }
            psInsert.executeBatch();

            psInsert = con.prepareStatement(crearTablaTemas);
            psQuery.executeUpdate();

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
}
