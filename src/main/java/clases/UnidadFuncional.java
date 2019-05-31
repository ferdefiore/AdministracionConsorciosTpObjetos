package clases;

import java.util.Observable;
import java.util.Observer;

public class UnidadFuncional implements Observer {
    private int id;

    private enum tipo {
        Departamento,
        Cochera,
        Local
    }
    private Propietario propietario;
    private float coeficiente;
    private String pisoNum;
    private float saldo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public float getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(float coeficiente) {
        this.coeficiente = coeficiente;
    }

    public String getPisoNum() {
        return pisoNum;
    }

    public void setPisoNum(String pisoNum) {
        this.pisoNum = pisoNum;
    }

    public float getSaldo() {
        return saldo;
    }
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void modificarSaldo(float valor) {
        saldo += valor;
    }

    //todo completar observer
    @Override
    public void update(Observable o, Object arg) {

    }
}
