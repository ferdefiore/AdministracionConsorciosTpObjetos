package clases.clasesRelacionales;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance
public abstract class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String concepto;

    public int getId() {
        return id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto){
        this.concepto = concepto;
    }

    public abstract float getMonto();

    public Gasto(String concepto) {
        this.concepto = concepto;
    }

    public Gasto() {
    }
    public List<GastoCompuesto> devolverCompuestos(){
        return new ArrayList<>();
    }

}
