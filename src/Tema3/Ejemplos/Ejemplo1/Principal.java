package Tema3.Ejemplos.Ejemplo1;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Scanner;

public class Principal {
    private static final String BD_PERSONAS = "ficheros/personas.oo";
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        int opc;
        ObjectContainer db;

        db=abrirBd();
        do {
            opc = solicitarOpcion();
            tratarOpcion(opc,db);
        } while (opc != 4);

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
     * Gesti�n del men�
     * @param opc opci�n seleccionada.
     * @param db referencia a la BD
     */
    private static void tratarOpcion(int opc, ObjectContainer db) {
        int edad;
        switch (opc) {
            case 1 -> insertarPersonaEnBd(db);
            case 2 -> consultarBd(db);
            case 3 -> {
                edad = solicitarEdad();
                consultarPersonasConEdad(db, edad);
            }
        }
    }

    /**
     * Presentaci�n y grabaci�n de la opci�n seleccionada.
     * @return
     */
    private static int solicitarOpcion() {
        int opc;
        System.out.println("1.Insertar persona en  BD");
        System.out.println("2.Consultar BD completa");
        System.out.println("3.Consultar personas con una edad");
        System.out.println("4.Salir");

        do {
            System.out.println("Introduce opcion");
            opc = Integer.parseInt(teclado.nextLine());
        } while (opc < 1 || opc > 4);
        return opc;
    }

    /**
     * Consulta sin filtro, para ello se crea una persona sin modificar sus par�metros.
     * @param db Referencia a la BD
     */
    private static void consultarBd(ObjectContainer db) {

        Persona patron = new Persona(); // consultar todas las personas, sin filtro
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
     * @param edad Edad a consultar. Para ello se crea Persona con la edad pasada como par�metro.
     */
    private static void consultarPersonasConEdad( ObjectContainer db, int edad) {
        Persona patron = new Persona(null, edad); // consultar todas las personas que tienen esa edad
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
        Persona persona = crearPersona();
        db.store(persona);
    }

    /**
     * M�todo que crea una persona solicitando la informaci�n por teclado.
     * @return Devuelve un instancia del objeto persona con los datos ingresados por el usuario.
     */
    private static Persona crearPersona() {
        Persona persona;
        persona = new Persona(solicitarCadena(),solicitarEdad());
        return persona;
    }

    /**
     * M�todo para interactuar con el usuario solicitando la edad.
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

    /**
     * M�todo para interactuar con el usuario solicitando el nombre.
     * @return String con la edad.
     */
    private static String solicitarCadena() {
        String nombre;
        System.out.println("Nombre: ");
        nombre = teclado.nextLine();
        return nombre;
    }
}
