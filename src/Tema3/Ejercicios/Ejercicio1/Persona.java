package Tema3.Ejercicios.Ejercicio1;

public class Persona {

    private String nombre;
    private int edad;
    private String dni;
    private Cuenta miCuenta;

    public Persona(String nombre, int edad, String dni, Cuenta miCuenta) {
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
        this.miCuenta = miCuenta;
    }

    public Persona() {

    }

    public void setSaldo(Double saldo) {
        this.miCuenta.setSaldo(saldo);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getNumCuenta() {
        return miCuenta.getNumCuenta();
    }

    @Override
    public String toString() {
        if (miCuenta != null) {
            return "Persona [nombre=" + nombre + ", edad=" + edad + ", dni=" + dni + ", " + miCuenta + "]";
        } else {
            return "Persona [nombre=" + nombre + ", edad=" + edad + ", dni=" + dni + "]";
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persona other = (Persona) obj;
        if (nombre == null) {
            return other.nombre == null;
        } else return nombre.equals(other.nombre);
    }

    public void setCuenta(Cuenta cuenta) {
        this.miCuenta = cuenta;
    }
}