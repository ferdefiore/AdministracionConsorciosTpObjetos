package clases;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Consorcio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NaturalId
    private String nombre;
    private String cuit;
    private String direccion;
    private String ciudad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "id_liquidacionVigente")
    private Liquidacion liquidacionVigente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_consorcio_dueño")
    private List<UnidadFuncional> unidadesFuncionales;

    public Consorcio() {
    }

    public Consorcio(String nombre, String cuit, String direccion, String ciudad, Liquidacion liquidacionVigente, List<UnidadFuncional> unidadesFuncionales) {
        //this.id = id;
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

    public void eliminarUnidadFuncional(UnidadFuncional uf){
        unidadesFuncionales.remove(uf);
    }

    public void agregarUnidadFuncional(List<UnidadFuncional> ufs){unidadesFuncionales.addAll(ufs);}

    public void eliminarUnidadFuncional(List<UnidadFuncional> ufs){
        unidadesFuncionales.removeAll(ufs);
    }

    public void agregarGasto(Gasto g) {
        this.liquidacionVigente.getGastos().add(g);
    }

    public List<UnidadFuncional> getUnidadesFuncionales(){
    return unidadesFuncionales;
    }

    @Override
    public String toString() {
        return "Consorcio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cuit='" + cuit + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }

    public Liquidacion cerrarLiquidacion() {
        /* Liquidacion vigente dame el total de los gastos, a cada unidad funcional que tengo le sumo total de gasto*%pago de cada uf
        * la liquidacion que tengo, la tengo que mandar a historicas y debo crear una nueva para el proximo mes.
        * */
        float gastoFinal = liquidacionVigente.getGastoParcial();
        for (UnidadFuncional uf:unidadesFuncionales) {
            //todo verificar q de 100% los coefa
            uf.modificarSaldo(gastoFinal*uf.getCoeficiente());
        }
        Liquidacion cerrada = liquidacionVigente;
        liquidacionVigente = new Liquidacion(cerrada.getPeriodo().plusMonths(1),new ArrayList<>(),this);
        return cerrada;
    }

}
