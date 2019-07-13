package clases.utils;

import clases.clasesRelacionales.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DAOmanager {

    private static DAOmanager DAOmanager;

    //singleton
    public static DAOmanager getDAOmanager() {
        if (DAOmanager == null) {
            DAOmanager = new DAOmanager();
        }
        return DAOmanager;
    }

    public void inicDB() {
        EntityManager manager = JPAUtility.getEntityManager();
        try {
            LiquidacionesHistoricas lh = (LiquidacionesHistoricas) manager.createQuery("FROM LiquidacionesHistoricas").getSingleResult(); //todo quiza se pueda situar en otro lado
        } catch (NoResultException e) {
            manager.getTransaction().begin();
            LiquidacionesHistoricas liquidacionesHistoricas = new LiquidacionesHistoricas();
            manager.persist(liquidacionesHistoricas);
            manager.getTransaction().commit();
        }
    }

    public List<String> getListaNombresConsorcios() {
        EntityManager manager = JPAUtility.getEntityManager();
        return (List<String>) manager.createQuery("SELECT nombre FROM Consorcio").getResultList();
    }

    private Integer getIdFromNombreConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        String queryGetIdFromNombre = "SELECT id FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        return (Integer) manager.createQuery(queryGetIdFromNombre).getSingleResult();
    }

    public List<Gasto> getGastosLiquidacionVigente(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = DAOmanager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        List<Gasto> retorno = consorcio.getLiquidacionVigente().getGastos();
        Set<Gasto> set = new HashSet<>();
        set.addAll(retorno);
        retorno.clear();
        retorno.addAll(set);
        return retorno;
    }

    public List<UnidadFuncional> getListaUnidadesFuncionalesConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio c = manager.find(Consorcio.class, idConsorcio);
        return c.getUnidadesFuncionales();
    }

    public Consorcio getConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = DAOmanager.getIdFromNombreConsorcio(nombreConsorcio);
        return manager.find(Consorcio.class, idConsorcio);
    }

    public void guardarConsorcio(Consorcio nuevoConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(nuevoConsorcio);
        manager.getTransaction().commit();
    }

    public List<String> getListaDniNombrePropietario() {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.createNativeQuery("SELECT CONCAT_WS(' ',dni, nombreApellido) FROM Propietario").getResultList();
    }

    public Propietario getPropietarioFromDni(String dniPropietario) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(Propietario.class, dniPropietario);
    }

    public void agregarUnidadFuncional(String nombreConsorcioPerteneciente, UnidadFuncional ufNueva) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = this.getIdFromNombreConsorcio(nombreConsorcioPerteneciente);
        manager.getTransaction().begin();
        manager.find(Consorcio.class, idConsorcio).agregarUnidadFuncional(ufNueva);
        manager.getTransaction().commit();
    }

    public void agregarPropietario(Propietario nuevoPropietario) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(nuevoPropietario);
        manager.getTransaction().commit();
    }

    public LiquidacionesHistoricas getLiquidacionesHistoricas() {
        EntityManager manager = JPAUtility.getEntityManager();
        return (LiquidacionesHistoricas) manager.createQuery("FROM LiquidacionesHistoricas").getSingleResult();
    }

    public Liquidacion getLiquidacion(Integer idLiquidacion) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(Liquidacion.class, idLiquidacion);
    }

    public List<Propietario> getListaPropietarios() {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.createQuery("FROM Propietario").getResultList();
    }

    public List<UnidadFuncional> getListaUnidadesFuncionales() {
        EntityManager manager = JPAUtility.getEntityManager();
        return (List<UnidadFuncional>) manager.createQuery("FROM UnidadFuncional").getResultList();
    }

    public void actualizarConsorcioYAgregarHistorica(Consorcio consorcio, LiquidacionesHistoricas liquidacionesHistoricas) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(consorcio);
        manager.merge(liquidacionesHistoricas);
        manager.getTransaction().commit();
    }

    public void actualizarConsorcio(Consorcio consorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(consorcio);
        manager.getTransaction().commit();
    }

    public GastoCompuesto getGastoCompuesto(Integer idGastoSeleccionado) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(GastoCompuesto.class, idGastoSeleccionado);

    }
//todo se puede hacer un actualizer con objetc supongo para no tener tantos pero no difiere mucho
    public void actualizarGasto(GastoCompuesto compuesto) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(compuesto);
        manager.getTransaction().commit();
    }

    public UnidadFuncional getUnidadFuncionalFromId(Integer idUnidadFuncional) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(UnidadFuncional.class,idUnidadFuncional);
    }

    public void guardarPagoYactualizarUnidadFuncional(Pago pago,UnidadFuncional uf) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(pago);
        manager.merge(uf);
        manager.getTransaction().commit();
    }
}
