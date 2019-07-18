package clases.clasesRelacionales;

import clases.utils.Constantes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GastoCompuesto extends Gasto {


    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gastoCompuesto_padre")
    private List<Gasto> gastos;

    public List<Gasto> getGastos() {
        return gastos;
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

    public GastoCompuesto(String concepto, List<Gasto> gastos) {
        setConcepto(concepto);
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

    @Override
    public String toString() {
        String retorno = "Gasto compuesto, concepto: " + this.getConcepto() + " Detalles: ";
        for (Gasto g: gastos) {
            retorno += g.toString();
        }
        return retorno;
    }
}
