package clases;

import javax.persistence.*;
import java.util.Observable;
import java.util.Observer;
@Entity
public class UnidadFuncional implements Observer {
    @Id
    private int id;
    private String tipo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Propietario propietario;
    private double coeficiente;
    private String pisoNum;
    private double saldo;

    public UnidadFuncional(int id, String tipo,Propietario propietario, double coeficiente, String pisoNum, double saldo) {
        this.id = id;
        this.tipo = tipo;
        this.propietario = propietario;
        this.coeficiente = coeficiente;
        this.pisoNum = pisoNum;
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

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

    public double getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(double coeficiente) {
        this.coeficiente = coeficiente;
    }

    public String getPisoNum() {
        return pisoNum;
    }

    public void setPisoNum(String pisoNum) {
        this.pisoNum = pisoNum;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void modificarSaldo(double valor) {
        saldo += valor;
    }

    //todo completar observer
    @Override
    public void update(Observable o, Object arg) {

    }
}
