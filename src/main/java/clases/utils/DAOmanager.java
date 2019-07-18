package clases.utils;

import clases.clasesRelacionales.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOmanager {

    private static DAOmanager DAOmanager;

    public static DAOmanager getDAOmanager() {
        if (DAOmanager == null) {
            DAOmanager = new DAOmanager();
        }
        return DAOmanager;
    }

    public void inicDB() {
        JPAUtility.getEntityManager();
    }

    public Consorcio getConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = DAOmanager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = manager.find(Consorcio.class, idConsorcio);
        return consorcio;
    }

    public Liquidacion getLiquidacion(Integer idLiquidacion) {
        EntityManager manager = JPAUtility.getEntityManager();
        Liquidacion liquidacion = manager.find(Liquidacion.class, idLiquidacion);
        return liquidacion;
    }

    public Propietario getPropietarioFromDni(String dniPropietario) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(Propietario.class, dniPropietario);
    }

    public LiquidacionesHistoricas getLiquidacionesHistoricas() {
        EntityManager manager = JPAUtility.getEntityManager();
        try{
            return (LiquidacionesHistoricas) manager.createQuery("FROM LiquidacionesHistoricas").getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public GastoCompuesto getGastoCompuesto(Integer idGastoSeleccionado) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(GastoCompuesto.class, idGastoSeleccionado);
    }

    public UnidadFuncional getUnidadFuncionalFromId(Integer idUnidadFuncional) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(UnidadFuncional.class, idUnidadFuncional);
    }

    public List<Propietario> getListaPropietarios() {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.createQuery("FROM Propietario").getResultList();
    }

    public List<UnidadFuncional> getListaUnidadesFuncionales() {
        EntityManager manager = JPAUtility.getEntityManager();
        return (List<UnidadFuncional>) manager.createQuery("FROM UnidadFuncional").getResultList();
    }

    public List<Gasto> getGastosLiquidacionVigente(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = DAOmanager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        //Liquidacion ret = this.eliminarRepetidos(consorcio.getLiquidacionVigente());
        Liquidacion ret = consorcio.getLiquidacionVigente();
        return ret.getGastos();
    }

    public List<String> getListaNombresConsorcios() {
        EntityManager manager = JPAUtility.getEntityManager();
        return (List<String>) manager.createQuery("SELECT nombre FROM Consorcio").getResultList();
    }

    public List<String> getListaDniNombrePropietario() {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.createNativeQuery("SELECT CONCAT_WS(' ',dni, nombreApellido) FROM Propietario").getResultList();
    }

    public List<UnidadFuncional> getListaUnidadesFuncionalesConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio c = manager.find(Consorcio.class, idConsorcio);
        return c.getUnidadesFuncionales();
    }

    private Integer getIdFromNombreConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        String queryGetIdFromNombre = "SELECT id FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        return (Integer) manager.createQuery(queryGetIdFromNombre).getSingleResult();
    }

    public void guardarConsorcio(Consorcio nuevoConsorcio) {
        this.persistirEnBase(nuevoConsorcio);
    }

    public void agregarPropietario(Propietario nuevoPropietario) {
        this.persistirEnBase(nuevoPropietario);
    }

    public void guardarLiquidacionHistorica(LiquidacionesHistoricas liquidacionesHistoricas) {
        this.persistirEnBase(liquidacionesHistoricas);
    }

    public void actualizarConsorcioYAgregarHistorica(Consorcio consorcio, LiquidacionesHistoricas liquidacionesHistoricas) {
        this.actualizarEnBase(consorcio);
        this.actualizarEnBase(liquidacionesHistoricas);
    }

    public void actualizarConsorcio(Consorcio consorcio) {
        this.actualizarEnBase(consorcio);
    }

    public void actualizarGasto(GastoCompuesto compuesto) {
        this.actualizarEnBase(compuesto);
    }

    public void guardarPagoYactualizarUnidadFuncional(Pago pago, UnidadFuncional uf) {
        this.persistirEnBase(pago);
        this.actualizarEnBase(uf);
    }


    private void actualizarEnBase(Object object) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(object);
        manager.getTransaction().commit();
    }

    private void persistirEnBase(Object object) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();
    }

}
