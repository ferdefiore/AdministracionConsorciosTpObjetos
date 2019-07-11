package clases.clasesRelacionales;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LiquidacionesGrupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany()
    @JoinColumn(name = "id_liquidacion_grupoPerteneciente")
    private List<Liquidacion> liquidaciones;

    public LiquidacionesGrupo() {
        this.liquidaciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Liquidacion> getLiquidaciones() {
        return liquidaciones;
    }

    public void setLiquidaciones(List<Liquidacion> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }

    public void agregarLiquidacion(Liquidacion lq) {
        liquidaciones.add(lq);
    }
}
