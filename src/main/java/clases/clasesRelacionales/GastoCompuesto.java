package clases.clasesRelacionales;

import clases.utils.Constantes;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GastoCompuesto extends Gasto {
    public List<Gasto> getGastos() {
        return gastos;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Gasto> gastos;

    public List<GastoCompuesto> getCompuestos() {
        List<GastoCompuesto> temp = new ArrayList();
        temp.add(this);
        for (Gasto g : gastos) {
            if (g instanceof GastoCompuesto)
            temp.addAll(((GastoCompuesto) g).getCompuestos());
        }
        return temp;
    }

    @Override
    public List<GastoCompuesto> devolverCompuestos() {
        List<GastoCompuesto> temp = new ArrayList<>();
        temp.add(this);
        for (Gasto g: gastos){
            temp.addAll(g.devolverCompuestos());
        }
        return temp;
    }

    public GastoCompuesto() {
    }

    @Override
    public List<String> pseudoToString() {
        List<String> ret = new ArrayList<>();
        for (Gasto g: gastos) {
            ret.addAll(g.pseudoToString());
        }
        return ret;
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

    public void agregarGastoACompuesto(Gasto g) {
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
