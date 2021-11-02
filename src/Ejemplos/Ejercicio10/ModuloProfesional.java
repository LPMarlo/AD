package Ejemplos.Ejercicio10;

import java.io.Serializable;

public class ModuloProfesional implements Serializable {

    private int numHoras;
    private String nombreModulo;

    public ModuloProfesional(int numHoras, String nombreModulo) {
        this.numHoras = numHoras;
        this.nombreModulo = nombreModulo;
    }

    public int getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(int numHoras) {
        this.numHoras = numHoras;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    @Override
    public String toString() {
        return "ModuloProfesional{" +
                "numHoras=" + numHoras +
                ", nombreModulo='" + nombreModulo + '\'' +
                '}';
    }
}
