package Tema3.Ejercicios.Ejercicio4;

public class Asignatura {
    private String codAsig;
    private String nombre;
    private String tipo;

    public Asignatura(String codAsig, String nombre, String tipo) {
        this.codAsig = codAsig;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "\nCodigo Asignatura: " + codAsig +
                "\n\tNombre: " + nombre +
                "\n\tTipo: " + tipo;
    }
}
