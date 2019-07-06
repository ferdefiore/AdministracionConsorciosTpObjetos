package clases;

import javax.persistence.*;

@Entity
public class UnidadFuncional {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String tipo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Propietario propietario;
    private double coeficiente;
    private String pisoNum;
    private double saldo;

    public UnidadFuncional() {
    }

    @Override
    public String toString() {
        return "IdUf: " +this.getId()+ '\t' +" PisoNum: " + this.getPisoNum() + '\t' +  " Saldo: " + this.getSaldo() +  '\t' + " Propietario: " + this.getPropietario().getNombreApellido() + "." ;
    }

    public UnidadFuncional(String tipo, Propietario propietario, double coeficiente, String pisoNum, double saldo) {
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
}
