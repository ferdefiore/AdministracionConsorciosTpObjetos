package clases.utils;

import clases.clasesRelacionales.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
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
            LiquidacionesHistoricas lh = (LiquidacionesHistoricas) manager.createQuery("FROM LiquidacionesHistoricas").getSingleResult();
        } catch (NoResultException e) {
            manager.getTransaction().begin();
            LiquidacionesHistoricas liquidacionesHistoricas = new LiquidacionesHistoricas();
            manager.persist(liquidacionesHistoricas);
            manager.getTransaction().commit();
        }
    }

    public List<String> getListaNombresConsorcios() {
        EntityManager manager = JPAUtility.getEntityManager();
        List<String> nombres = new ArrayList<>();
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio").getResultList();
        for (Consorcio c : resultList) {
            nombres.add(c.getNombre());
        }
        return nombres;
    }

    private Integer getIdFromNombreConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        String queryGetIdFromNombre = "SELECT id FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        return (Integer) manager.createQuery(queryGetIdFromNombre).getSingleResult();
    }

    public List<Integer> getListaGastosCompuestos(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        String query = "SELECT id " +
                "FROM Gasto g JOIN Liquidacion l " +
                "ON g.id_liq_perteneciente = l.id_liquidacion " +
                "WHERE l.id_consorcio = " + getIdFromNombreConsorcio(nombreConsorcio) + " " +
                "AND g.dtype = 'GastoCompuesto' AND l.id_liquidacion =" + getIdLiquidacionVigenteConsorcio(nombreConsorcio);
        List<Integer> resultList = (List<Integer>) manager.createNativeQuery(query).getResultList();
        return resultList;
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

    private Integer getIdLiquidacionVigenteConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        String queryGetIdLiquidacionFromNombre = "SELECT liquidacionVigente FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        Liquidacion lq = (Liquidacion) manager.createQuery(queryGetIdLiquidacionFromNombre).getSingleResult();
        return lq.getId_liquidacion();
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
        EntityManager manager = JPAUtility.getEntityManager();
        GastoSimple gastoSimple = new GastoSimple(concepto, monto);
        Integer idConsorcio = DAOmanager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = manager.find(Consorcio.class, idConsorcio);
        manager.getTransaction().begin();
        consorcio.getLiquidacionVigente().agregarGasto(gastoSimple);
        manager.getTransaction().commit();
        System.out.println(gastoSimple.toString());
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = manager.find(Consorcio.class, idConsorcio);
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto, new ArrayList<Gasto>());
        manager.getTransaction().begin();
        consorcio.getLiquidacionVigente().agregarGasto(gastoCompuesto);
        manager.getTransaction().commit();
        System.out.println(gastoCompuesto.toString());
    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        GastoSimple gastoSimple = new GastoSimple(concepto,monto);
        Consorcio consorcio = manager.find(Consorcio.class, idConsorcio);
        manager.getTransaction().begin();
        consorcio.getLiquidacionVigente().agregarAGastoCompuesto(gastoSimple,idGastoSeleccionado);
        manager.persist(gastoSimple);
        manager.getTransaction().commit();
        System.out.println(gastoSimple.toString());
    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto, new ArrayList<Gasto>());
        Consorcio consorcio = manager.find(Consorcio.class, idConsorcio);
        manager.getTransaction().begin();
        consorcio.getLiquidacionVigente().agregarAGastoCompuesto(gastoCompuesto,idGastoSeleccionado);
        manager.persist(gastoCompuesto);
        manager.getTransaction().commit();
        System.out.println(gastoCompuesto.toString());
    }

    public List<UnidadFuncional> getListaUnidadesFuncionalesConsorcio(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio c = manager.find(Consorcio.class, idConsorcio);
        return c.getUnidadesFuncionales();
    }

    public void generarPago(Integer idUnidadFuncional, Double monto) {
        EntityManager manager = JPAUtility.getEntityManager();
        UnidadFuncional uf = (UnidadFuncional) manager.createQuery("FROM UnidadFuncional WHERE id =" + idUnidadFuncional).getSingleResult();
        Pago nuevoPago = new Pago(monto, uf);
        manager.getTransaction().begin();
        manager.persist(nuevoPago);
        uf.modificarSaldo(monto);
        manager.getTransaction().commit();
    }

    public void cerrarLiquidacion(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = DAOmanager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        manager.getTransaction().begin();
        Liquidacion liquidacionCerrada = consorcio.cerrarLiquidacion();
        AgregarHistorica(liquidacionCerrada, idConsorcio, manager);
        manager.getTransaction().commit();
    }

    public Liquidacion cerrarLiquidacionGenerarInforme(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = DAOmanager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        manager.getTransaction().begin();
        Liquidacion liquidacionCerrada = consorcio.cerrarLiquidacion();
        AgregarHistorica(liquidacionCerrada, idConsorcio, manager);
        manager.getTransaction().commit();
        return liquidacionCerrada;
    }

    private void AgregarHistorica(Liquidacion liquidacionCerrada, Integer idConsorcio, EntityManager manager) {
        LiquidacionesHistoricas lh = (LiquidacionesHistoricas) manager.createQuery("FROM LiquidacionesHistoricas").getSingleResult();
        lh.agregarHistorica(idConsorcio, liquidacionCerrada);
    }

    public Integer getNroConsorcioSiguiente() {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer ultimo = (Integer) manager.createNativeQuery("SELECT ID FROM CONSORCIO ORDER BY ID ASC LIMIT 1").getSingleResult();
        return ultimo + 1;
    }

    public void guardarConsorcio(Consorcio nuevoConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(nuevoConsorcio);
        manager.getTransaction().commit();
        System.out.println("guardado");
        System.out.println(nuevoConsorcio.toString());
    }

    public List<String> getListaDniNombrePropietario() {
        EntityManager manager = JPAUtility.getEntityManager();
        List<Propietario> propietarios = manager.createQuery("FROM Propietario").getResultList();
        List<String> ret = new ArrayList<>();
        for (Propietario p : propietarios) {
            ret.add(p.getDni() + " " + p.getNombreApellido());
        }
        return ret;
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

    public List<Liquidacion> getHistoricas(String nombreConsorcio) {
        EntityManager manager = JPAUtility.getEntityManager();
        Integer idConsorcio = this.getIdFromNombreConsorcio(nombreConsorcio);
        LiquidacionesHistoricas lh = (LiquidacionesHistoricas) manager.createQuery("FROM LiquidacionesHistoricas").getSingleResult();
        return lh.getHashLiquidaciones(idConsorcio);
    }

    public Liquidacion getLiquidacion(Integer idLiquidacion) {
        EntityManager manager = JPAUtility.getEntityManager();
        return manager.find(Liquidacion.class, idLiquidacion);
    }

    public List<String> getListaPropietarios() {
        EntityManager manager = JPAUtility.getEntityManager();
        List<Propietario> propietarios = manager.createQuery("FROM Propietario").getResultList();
        List<String> ret = new ArrayList<>();
        for (Propietario p : propietarios) {
            ret.add(p.toString());
        }
        return ret;
    }

    public List<UnidadFuncional> getListaUnidadesFuncionales() {
        EntityManager manager = JPAUtility.getEntityManager();
        return (List<UnidadFuncional>) manager.createQuery("FROM UnidadFuncional").getResultList();
    }

    public class Retorno{
        public String tipo;
        public Integer id;
        public String concepto;
        public double monto;

        public Retorno(String tipo, Integer id, String concepto, double monto) {
            this.tipo = tipo;
            this.id = id;
            this.concepto = concepto;
            this.monto = monto;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getConcepto() {
            return concepto;
        }

        public void setConcepto(String concepto) {
            this.concepto = concepto;
        }

        public double getMonto() {
            return monto;
        }

        @Override
        public String toString() {
            return "Retorno{" +
                    "tipo='" + tipo + '\'' +
                    ", id=" + id +
                    ", concepto='" + concepto + '\'' +
                    ", monto=" + monto +
                    '}';
        }

        public void setMonto(double monto) {
            this.monto = monto;
        }
    }
}
