package Tema3.Ejercicios.ActEv;

import java.util.ArrayList;

public class Restaurante {

    private String nombre;
    private ArrayList<Empleado> empleados;
    private ArrayList<Producto> productos;

    public Restaurante(String nombre, ArrayList<Empleado> empleados, ArrayList<Producto> productos) {
        this.nombre = nombre;
        this.empleados = empleados;
        this.productos = productos;
    }

    public Restaurante(String nombre) {
        this.nombre = nombre;
        this.productos = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }

    public void addEmpleados(ArrayList<Empleado> emps) {
        this.empleados.addAll(emps);
    }

    public void addProductos(ArrayList<Empleado> ps) {
        this.empleados.addAll(ps);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void addEmpleado(Empleado e) {
        this.empleados.add(e);
    }

    public void removeEmpleado(Empleado e) {
        this.empleados.remove(e);
    }

    public void addProducto(Producto p) {
        this.productos.add(p);
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "nombre='" + nombre + '\'' +
                ", empleados=" + empleados +
                ", productos=" + productos +
                '}';
    }
}
