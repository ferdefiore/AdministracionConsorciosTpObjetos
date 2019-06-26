package clases;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Gasto {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    protected int id;
    protected String concepto;
    protected float monto;

    public int getId(){
        return id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public abstract float getMonto();
//todo chequear recomendacion del public, si lo cambio a private ver en gastosimple la recomendacion del porque no deberia estar hecho asi
    public Gasto(String concepto, float monto) {
        //this.id = id;
        this.concepto = concepto;
        this.monto = monto;
    }

    public Gasto() {
    }
}
