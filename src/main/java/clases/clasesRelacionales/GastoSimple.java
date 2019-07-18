package clases.clasesRelacionales;

import javax.persistence.Entity;

@Entity
public class GastoSimple extends Gasto {

    private float monto;

    public GastoSimple() {
    }

    public GastoSimple(String concepto, float monto) {
        setConcepto(concepto);
        this.monto = monto;
    }

    @Override
    public float getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Gasto Simple: Monto Total: $" + monto + '\t' + " Concepto: ' " + this.getConcepto() + " '";
    }
}
