package Tema3.Ejercicios.Ejercicio4;

public class Nota {

    private double nota1ev;
    private double nota2ev;
    private double nota3ev;
    private double notaJun;
    private double notaSep;

    public Nota() {
    }


    public double getNota1ev() {
        return nota1ev;
    }

    public void setNota1ev(double nota1ev) {
        this.nota1ev = nota1ev;
    }

    public double getNota2ev() {
        return nota2ev;
    }

    public void setNota2ev(double nota2ev) {
        this.nota2ev = nota2ev;
    }

    public double getNota3ev() {
        return nota3ev;
    }

    public void setNota3ev(double nota3ev) {
        this.nota3ev = nota3ev;
    }

    public double getNotaJun() {
        return notaJun;
    }

    public void setNotaJun(double notaJun) {
        this.notaJun = notaJun;
    }

    public double getNotaSep() {
        return notaSep;
    }

    public void setNotaSep(double notaSep) {
        this.notaSep = notaSep;
    }

    @Override
    public String toString() {
        return "\n\tNotas:" +
                "\n\t\tNota 1EV: " + nota1ev +
                "\n\t\tNota 2EV: " + nota2ev +
                "\n\t\tNota 3EV: " + nota3ev +
                "\n\t\tNota JUN: " + notaJun +
                "\n\t\tNota SEP: " + notaSep;
    }
}
