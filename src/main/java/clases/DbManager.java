package clases;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    public static DbManager dbManager;

    public void inicDB() {
        JPAUtility.getEntityManager();
    }

    public static DbManager getDbManager() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }

    public List<String> getListaNombresConsorcios() {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        List<String> nombres = new ArrayList<>();
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio").getResultList();
        for (Consorcio c : resultList) {
            nombres.add(c.getNombre());
        }
        return nombres;
    }

    public Integer getIdFromNombreConsorcio(String nombreConsorcio) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        String queryGetIdFromNombre = "SELECT id FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        return (Integer) manager.createQuery(queryGetIdFromNombre).getSingleResult();
    }

    public List<Integer> getListaGastos(String nombreConsorcio) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        String query = "SELECT id " +
                "FROM Gasto g JOIN Liquidacion l " +
                "ON g.id_liq_perteneciente = l.id_liquidacion " +
                "WHERE l.id_consorcio = " + getIdFromNombreConsorcio(nombreConsorcio) + " " +
                "AND g.dtype = 'GastoCompuesto' AND l.id_liquidacion ="+ getIdLiquidacionVigenteConsorcio(nombreConsorcio);
        List<Integer> resultList = (List<Integer>) manager.createNativeQuery(query).getResultList();
        return resultList;
    }

    public List<Gasto> getGastosLiquidacionVigente(String nombreConsorcio){
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        List<Gasto> retorno = consorcio.getLiquidacionVigente().getGastos();
        return retorno;
    }

    private Integer getIdLiquidacionVigenteConsorcio(String nombreConsorcio) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        String queryGetIdLiquidacionFromNombre = "SELECT liquidacionVigente FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        Liquidacion lq = (Liquidacion) manager.createQuery(queryGetIdLiquidacionFromNombre).getSingleResult();
        return lq.getId_liquidacion();
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        GastoSimple gastoSimple = new GastoSimple(concepto, monto);
        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio resultList = manager.find(Consorcio.class,idConsorcio);
        manager.getTransaction().begin();
        resultList.agregarGasto(gastoSimple);
        manager.getTransaction().commit();
        System.out.println(gastoSimple.toString());
    }
// todo se pueden cambiar las querys por find como en el agregarNuevoGasto
    public void agregarNuevoGasto(String nombreConsorcio, String concepto) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio result = (Consorcio) manager.createQuery("FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'").getSingleResult();
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto, new ArrayList<Gasto>());
        manager.getTransaction().begin();
        result.getLiquidacionVigente().agregarGasto(gastoCompuesto);
        manager.getTransaction().commit();
        System.out.println(gastoCompuesto.toString());
    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        GastoSimple gastoSimple = new GastoSimple(concepto, monto);
        Consorcio result = (Consorcio) manager.createQuery("FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'").getSingleResult();
        Boolean wh = true;
        int i = 0;
        Liquidacion r1 = result.getLiquidacionVigente();
        List<Gasto> g1 = r1.getGastos();
        while(wh && (i < g1.size())){
            Gasto g = result.getLiquidacionVigente().getGastos().get(i);
            if (g.getId() == idGastoSeleccionado) {
                wh = false;
                GastoCompuesto compuesto = (GastoCompuesto) g;
                manager.getTransaction().begin();
                compuesto.agregarGasto(gastoSimple);
                manager.getTransaction().commit();
            }
            i++;
        }
        System.out.println(gastoSimple.toString());
    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto, new ArrayList<Gasto>());
        Consorcio result = (Consorcio) manager.createQuery("FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'").getSingleResult();
        for (Gasto g : result.getLiquidacionVigente().getGastos()) {
            if (g.getId() == idGastoSeleccionado) {
                GastoCompuesto compuesto = (GastoCompuesto) g;
                manager.getTransaction().begin();
                compuesto.agregarGasto(gastoCompuesto);
                manager.getTransaction().commit();
            }
        }
        System.out.println(gastoCompuesto.toString());
    }

    public List<UnidadFuncional> getListaUnidadesFuncionalesConsorcio(String nombreConsorcio) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio c = manager.find(Consorcio.class,idConsorcio);
        return c.getUnidadesFuncionales();
    }

    public void generarPago(String nombreConsorcio, Integer idUnidadFuncional, Double monto) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        //Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        //todo no hace falta pasar el nombre del consorcio ya que los id de las liquidaciones son generados automaticos para todos los consorcios entonces un id solo pertence a un consorcio
        UnidadFuncional uf = (UnidadFuncional) manager.createQuery("FROM UnidadFuncional WHERE id =" + idUnidadFuncional).getSingleResult();
        Pago nuevoPago = new Pago(monto, uf);
        manager.getTransaction().begin();
        manager.persist(nuevoPago);
        uf.modificarSaldo(monto);
        manager.getTransaction().commit();
    }

    public void cerrarLiquidacion(String nombreConsorcio) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        manager.getTransaction().begin();
        consorcio.cerrarLiquidacion();
        manager.getTransaction().commit();
    }

    public Liquidacion cerrarLiquidacionGenerarInforme(String nombreConsorcio) {
        EntityManager manager;
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        manager.getTransaction().begin();
        Liquidacion ret = consorcio.cerrarLiquidacion();
        manager.getTransaction().commit();
        return ret;
    }
}
