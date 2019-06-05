package clases;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class LiquidacionesHistoricas {
    @Id
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_liquidacion_wrapper ")
    //Liquidaciones grupo funciona tipo un wrapper, porque no me deja crear un hash con <clave,lista<entidad>>
    private Map<Integer, LiquidacionesGrupo> liquidaciones;

    public LiquidacionesHistoricas(int id) {
        this.id = id;
        this.liquidaciones = new HashMap<>();
    }

    public void agregarHistorica(Integer i, Liquidacion lq){
        //liquidaciones.get(i).getLiquidaciones().add(lq);
        LiquidacionesGrupo lg = new LiquidacionesGrupo(this.id);
        lg.getLiquidaciones().add(lq);
        //todo aca no tendria que poner lg, sino que tendria que sumar los elemnetos que haya... cambio a futuro
        liquidaciones.put(i,lg);
    }

    public List<Liquidacion> getHashLiquidaciones(Integer i){
        return liquidaciones.get(i).getLiquidaciones();
    }
}
