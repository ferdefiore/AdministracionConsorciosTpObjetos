package clases;

import org.hibernate.annotations.Target;

import javax.persistence.*;
import java.util.LinkedList;
@Entity
@Table(name = "TBL_CONSORCIOS")
public class Consorcio {
    @Id
    @Column(name = "ID_CONS")
    private int id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CUIT")
    private String cuit;

    @Column(name = "DIRECCION")
    private String direccion;

    @Column(name = "CIUDAD")
    private String ciudad;

    //todo estudiar este cascadeType.All, se puede usar orphan para borrado
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "id_liquidacion")
    private Liquidacion liquidacionVigente;

    @ManyToMany()
    private LinkedList<UnidadFuncional> unidadesFuncionales;

    public Consorcio() {
    }

    public Consorcio(int id, String nombre, String cuit, String direccion, String ciudad, Liquidacion liquidacionVigente, LinkedList<UnidadFuncional> unidadesFuncionales) {
        this.id = id;
        this.nombre = nombre;
        this.cuit = cuit;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.liquidacionVigente = liquidacionVigente;
        this.unidadesFuncionales = unidadesFuncionales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Liquidacion getLiquidacionVigente() {
        return liquidacionVigente;
    }

    public void setLiquidacionVigente(Liquidacion liquidacionVigente) {
        this.liquidacionVigente = liquidacionVigente;
    }

    public void agregarUnidadFuncional(UnidadFuncional uf){
        unidadesFuncionales.add(uf);
    }

    //todo verificar funcionamiento remove
    public void eliminarUnidadFuncional(UnidadFuncional uf){
        unidadesFuncionales.remove(uf);
    }

    public void cerrarLiquidacion() {
        //todo aca cierra la liquidacion ya me olvide que habiamos planteado
    }

}
