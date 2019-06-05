package clases;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.List;

@Entity
public class Consorcio {
    @Id
    private int id;
    private String nombre;
    private String cuit;
    private String direccion;
    private String ciudad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "id_liquidacionVigente")
    private Liquidacion liquidacionVigente;

    //todo esto queda horrible en cuanto a DB, hace una tercera tabla con id cons, id liq no pude hacer que uf tenga consorcio.
    //@ManyToMany() esto andaba pero agrega una tabla de consorcio,uf ... lo ideal seria q uf tenga un id consorcio
    //Bueno quedo arreglado como esta ahora, espero que ande
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_consorcio_dueño")
    private List<UnidadFuncional> unidadesFuncionales;

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

    public Consorcio() {
    }

    public Consorcio(int id, String nombre, String cuit, String direccion, String ciudad, Liquidacion liquidacionVigente, List<UnidadFuncional> unidadesFuncionales) {
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

    public Liquidacion cerrarLiquidacion() {
        //todo aca cierra la liquidacion ya me olvide que habiamos planteado
        /* Liquidacion vigente dame el total de los gastos, a cada unidad funcional que tengo le sumo total de gasto*%pago de cada uf
        * la liquidacion que tengo, la tengo que mandar a historicas y debo crear una nueva para el proximo mes.
        * */
        float gastoFinal = liquidacionVigente.getGastoParcial();
        for (UnidadFuncional uf:unidadesFuncionales) {
            uf.modificarSaldo(gastoFinal*uf.getCoeficiente());
        }
        //todo aumentar el mes en uno
        Liquidacion cerrada = liquidacionVigente;
        liquidacionVigente = new Liquidacion(cerrada.getId_liquidacion()+1,cerrada.getPeriodo().plusMonths(1),0,this);
        return cerrada;
    }

}
