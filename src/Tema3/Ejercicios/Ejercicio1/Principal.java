package Tema3.Ejercicios.Ejercicio1;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.QueryComparator;

import java.util.Scanner;

/**
 * @author Marlo Lebrón Pareja
 */
public class Principal {

    private static final String BD_PERSONAS = "ficheros/personas.oo";
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opc;
        ObjectContainer db = abrirBd();
        do {
            opc = solicitarOpcion();
            tratarOpcion(opc,db);
        } while (opc != 10);
        db.close();
    }

    /**
     * Crea una referencia embebida a la BD OO.
     * @return referencia a la BD
     */
    private static ObjectContainer abrirBd(){
        return Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD_PERSONAS);
    }

    /**
     * Gestión del menú
     * @param opc opción seleccionada.
     * @param db referencia a la BD
     */
    private static void tratarOpcion(int opc, ObjectContainer db) {
        switch (opc) {
            case 1 -> insertarPersonaEnBd(db);
            case 2 -> consultarBd(db);
            case 3 -> consultarPersonasConEdad(db, solicitarEdad());
            case 4 -> modificarDatosPorDNI(db);
            case 5 -> asociarCuenta(db);
            case 6 -> consultarDatosPersona(db);
            case 7 -> eliminarPersonaYCuentaAsociada(db);
            case 8 -> consultarCuentas(db);
            case 9 -> consultarPersonaConDeterminadoSaldo(db);
        }
    }

    /**
     * Presentación y grabación de la opción seleccionada.
     * @return opción seleccionada.
     */
    private static int solicitarOpcion() {
        System.out.println("1. Insertar persona en  BD.");
        System.out.println("2. Consultar BD completa.");
        System.out.println("3. Consultar personas con una edad.");
        System.out.println("4. Modificar datos por DNI.");
        System.out.println("5. Asociar cuenta.");
        System.out.println("6. Consultar datos de persona.");
        System.out.println("7. Eliminar persona y cuenta asociada.");
        System.out.println("8. Consultar cuentas.");
        System.out.println("9. Consultar persona con un determinado saldo.");
        System.out.println("10. Salir.");

        int opc;
        do {
            System.out.println("Opción:");
            opc = Integer.parseInt(teclado.nextLine());
        } while (opc < 1 || opc > 9);

        return opc;
    }

    /**
     * Consulta sin filtro, para ello se crea una persona sin modificar sus parámetros.
     * @param db Referencia a la BD
     */
    private static void consultarBd(ObjectContainer db) {

        Persona patron = new Persona();
        ObjectSet<Persona> result = db.queryByExample(patron);

        if (result.size() == 0)
            System.out.println("BD Vacia");
        else {
            System.out.println("Numero de personas " + result.size());
            for (Persona persona: result) {
                System.out.println(persona);
            }
        }
    }

    /**
     * Consulta conjunto de personas por edad.
     * @param db Referencia a la BD.
     * @param edad Edad a consultar. Para ello se crea Persona con la edad pasada como parámetro.
     */
    private static void consultarPersonasConEdad( ObjectContainer db, int edad) {
        Persona patron = new Persona(null, edad, null, null); // consultar todas las personas que tienen esa edad
        Persona per;

        ObjectSet<Persona> result = db.queryByExample(patron);

        if (result.size() == 0)
            System.out.println("BD Vacia");
        else {
            System.out.println("Numero de personas con edad " + edad  + " son: "  + result.size());
            while (result.hasNext()) {
                per = result.next();
                System.out.println( per);
            }
        }
    }

    /**
     * Inserta objeto persona en BD con los datos solicitados al usuario.
     * @param db Referencia a la base de datos.
     */
    private static void insertarPersonaEnBd(ObjectContainer db) {
        Persona persona = crearPersona(db);
        db.store(persona);
    }

    private static void asociarCuenta(ObjectContainer db) {
        String dni = solicitarCadena("DNI: ");
        Persona persona = consultarPersonaPorDNI(db, dni);
        Cuenta cuenta = crearCuenta(db);

        if (persona != null) {
            if (cuenta != null) {
                persona.setCuenta(cuenta);
                db.store(persona);
                db.store(cuenta);
            } else {
                System.out.println("La cuenta ya existe.");
            }
        } else {
            System.out.println("No existe una persona con ese DNI.");
        }
    }

    private static Cuenta consultarCuenta(ObjectContainer db, int numCuenta) {
        ObjectSet<Object> resultado = db.queryByExample(new Cuenta(numCuenta, 0));
        if (resultado.size() == 0) {
            return null;
        } else {
            return (Cuenta) resultado.next();
        }
    }

    private static void modificarDatosPorDNI(ObjectContainer db) {
        String dni = solicitarCadena("DNI: ");

        System.out.println("1. Modificar edad.");
        System.out.println("2. Modificar nombre.");
        System.out.println("3. Salir.");

        int opc;
        do {
            System.out.println("Opción:");
            opc = Integer.parseInt(teclado.nextLine());
        } while (opc < 1 || opc > 3);

        switch (opc) {
            case 1 -> modificarEdad(db, dni);
            case 2 -> modificarNombre(db, dni);
        }
    }

    private static void modificarNombre(ObjectContainer db, String dni) {
        Persona persona = consultarPersonaPorDNI(db, dni);

        if (persona != null) {
            String nombre = solicitarCadena("Nombre: ");
            persona.setNombre(nombre);
            db.store(persona);
            System.out.println("Nombre modificado.");
        } else {
            System.out.println("No existe una persona con ese DNI.");
        }
    }

    private static void modificarEdad(ObjectContainer db, String dni) {
        Persona persona = consultarPersonaPorDNI(db, dni);

        if (persona != null) {
            int edad = solicitarEdad();
            persona.setEdad(edad);
            db.store(persona);
            System.out.println("Edad modificada.");
        } else {
            System.out.println("No existe una persona con ese DNI.");
        }
    }

    private static Persona consultarPersonaPorDNI(ObjectContainer db, String dni) {
        ObjectSet<Object> resultado = db.queryByExample(new Persona( null, 0, dni, null));

        if (resultado.hasNext()) {
            return (Persona) resultado.next();
        } else {
            return null;
        }
    }

    private static void consultarCuentas(ObjectContainer db) {
        ObjectSet<Cuenta> cuentas = db.queryByExample(Cuenta.class);
        for (Cuenta cuenta : cuentas) {
            System.out.println(cuenta);
        }
    }

    private static void eliminarPersonaYCuentaAsociada(ObjectContainer db) {
        String dni = solicitarCadena("DNI: ");
        Persona persona = consultarPersonaPorDNI(db, dni);

        if (persona != null) {
            Cuenta cuenta = consultarCuenta(db, persona.getNumCuenta());
            if (cuenta != null) {
                db.delete(cuenta);
                System.out.println("Cuenta asociada eliminada.");
            }
            db.delete(persona);
            System.out.println("Persona eliminada.");
        } else {
            System.out.println("No existe una persona con ese DNI.");
        }
    }

    private static void consultarDatosPersona(ObjectContainer db) {
        String dni = solicitarCadena("DNI: ");
        Persona persona = consultarPersonaPorDNI(db, dni);
        if (persona != null) {
            System.out.println(persona);
        } else {
            System.out.println("No existe una persona con ese DNI.");
        }
    }

    private static void consultarPersonaConDeterminadoSaldo(ObjectContainer db) {
        double saldo = solicitarDouble("Saldo: ");
        ObjectSet<Persona> personas = db.query(new Predicate<>() {
            @Override
            public boolean match(Persona persona) {
                return persona.getSaldo() >= saldo;
            }
        }, (QueryComparator<Persona>) (persona, target1) -> Double.compare(persona.getSaldo(), target1.getSaldo()));

        if (personas.size() != 0) {
            for (Persona persona : personas) {
                System.out.println(persona);
            }
        }
        else {
            System.out.println("No se ha encontrado ningún resultado.");
        }
    }

    // CREACIÓN DE OBJETOS

    /**
     * Crea una cuenta con los datos solicitados al usuario.
     * @param db Referencia a la base de datos.
     * @return Objeto cuenta creado.
     */
    private static Cuenta crearCuenta(ObjectContainer db) {
        int numCuenta = solicitarInt("Número de cuenta: ");
        double saldo = solicitarDouble("Saldo: ");

        Cuenta cuenta = new Cuenta(numCuenta, saldo);
        Cuenta aux = consultarCuenta(db, numCuenta);

        if (aux == null) {
            db.store(cuenta);
            return cuenta;
        } else {
            return null;
        }
    }

    /**
     * Método que crea una persona solicitando la información por teclado.
     * @return Devuelve un instancia del objeto persona con los datos ingresados por el usuario.
     */
    private static Persona crearPersona(ObjectContainer db) {
        Persona persona = null;
        ObjectSet<Persona> result;

        do {
            String dni = solicitarCadena("DNI: ");
            Persona patron = new Persona(null, 0, dni, null);

            result = db.queryByExample(patron);

            if (result.size() == 0)
                persona = new Persona(solicitarCadena("Nombre: "),solicitarEdad(), dni, null);
            else {
                System.out.println("Ya existe una persona con ese DNI.");
            }
        } while (result.size() != 0);

        return persona;
    }

    // SOLICITUDES DE DATOS

    private static double solicitarDouble(String msg) {
        System.out.println(msg);
        return Double.parseDouble(teclado.nextLine());
    }

    private static int solicitarInt(String msg) {
        System.out.println(msg);
        return Integer.parseInt(teclado.nextLine());
    }

    private static String solicitarCadena(String msg) {
        System.out.println(msg);
        return teclado.nextLine();
    }

    /**
     * Método para interactuar con el usuario solicitando la edad.
     * @return int con la edad.
     */
    private static int solicitarEdad() {
        int edad;
        do {
            System.out.println("Introduce edad:");
            edad = Integer.parseInt(teclado.nextLine());
        } while (edad < 0);
        return edad;
    }
}
