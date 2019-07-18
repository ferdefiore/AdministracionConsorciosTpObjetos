package clases.clasesRelacionales;

import javax.persistence.*;

@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double montoPagado;
    @ManyToOne()
    @JoinColumn(name = "id_unidadFuncional")
    private UnidadFuncional unidadFuncional;

    public Pago(Double montoPagado, UnidadFuncional unidadFuncional) {
        this.montoPagado = montoPagado;
        this.unidadFuncional = unidadFuncional;
    }

    public Pago() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
