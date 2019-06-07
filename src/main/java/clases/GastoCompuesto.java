package clases;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class GastoCompuesto extends Gasto {
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Gasto> gastos;

    public GastoCompuesto(){    }

    public GastoCompuesto(int id, String concepto,List<Gasto> gastos) {
        super(id, concepto,0);
        this.gastos = gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
        //todo, se puede sumar directamente cuando la asigno asi no tengo que hacerlo cada vez que lo piden
        //pero para que quede claro que reimplementa getGasto de otra manera, lo dejo q haga mas laburo. Borraria lo de abajo.
/*        float suma = 0f;
        for (Gasto g:gastos) {
            suma+= g.getMonto();
        }
        this.monto = suma;*/
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

    //todo estos nose si estan de mas, porque se puede hacer directamente con el getGastos.add (desde punto de vista objetos creo q da igual)
    public void agregarGasto(Gasto g){
        gastos.add(g);
    }
    public void eliminarGasto(Gasto g){ gastos.remove(g);}
}
