package clases;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo liquidacioneshistoricas tendra que ser tratado como un singleton, va a guardar las historicas de todos los consorcios
@Entity
public class LiquidacionesHistoricas {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_liquidacion_wrapper ")
    //Liquidaciones grupo funciona tipo un wrapper, porque no me deja crear un hash con <clave,lista<entidad>>
    private Map<Integer, LiquidacionesGrupo> liquidaciones;

    public LiquidacionesHistoricas() {
        this.liquidaciones = new HashMap<>();
    }

    public void agregarHistorica(Integer i, Liquidacion lq){
        if (liquidaciones.get(i)==null){
            LiquidacionesGrupo liquidacionesGrupo = new LiquidacionesGrupo();//todo entre parentesis ponerle id automatico
            liquidacionesGrupo.getLiquidaciones().add(lq);
            liquidaciones.put(i,liquidacionesGrupo);
        }else {
            liquidaciones.get(i).getLiquidaciones().add(lq);
        }
    }

    public List<Liquidacion> getHashLiquidaciones(Integer i){
        return liquidaciones.get(i).getLiquidaciones();
    }
}
