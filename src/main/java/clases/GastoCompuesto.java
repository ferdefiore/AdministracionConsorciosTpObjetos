package clases;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class GastoCompuesto extends Gasto {
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Gasto> gastos;

    public GastoCompuesto(int id, String concepto,List<Gasto> gastos) {
        super(id, concepto,0);
        float suma = 0f;
        for (int i = 0; i < gastos.size(); i++) {
            suma += gastos.get(i).getMonto();
        }
        this.monto = suma;
        this.gastos = gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    @Override
    public float getMonto() {
        float total = 0;
        for (Gasto g:gastos) {
            total+=g.getMonto();
        }
        return total;
    }

    public void agregarGasto(Gasto g){
        gastos.add(g);
    }
    public void eliminarGasto(Gasto g){
        //todo verificar como funciona el remove
        gastos.remove(g);
    }
}
