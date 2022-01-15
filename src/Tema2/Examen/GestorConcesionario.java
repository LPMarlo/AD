package Tema2.Examen; /**
 * 
 */

import java.sql.*;
import java.util.Scanner;

/**
 * @author JESUS
 *
 */
public class GestorConcesionario {
	private static Scanner sc = new Scanner(System.in);
	// ** SECCI�N DE INSERCIONES. (18%)
	// EN ESTE APARTADO GESTIONAREMOS LAS MANIPULACI�N DE LA BD	LOCAL
	public boolean insertaCocheBD(int idMarca) {
		boolean insertado = false;


		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");


		try {
			String sql = "INSERT INTO coche (IDMARCA) values(?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, idMarca);
			ps.executeUpdate();
			insertado = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertado;
	}
	
	public boolean insertaVentaBD(int idCoche, int idEmpleado, int importe) {
		boolean insertada = false;
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");


		try {
			String sql = "INSERT INTO venta(idCoche, idEmp, importe)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, idCoche);
			ps.setInt(2, idEmpleado);
			ps.setInt(3, importe);

			ps.executeUpdate();
			insertada = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertada;
	}
	
	public boolean actualizaCocheBD(int idCoche, String codCoche) {
		boolean actualizado = false;
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");
		String sql2 = "UPDATE coche SET codCoche=? WHERE idCoche=?";
		PreparedStatement ps2 = null;
		try {
			ps2 = c.prepareStatement(sql2);
			ps2.setString(1, codCoche);
			ps2.setInt(2, idCoche);

			ps2.executeUpdate();
			actualizado = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actualizado;
	}
	
	// ** SECCI�N DE FUNCIONALIDAD DE LA APLICACI�N.(30%)
	// EN ESE APARTADO IMPLEMENTAREMOS LOS M�TODOS QUE SER�N LLAMADOS DESDE EL MEN�	
	/**
	 * 1. Registra nuevo veh�culo.
	 *  
	 * 1) Muestra a los usuarios las marcas disponibles.
	 * 2) El usuario selecciona la marca.
	 * 3) Inserta el veh�culo y obtiene el id
	 * 4) Llama al m�todo generarCodigo y actualiza el COCHE.CODCOCHE
	 * 3) Muestra mensaje de inserci�n OK y el COCHE.CODCOCHE del veh�culo insertado. 
	 */
	public void registraNuevoVehiculo() {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");

			String sql2 = "SELECT * FROM MARCA";
			Statement st = c.createStatement();
			ResultSet rs2 = st.executeQuery(sql2);

			while (rs2.next()) {
				System.out.println(rs2.getString(2));
			}

			System.out.println("Marca:");
			String marca = sc.nextLine();

			String sql = "SELECT idMarca FROM marca WHERE DESCMARCA = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, marca);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				insertaCocheBD(rs.getInt(1));
			}
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2. Registra nueva venta.
	 * 1) Muestra al usuario los empleados del concesionario.
	 * 2) Muestra aquellos coches que a�n no han sido vendidos. (MARCA.DESCMARCA - COCHE.CODCOCHE)
	 */
	public void registraNuevaVenta() {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");
			String sql2 = "SELECT * FROM empleado";
			Statement st = c.createStatement();
			ResultSet rs2 = st.executeQuery(sql2);
			while (rs2.next()) {
				System.out.println(rs2.getString(1)+ rs2.getString(2)+ rs2.getString(3));
			}
			System.out.println("ID COCHE: ");
			int idCoche = Integer.parseInt(sc.nextLine());

			System.out.println("ID EMPLEADO:");
			int idEmmpleado = Integer.parseInt(sc.nextLine());

			System.out.println("Importe: ");
			int importe = Integer.parseInt(sc.nextLine());

			insertaVentaBD(idCoche, idEmmpleado, importe);
			while (rs2.next()) {
				System.out.println(rs2.getString(1)+" "+ rs2.getString(2)+ " "+rs2.getString(3));
			}

			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ** SECCI�N DE INFORMES DE LA APLICACI�N (36%)
	// En esta secci�n se implementar�n los distintos informes que puede mostrar la aplicaci�n.
	
	/**
	 * 3. Listar ventas de una marca. 
	 * @param marca Marca de la que quieren pintarse todas sus ventas ventas y empleado que la realiz� con el siguiente formato:
	 * Nombre empleado - marca - importe
	 * Ejemplo:
	 * Germ�n Ruiz Dom�nguez - Ford - 12500
	 */
	public void pintaVentasPorMarca(String marca) {
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");
			String sql2 = "SELECT e.nomemp, m.descmarca, importe FROM empleado e JOIN venta v on v.idemp=e.idemp join coche c on join marca m on m.idmarca";
			Statement st = c.createStatement();
			ResultSet rs2 = st.executeQuery(sql2);

			while (rs2.next()) {
				System.out.println(rs2.getString(1)+" "+ rs2.getString(2)+ " "+rs2.getString(3));
			}

			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 4. Calcular total de ventas de un empleado
	 * Calcular� las ventas acumuladas de un determinado empleado cuyo ID es solicitado al usuario por pantalla.
	 * Se comprobar� previamente la existencia del ID facilitado en la BD.
	 * @param idEmpleado
	 */
	public void pintaTotalVentasEmpleado(int idEmpleado) {
		String sql = "SELECT e.idemp, v.importe from empleado e join venta v on v.idemp= e.idemp where idemp = ? group by v.importe ";
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, idEmpleado);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString(1));
			}

			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 5. Informe de situaci�n. 
	 * Mostrar� todos los empleados y sus ventas si tuvieran siguiendo este formato.
	 * Germ�n Ruiz Dom�nguez:
	 *     - Ford: 12500
	 *     - Kia: 18000
	 * Laura Escudero Ben�tez:
	 *     - Este empleado a�n no ha registrado ventas.
	 *  
	 */
	public void informeSituacion() {
		String sql = "SELECT e.nomemp, e.ape1emp, e.ape2emp, v.importe from empleado e join venta v on v.idemp=e.idemp";
		Connection c = null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", "");

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
			}
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// SECCI�N GENERAR C�DIGO DE VEH�CULO. (11%)
	/**
	 * Este m�todo generar� un c�digo asociado al idCoche.
	 * Deber� llamar a la funci�n obtener_codigo
	 * create function obtener_codigo(ID INTEGER) RETURNS varchar(50)
	 * que genera un c�digo a partir del id pasado como par�metro.
	 * @param idCoche
	 * @return
	 */
	public String generarCodigoCoche(int idCoche) {
		String llamada_funcion = "{ ? = call obtener_codigo(?)}";
		String codCoche = null;
		Connection con = null;
		double gastos = 75000.00;

		try {
			// Se conecta con la base de datos. En este caso es externa y el usuario nos viene dado. Es un usuario �nicamente con funci�n de ejecuci�n de funciones
			con = DriverManager.getConnection("jdbc:mysql://185.224.138.49:3306/u579684516_ACCDAT", "u579684516_ACCDAT", "AccDat_2DAM");

			// Obtenermos una instancia de un objeto que implementa la interface CallableStatement.
			CallableStatement cst = con.prepareCall(llamada_funcion);

			// Se define el tipo de salida
			cst.registerOutParameter(1, Types.VARCHAR);
			// Se pasa el par�metro como segundo '?' de la llamada
			cst.setInt(2, idCoche);

			// Se realiza la ejecuci�n
			cst.executeUpdate();

			// Se toma el valor de salida del objeto CallableStatement y se pinta por pantalla.
			codCoche = cst.getString(1);

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
		return codCoche;
	}
}
