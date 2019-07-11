package clases.clasesRelacionales;

import javax.persistence.Entity;

@Entity
public class GastoSimple extends Gasto {
    public GastoSimple() {
    }

    public GastoSimple(String concepto, float monto) {
        super(concepto, monto);
    }

    @Override
    public float getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Gasto Simple: Monto Total: $" + this.getMonto() + '\t' + " Concepto: ' " + concepto + " '";
    }
}
