package clases;

import java.time.YearMonth;
import java.util.ArrayList;

public class Liquidacion {
    private int id_liquidacion;
    private YearMonth mes;
    private float gastoParcial;
    private ArrayList<Gasto> gastos;

    public int getId_liquidacion() {
        return id_liquidacion;
    }

    public void setId_liquidacion(int id_liquidacion) {
        this.id_liquidacion = id_liquidacion;
    }

    public YearMonth getMes() {
        return mes;
    }

    public void setMes(YearMonth mes) {
        this.mes = mes;
    }

    public void agregarGasto(Gasto g) {
        gastoParcial+= g.getMonto();
        gastos.add(g);
    }

    public void eliminarGasto(Gasto g) {
        gastoParcial-=  g.getMonto();
        gastos.remove(g);
    }

}
