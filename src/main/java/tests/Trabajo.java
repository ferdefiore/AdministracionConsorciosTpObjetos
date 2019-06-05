package tests;

import javax.persistence.*;

@Entity
@Table
public class Trabajo {
    @Id
    @GeneratedValue
    private Long id_trabajo;

    private String nombretrabajo;

    public Trabajo(String nombretrabajo) {
        this.nombretrabajo = nombretrabajo;
    }

    public Trabajo() {
    }

    public Long getId_trabajo() {
        return id_trabajo;
    }

    public void setId_trabajo(Long id_trabajo) {
        this.id_trabajo = id_trabajo;
    }

    public String getNombretrabajo() {
        return nombretrabajo;
    }

    public void setNombretrabajo(String nombretrabajo) {
        this.nombretrabajo = nombretrabajo;
    }
}
