package clases;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo liquidacioneshistoricas tendra que ser tratado como un singleton, va a guardar las historicas de todos los consorcios
@Entity
public class LiquidacionesHistoricas {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_liquidacion_wrapper ")
    //Liquidaciones grupo funciona tipo un wrapper, porque no me deja crear un hash con <clave,lista<entidad>>
    private Map<Integer, LiquidacionesGrupo> liquidaciones;

    public LiquidacionesHistoricas() {
        this.liquidaciones = new HashMap<>();
    }

    public void agregarAHistorica(int id, Liquidacion cerrada) {
        this.liquidaciones.get(id).agregarLiquidacion(cerrada);
    }

    public void agregarHistorica(Integer i, Liquidacion lq){
        if (liquidaciones.get(i)==null){
            LiquidacionesGrupo liquidacionesGrupo = new LiquidacionesGrupo();
            liquidacionesGrupo.getLiquidaciones().add(lq);
            liquidaciones.put(i,liquidacionesGrupo);
        }else {
            liquidaciones.get(i).getLiquidaciones().add(lq);
        }
    }

    public List<Liquidacion> getHashLiquidaciones(Integer i){
        if (null == liquidaciones.get(i)){
            return new ArrayList<>();
        }
        List<Liquidacion> ret = liquidaciones.get(i).getLiquidaciones();
        return ret;
    }
}
