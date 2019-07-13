package clases.clasesRelacionales;

import clases.utils.Constantes;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Liquidacion {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_liquidacion;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_consorcio")
    private Consorcio consorcio;
    private YearMonth periodo;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_liq_perteneciente")
    private List<Gasto> gastos;

    //constructores
    public Liquidacion() {
    }

    public Liquidacion(YearMonth periodo, List<Gasto> gastos, Consorcio consorcio) {
        this.periodo = periodo;
        this.gastos = gastos;
        this.consorcio = consorcio;
    }

    //metodos
    public float getGastoParcial() {
        float gastoTotal = Constantes.ceroFloat;
        for (Gasto g : gastos) {
            gastoTotal += g.getMonto();
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

    public void agregarGasto(Gasto nuevo, Integer idGastoPadre){

        List<GastoCompuesto> temp = new ArrayList();
        for (Gasto g : gastos) {
            if (g instanceof GastoCompuesto)
                temp.addAll(((GastoCompuesto) g).getCompuestos());
        }
        for (GastoCompuesto g:temp) {
            if (g.getId() == idGastoPadre){
                g.agregarGastoACompuesto(nuevo);
                break;
            }
        }
    }
}
