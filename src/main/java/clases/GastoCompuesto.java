package clases;

public class GastoCompuesto extends Gasto {
    private java.util.ArrayList<Gasto> gastos;

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
