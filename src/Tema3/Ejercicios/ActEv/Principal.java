package Tema3.Ejercicios.ActEv;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.query.QueryComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Principal {
    private static final String BD = "ficheros/restaurantes.oo";
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opc;

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Restaurante.class).cascadeOnUpdate(true);
        ObjectContainer db = Db4oEmbedded.openFile(config, BD);
        do {
            //cargarDatos(db);
            opc = solicitarOpcion();
            tratarOpcion(opc, db);
        } while (opc != 8);
        db.close();
    }

    private static void cargarDatos(ObjectContainer db) {
        Nomina nomina = new Nomina("ES21 1465 0100 72 2030876293", 153892.47);

        ArrayList<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado("Marlo", "Lebron", "Pareja", "12345678A", nomina));

        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Agua", 1, Categoria.BEBIDA.toString()));
        productos.add(new Producto("Ensalada", 4.5, Categoria.COMIDA.toString()));
        productos.add(new Producto("Pastel Vegano", 2.75, Categoria.POSTRE.toString()));

        Restaurante restaurante = new Restaurante("RestAccdat", empleados, productos);

        db.store(restaurante);
    }

    private static int solicitarOpcion() {
        System.out.println("1. Alta de restaurante.");
        System.out.println("2. Registrar productos.");
        System.out.println("3. Asociar empleado a restaurante.");
        System.out.println("4. Busqueda de productos.");
        System.out.println("5. Busqueda de restaurante.");
        System.out.println("6. Busqueda de empleado por datos.");
        System.out.println("7. Busqueda de empleados por rango salarial.");
        System.out.println("8. Salir.");

        int opc;
        do {
            System.out.println("Opción:");
            opc = Integer.parseInt(sc.nextLine());
        } while (opc < 1 || opc > 8);

        return opc;
    }

    private static void tratarOpcion(int opc, ObjectContainer db) {
        switch (opc) {
            case 1 -> altaRestaurante(db);
            case 2 -> registrarProductos(db);
            case 3 -> asociarEmpleadoARestaurante(db);
            case 4 -> busquedaProductos(db);
            case 5 -> busquedaRestaurante(db);
            case 6 -> busquedaEmpleadoPorDato(db);
            case 7 -> busquedaEmpleadoRangoSalarial(db);
        }
    }

    private static void busquedaEmpleadoRangoSalarial(ObjectContainer db) {
        String nombreRest = solicitarString("Nombre restaurante: ");

        ObjectSet<Restaurante> restaurantes = db.queryByExample(new Restaurante(nombreRest));
        Restaurante restaurante = restaurantes.get(0);

        double rangoMin = solicitarDouble("Rango mínimo: ");
        double rangoMax = solicitarDouble("Rango máximo: ");

        ObjectSet<Empleado> empleados;
        if (restaurante != null) {
            empleados = db.query(new Predicate<>() {
                @Override
                public boolean match(Empleado empleado) {
                    return restaurante.getEmpleados().contains(empleado) && empleado.getNomina().getSueldo() >= rangoMin && empleado.getNomina().getSueldo() <= rangoMax;
                }
            }, (QueryComparator<Empleado>) (o1, o2) -> (-1) * Double.compare(o1.getNomina().getSueldo(), o2.getNomina().getSueldo()));

            if (empleados == null) {
                System.out.println("No se han encontrado registros");
            } else {
                for (Empleado emp : empleados) {
                    System.out.println(emp);
                }
            }
        } else {
            System.out.println("Restaurante no encontrado");
        }
    }

    private static void busquedaEmpleadoPorDato(ObjectContainer db) {
        String cadena = solicitarString("Busqueda empleado: ");
        ObjectSet<Empleado> empleados = db.query(new Predicate<>() {
            @Override
            public boolean match(Empleado emp) {
                return emp.getNombre().contains(cadena) || emp.getApellido1().contains(cadena) || emp.getApellido2().contains(cadena);
            }
        }, (QueryComparator<Empleado>) (emp1, emp2) -> (-1) * Double.compare(emp1.getNomina().getSueldo(), emp2.getNomina().getSueldo()));

        if (empleados.size() != 0) {
            for (Empleado emp : empleados) {
                System.out.println(emp);
            }
        } else System.out.println("No se ha encontrado ningún resultado.");
    }

    private static void busquedaRestaurante(ObjectContainer db) {
        String nombreRest = solicitarString("Nombre restaurante: ");
        ObjectSet<Restaurante> restaurantes = db.query(new Predicate<>() {
            @Override
            public boolean match(Restaurante rest) {
                return rest.getNombre().contains(nombreRest);
            }
        }, (QueryComparator<Restaurante>) (rest1, rest2) -> (-1) * Integer.compare(rest1.getEmpleados().size(), rest2.getEmpleados().size()));

        if (restaurantes.size() != 0) {
            for (Restaurante rest : restaurantes) {
                System.out.println(rest);
            }
        } else System.out.println("No se ha encontrado ningún registro.");

    }

    private static void busquedaProductos(ObjectContainer db) {
        String nombreRest = solicitarString("Nombre restaurante: ");

        ObjectSet<Restaurante> restaurantes = db.queryByExample(new Restaurante(nombreRest));
        Restaurante restaurante = restaurantes.get(0);

        String nombreProducto = solicitarString("Nombre producto: ");
        if (restaurante != null) {
            boolean hayRegistros = false;
            ArrayList<Producto> lisRest = restaurante.getProductos();
            lisRest.sort(Comparator.comparing(Producto::getNombre));
            for (Producto p : restaurante.getProductos()) {
                if (p.getNombre().contains(nombreProducto)) {
                    System.out.println(p);
                    hayRegistros = true;
                }
            }
            if (!hayRegistros) System.out.println("No hay ningún producto compatible con lo introducido.");
        }
    }

    private static void asociarEmpleadoARestaurante(ObjectContainer db) {
        String nombreRest = solicitarString("Nombre restaurante: ");

        if (comprobarRestaurante(db, nombreRest)) {
            ObjectSet<Restaurante> result = db.queryByExample(new Restaurante(nombreRest));
            Restaurante restaurante = result.get(0);
            String dniEmp = solicitarString("DNI: ");

            if (comprobarEmpleado(db, dniEmp)) {
                System.out.println("Este empleado ya tiene un empleo.");
            } else {
                Empleado empleado = new Empleado(
                        solicitarString("Nombre: "),
                        solicitarString("Primer apellido: "),
                        solicitarString("Segundo apellido: "),
                        dniEmp,
                        new Nomina(
                                solicitarString("IBAN: "),
                                solicitarDouble("Sueldo: ")
                        )
                );
                restaurante.addEmpleado(empleado);
                db.store(restaurante);
                db.store(empleado);
            }
        }
    }

    private static boolean comprobarEmpleado(ObjectContainer db, String dniEmp) {
        return db.queryByExample(new Empleado(dniEmp)).hasNext();
    }

    private static boolean comprobarRestaurante(ObjectContainer db, String nombreRest) {
        return db.queryByExample(new Restaurante(nombreRest)).hasNext();
    }

    private static void registrarProductos(ObjectContainer db) {
        String nombreRest = solicitarString("Nombre restaurante: ");
        ObjectSet<Restaurante> restaurantes = db.queryByExample(new Restaurante(nombreRest));
        if (comprobarRestaurante(db, nombreRest)) {
            do {
                Producto producto = null;
                try {
                    producto = new Producto(
                            solicitarString("Nombre producto: "),
                            solicitarDouble("Precio: "),
                            Categoria.valueOf(solicitarString("Categoría: ").toUpperCase()).toString()
                    );
                } catch (IllegalArgumentException e) {
                    System.out.println("Categoría no registrada.");
                }
                Restaurante restaurante = restaurantes.get(0);

                if (producto != null) {
                    if (restaurante.getProductos() != null) {
                        boolean existePto = false;
                        for (Producto p : restaurante.getProductos()) {
                            if (p.getNombre().equals(producto.getNombre())) {
                                existePto = true;
                                break;
                            }
                        }
                        if (existePto) {
                            System.out.println("Este producto ya está registrado en este restaurante");
                        }
                        restaurante.addProducto(producto);

                        db.store(producto);
                        db.store(restaurante);
                        db.commit();
                    }
                }
            } while (solicitarString("¿Desea introducir producto? (S/N)").equalsIgnoreCase("S"));
        }
    }

    private static void altaRestaurante(ObjectContainer db) {
        String nombre = solicitarString("Nombre restaurante: ");
        Restaurante restaurante = new Restaurante(nombre);
        ObjectSet<Restaurante> result = db.queryByExample(restaurante);

        if (result.size() == 0) {
            ArrayList<Empleado> empleados = registrarEmpleados(db);
            restaurante.addEmpleados(empleados);
            db.store(restaurante);
        } else System.out.println("Ya existe un restaurante con ese nombre.");
    }

    private static ArrayList<Empleado> registrarEmpleados(ObjectContainer db) {
        ArrayList<Empleado> empleados = new ArrayList<>();

        while (solicitarString("¿Desea introducir otro empleado? (S/N)").equalsIgnoreCase("S")) {
            Empleado empleado = new Empleado(
                    solicitarString("Nombre: "),
                    solicitarString("Primer apellido: "),
                    solicitarString("Segundo apellido: "),
                    solicitarString("DNI: "),
                    new Nomina(
                            solicitarString("IBAN: "),
                            solicitarDouble("Sueldo: ")
                    )
            );

            if (comprobarEmpleado(db, empleado))
                System.out.println("Error. DNI asignado a una persona que ya tiene trabajo.");
            else {
                empleados.add(empleado);
                db.store(empleado);
            }
        }
        return empleados;
    }

    private static boolean comprobarEmpleado(ObjectContainer db, Empleado empleado) {
        ObjectSet<Empleado> result = db.queryByExample(new Empleado(empleado.getDni()));
        return result.hasNext();
    }

    private static double solicitarDouble(String msg) {
        System.out.println(msg);
        return Double.parseDouble(sc.nextLine());
    }

    private static String solicitarString(String msg) {
        System.out.println(msg);
        return sc.nextLine();
    }
}
