package clases.clasesRelacionales;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GastoSimple extends Gasto {
    public GastoSimple() {
    }

    @Override
    public List<String> pseudoToString() {
        List<String> ret = new ArrayList<>();
        ret.add(this.getId() + " " + this.getConcepto());
        return ret;
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
