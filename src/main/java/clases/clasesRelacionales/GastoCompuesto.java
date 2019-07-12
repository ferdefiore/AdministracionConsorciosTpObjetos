package clases.clasesRelacionales;

import clases.utils.Constantes;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class GastoCompuesto extends Gasto {
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Gasto> gastos;

    public GastoCompuesto() {
    }

    public GastoCompuesto(String concepto, List<Gasto> gastos) {
        super(concepto, Constantes.ceroInteger);
        this.gastos = gastos;
    }

    @Override
    public float getMonto() {
        float total = Constantes.ceroFloat;
        for (Gasto g : gastos) {
            total += g.getMonto();
        }
        return total;
    }

    public void agregarGasto(Gasto g) {
        this.gastos.add(g);
    }

    public void eliminarGasto(Gasto g) {
        gastos.remove(g);
    }

    @Override
    public String toString() {
        String retorno = "Gasto compuesto, concepto: " + this.concepto + " Detalles: ";
        for (Gasto g: gastos) {
            retorno+= g.toString();
        }
        return retorno;
    }
}
