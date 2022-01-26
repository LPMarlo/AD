package Tema3.Ejercicios.Ejercicio1;

public class Cuenta {

    private int numCuenta;
    private double saldo;

    public Cuenta(int numCuenta, double saldo){
        this.numCuenta = numCuenta;
        this.saldo = saldo;
    }

    public int getNumCuenta(){
        return numCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "[" +
                "numCuenta=" + numCuenta +
                ", saldo=" + saldo +
                ']';
    }
}
