package clases;

import javax.persistence.*;

@Entity
public class Pago {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private long montoPagado;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_unidadFuncional")
    private UnidadFuncional unidadFuncional;

    public Pago(long montoPagado, UnidadFuncional unidadFuncional) {
        //this.id = id;
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

    public long getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(long montoPagado) {
        this.montoPagado = montoPagado;
    }

    public UnidadFuncional getUnidadFuncional() {
        return unidadFuncional;
    }

    public void setUnidadFuncional(UnidadFuncional unidadFuncional) {
        this.unidadFuncional = unidadFuncional;
    }
}
