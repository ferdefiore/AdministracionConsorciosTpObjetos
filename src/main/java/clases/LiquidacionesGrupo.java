package clases;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class LiquidacionesGrupo {
    @Id
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_liquidacion_grupoPerteneciente")
    private List<Liquidacion> liquidaciones;

    public LiquidacionesGrupo(int id) {
        this.id = id;
        this.liquidaciones = new ArrayList<>();
    }

    public LiquidacionesGrupo() {
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
