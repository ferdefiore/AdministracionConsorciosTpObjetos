package clases;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Liquidacion {
    @Id
    private int id_liquidacion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_consorcio")

    private Consorcio consorcio;
    private YearMonth periodo;

    public float getGastoParcial() {
        return gastoParcial;
    }

    public void setGastoParcial(float gastoParcial) {
        this.gastoParcial = gastoParcial;
    }

    private float gastoParcial;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_liq_perteneciente")
    private List<Gasto> gastos;

    public Consorcio getConsorcio() {
        return consorcio;
    }

    public void setConsorcio(Consorcio consorcio) {
        this.consorcio = consorcio;
    }

    public int getId_liquidacion() {
        return id_liquidacion;
    }

    public void setId_liquidacion(int id_liquidacion) {
        this.id_liquidacion = id_liquidacion;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public YearMonth getPeriodo() {
        return periodo;
    }

    public void setPeriodo(YearMonth periodo) {
        this.periodo = periodo;
    }

    public void agregarGasto(Gasto g) {
        gastoParcial+= g.getMonto();
        gastos.add(g);
    }

    public void eliminarGasto(Gasto g) {
        gastoParcial-=  g.getMonto();
        gastos.remove(g);
    }

    public Liquidacion(int id_liquidacion, YearMonth periodo, float gastoParcial, Consorcio consorcio) {
        this.id_liquidacion = id_liquidacion;
        this.periodo = periodo;
        this.gastoParcial = gastoParcial;
        this.gastos = new ArrayList<>();
        this.consorcio = consorcio;
    }
}
