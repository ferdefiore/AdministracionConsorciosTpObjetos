package clases;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.List;

@Entity
public class Liquidacion {
    //atributos
    @Id
    private int id_liquidacion;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_consorcio")
    private Consorcio consorcio;
    private YearMonth periodo;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_liq_perteneciente")
    private List<Gasto> gastos;

    //constructores
    public Liquidacion(){    }

    public Liquidacion(int id_liquidacion, YearMonth periodo, List<Gasto> gastos, Consorcio consorcio) {
        this.id_liquidacion = id_liquidacion;
        this.periodo = periodo;
        this.gastos = gastos;
        this.consorcio = consorcio;
    }

    //metodos
    public float getGastoParcial() {
        float gastoTotal=0;
        for (Gasto g: gastos) {
            gastoTotal+=g.getMonto();
        }
        return gastoTotal;
    }

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
        this.gastos.add(g);
    }
    public void agregarGasto(List<Gasto> gastos){
        this.gastos.addAll(gastos);
    }
    public void eliminarGasto(Gasto g) {
        gastos.remove(g);
    }

}
