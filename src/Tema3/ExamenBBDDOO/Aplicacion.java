package Tema3.ExamenBBDDOO;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.query.QueryComparator;

import java.util.List;
import java.util.Scanner;

/**
 * @author Marlo
 */
public class Aplicacion {
	private static final String BD_AGENDA = "ficheros/accdatgenda.oo";
	private static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int opc;
		ObjectContainer db;
		
		db=abrirBd();
		do {
			muestraMenu();
			opc = solicitarOpcion();
			tratarOpcion(opc,db);
		} while (opc != 7);
		
		System.out.println("Cerrando agenda...");	
	    db.close();		
	}

	private static ObjectContainer abrirBd() {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(Contacto.class).cascadeOnUpdate(true);
		config.common().objectClass(Contacto.class).cascadeOnDelete(true);
		return Db4oEmbedded.openFile(config, BD_AGENDA);
	}

	public static void muestraMenu() {
		System.out.println("1. Alta de contacto.");
		System.out.println("2. Añadir dato de contacto.");
		System.out.println("3. Eliminar dato de contacto.");
		System.out.println("4. Buscar contacto.");
		System.out.println("5. Calcular contactos.");
		System.out.println("6. Obtener contactos en un rango de edad.");
		System.out.println("7. Salir.");
	}

	/**
	 * Presentación y grabación de la opción seleccionada.
	 * @return opcion
	 */
	private static int solicitarOpcion() {
		int opc;
		System.out.println("Introduzca la opción que desea realizar:");
		opc = Integer.parseInt(sc.nextLine());
		return opc;
	}

	private static void tratarOpcion(int opc, ObjectContainer db) {
		switch (opc) {
			case 1 -> altaContacto(db);
			case 2 -> addDatoAContacto(db);
			case 3 -> delDatoContacto(db);
			case 4 -> buscarContacto(db);
			case 5 -> calcularContactos(db);
			case 6 -> contactosRangoEdad(db);
		}
	}

	private static void contactosRangoEdad(ObjectContainer db) {
		int rangoMin = inputInt("Edad mínima: ");
		int rangoMax = inputInt("Edad máxima: ");

		ObjectSet<Contacto> contactos = db.query(new Predicate<>() {
			@Override
			public boolean match(Contacto c) {
				return c.getEdad() < rangoMax && c.getEdad() > rangoMin;
			}
		}, (QueryComparator<Contacto>) (c1, c2) -> {
			int i;
			if (c1.getNombre().compareTo(c2.getNombre()) == 0) {
				if (c1.getApellido1().compareTo(c2.getApellido1()) == 0) {
					i = c1.getApellido2().compareTo(c2.getApellido2());
				} else {
					i = c1.getApellido1().compareTo(c2.getApellido1());
				}
			} else {
				i = c1.getNombre().compareTo(c2.getNombre());
			}
			return i;
		});

		if (contactos.size() != 0) {
			for (Contacto c : contactos) {
				System.out.println(c);
			}
		}
		else System.out.println("No se ha encontrado ningún registro.");
	}

	private static void calcularContactos(ObjectContainer db) {
		String tipoDato = inputString("Indica el tipo ((F-Fijos, M-Móviles u T-Oficina):");

		if (tipoDato.equalsIgnoreCase("F") || tipoDato.equalsIgnoreCase("M") || tipoDato.equalsIgnoreCase("T")) {
			ObjectSet<DatoContacto> datosContactos = db.query(new Predicate<>() {
				@Override
				public boolean match(DatoContacto dc) {
					return dc.getTipoDato().equals(tipoDato);
				}
			});
			if (datosContactos.hasNext()) {
				System.out.println("Número de datos: " + datosContactos.size());
			} else {
				System.out.println("No se ha encontrado ningún registro");
			}
		} else {
			System.out.println("Tipo de dato incorrecto.");
		}

	}

	private static void buscarContacto(ObjectContainer db) {
		String cadena = inputString("Busqueda: ");
		ObjectSet<Contacto> contactos = db.query(new Predicate<>() {
			@Override
			public boolean match(Contacto c) {
				return c.getNombre().contains(cadena) || c.getApellido1().contains(cadena) || c.getApellido2().contains(cadena);
			}
		}, (QueryComparator<Contacto>) (c1, c2) -> Integer.compare(c1.getEdad(), c2.getEdad()));

		if (contactos.size() != 0) {
			for (Contacto c : contactos) {
				System.out.println(c);
			}
		}
		else System.out.println("No se ha encontrado ningún registro.");
	}

	private static void delDatoContacto(ObjectContainer db) {
		ObjectSet<Contacto> result = comprobarContacto(db);

		if (result.hasNext()) {
			Contacto contacto = result.next();
			eliminarDatoContacto(db, contacto);
		} else {
			System.out.println("No existe el contacto introducido");
		}
	}

	private static void eliminarDatoContacto(ObjectContainer db, Contacto contacto) {
		List<DatoContacto> datos = contacto.getDatos();

		// Control de existencia de datos
		if (datos.size() > 0) {
			// Muestra los datos de contacto existentes
			System.out.println("DATOS DE CONTACTO:");
			for (int i = 0; i < datos.size(); i++) {
				System.out.println((i+1) +". "+datos.get(i));
			}
			int opt = inputInt("Indique cuál desea borrar: ");

			if (opt < 1 || opt > datos.size()) {
				System.out.println("Opción no permitida");
			} else {
				DatoContacto datoContacto = datos.get(opt-1);
				contacto.removeDato(datoContacto);
				db.delete(datoContacto);
				db.store(contacto);
				System.out.println("Dato eliminado correctamente");
			}
		} else {
			System.out.println("No se han encontrado datos de este contacto.");
		}
	}

	private static void addDatoAContacto(ObjectContainer db) {
		// Control contacto existente
		ObjectSet<Contacto> result = comprobarContacto(db);
		if (result.hasNext()) {
			Contacto contacto = result.next();
			registrarDatosContacto(db, contacto);
		} else {
			System.out.println("No existe un contacto con la información introducida");
		}

	}

	private static ObjectSet<Contacto> comprobarContacto(ObjectContainer db) {
		String nombre = inputString("Nombre: ");
		String apellido1 = inputString("Primer apellido: ");
		String apellido2 = inputString("Segundo apellido: ");
		return db.queryByExample(new Contacto(apellido1, apellido2, nombre));
	}

	private static void altaContacto(ObjectContainer db) {
		ObjectSet<Contacto> result;
		do {
			String nombre = inputString("Nombre: ");
			String apellido1 = inputString("Primer apellido: ");
			String apellido2 = inputString("Segundo apellido: ");
			int edad = inputInt("Edad: ");

			// Control de duplicidad
			result = db.queryByExample(new Contacto(apellido1, apellido2, nombre));
			if (result.hasNext()) {
				System.out.println("Ya existe un contacto con la información introducida");
			} else {
				Contacto contacto = new Contacto(apellido1, apellido2, nombre, edad);
				registrarDatosContacto(db, contacto);
			}
		} while (result.size() != 0);
		System.out.println("Alta de contacto realizada correctamente.");
	}

	private static void registrarDatosContacto(ObjectContainer db, Contacto contacto) {
		do {
			String tipoDato = inputString("Tipo de dato (F, M, T):").toUpperCase();
			int valor = inputInt("Valor: ");

			// Filtro tipo de valor correcto
			if (tipoDato.equalsIgnoreCase("F") || tipoDato.equalsIgnoreCase("M") || tipoDato.equalsIgnoreCase("T")) {
				ObjectSet<DatoContacto> result = db.queryByExample(new DatoContacto(valor));

				// Control duplicidad de valores
				if (result.hasNext()) {
					System.out.println("Error. Dato de contacto duplicado.");
				} else {
					DatoContacto datoContacto = new DatoContacto(tipoDato, valor);
					contacto.addDato(datoContacto);
					db.store(datoContacto);
				}
			} else {
				System.out.println("Error. Tipo de dato incorrecto.");
			}
		} while (inputString("¿Desea añadir otro número? [S/N]").equalsIgnoreCase("S"));
		db.store(contacto);
	}


	private static int inputInt(String s) {
		System.out.println(s);
		return Integer.parseInt(sc.nextLine());
	}

	private static String inputString(String s) {
		System.out.println(s);
		return sc.nextLine();
	}
}
