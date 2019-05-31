package tests;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Empleado {

    @Id
    @GeneratedValue
    private Long codigo;

    private String nombre;

    //@JoinColumn(name = "id_trabajo")
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Trabajo> trabajos = new ArrayList<>();

    public List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public Empleado(String nombre) {
        this.nombre = nombre;
    }

    public Empleado() {
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "tests.Empleado{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
