package clases;

import clases.TestClases.TestConsorcio;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    public static DbManager dbManager;
    private static EntityManager manager;
    //private static EntityManagerFactory emf;

    private DbManager() {
        //emf = Persistence.createEntityManagerFactory("Persistencia");
        //manager = JPAUtility.getEntityManager();
    }

    public void inicDB() {
        //manager = JPAUtility.getEntityManager();
    }

    public static DbManager getDbManager() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }

    public List<String> getListaNombresConsorcios() {
        manager = JPAUtility.getEntityManager();
        List<String> nombres = new ArrayList<>();
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio").getResultList();
        for (Consorcio c : resultList) {
            nombres.add(c.getNombre());
        }
        return nombres;
    }

    private Integer getIdFromNombreConsorcio(String nombreConsorcio) {
        manager = JPAUtility.getEntityManager();
        String queryGetIdFromNombre = "SELECT id FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        return (Integer) manager.createQuery(queryGetIdFromNombre).getSingleResult();
    }

    public List<Integer> getListaGastos(String nombreConsorcio) {
        manager = JPAUtility.getEntityManager();
        String query = "SELECT id " +
                "FROM Gasto g JOIN Liquidacion l " +
                "ON g.id_liq_perteneciente = l.id_liquidacion " +
                "WHERE l.id_consorcio = " + getIdFromNombreConsorcio(nombreConsorcio) + " " +
                "AND g.dtype = 'GastoCompuesto' AND l.id_liquidacion ="+ getIdLiquidacionVigenteConsorcio(nombreConsorcio);
        List<Integer> resultList = (List<Integer>) manager.createNativeQuery(query).getResultList();
        return resultList;
    }

    private Integer getIdLiquidacionVigenteConsorcio(String nombreConsorcio) {
        manager = JPAUtility.getEntityManager();
        String queryGetIdLiquidacionFromNombre = "SELECT liquidacionVigente FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'";
        Liquidacion lq = (Liquidacion) manager.createQuery(queryGetIdLiquidacionFromNombre).getSingleResult();
        return lq.getId_liquidacion();
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        //Consorcio resultList = (Consorcio) manager.createQuery("FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'").getSingleResult();
        //List<Consorcio> res = (List<Consorcio>) manager.createQuery("FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'").getResultList();
        manager = JPAUtility.getEntityManager();
        Consorcio resultList = manager.find(Consorcio.class,idConsorcio);
        GastoSimple gastoSimple = new GastoSimple(concepto, monto);
        //manager.getTransaction().begin();
        resultList.agregarGasto(gastoSimple);
        manager.merge(resultList);
        //manager.getTransaction().commit();
        System.out.println(gastoSimple.toString());
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto) {

        manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();

        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio result = (Consorcio) manager.createQuery("FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'").getSingleResult();
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto, new ArrayList<Gasto>());
        result.getLiquidacionVigente().agregarGasto(gastoCompuesto);
        manager.persist(gastoCompuesto);

        manager.getTransaction().commit();
        System.out.println(gastoCompuesto.toString());

    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {

        manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();

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
                compuesto.agregarGasto(gastoSimple);
            }
            i++;
        }
        manager.persist(gastoSimple);

        manager.getTransaction().commit();
        System.out.println(gastoSimple.toString());

    }

    public void agregarAGasto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto) {
        manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();

        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto, new ArrayList<Gasto>());
        Consorcio result = (Consorcio) manager.createQuery("FROM Consorcio WHERE nombre = '" + nombreConsorcio + "'").getSingleResult();
        for (Gasto g : result.getLiquidacionVigente().getGastos()) {
            if (g.getId() == idGastoSeleccionado) {
                GastoCompuesto compuesto = (GastoCompuesto) g;
                compuesto.agregarGasto(gastoCompuesto);
            }
        }
        manager.persist(gastoCompuesto);

        manager.getTransaction().commit();
        System.out.println(gastoCompuesto.toString());

    }



    public List<Integer> getListaUnidadesFuncionalesConsorcio(String nombreConsorcio) {
        manager = JPAUtility.getEntityManager();

        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        List<Integer> idUnidadesFuncionales = (List<Integer>) manager.createNativeQuery("SELECT id FROM UNIDADFUNCIONAL WHERE ID_CONSORCIO_DUEÑO = " + idConsorcio).getResultList();



        return idUnidadesFuncionales;
    }

    public void generarPago(String nombreConsorcio, Integer idUnidadFuncional, Double monto) {
        manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();

        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        //todo no hace falta pasar el nombre del consorcio ya que los id de las liquidaciones son generados automaticos para todos los consorcios entonces un id solo pertence a un consorcio
        UnidadFuncional uf = (UnidadFuncional) manager.createQuery("FROM UnidadFuncional WHERE id =" + idUnidadFuncional).getSingleResult();
        Pago nuevoPago = new Pago(monto, uf);
        manager.persist(nuevoPago);
        uf.modificarSaldo(monto);

        manager.getTransaction().commit();



    }

    public void cerrarLiquidacion(String nombreConsorcio) {
        manager = JPAUtility.getEntityManager();

        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        consorcio.cerrarLiquidacion();
        manager.persist(consorcio);


    }

    public Liquidacion cerrarLiquidacionGenerarInforme(String nombreConsorcio) {
        manager = JPAUtility.getEntityManager();

        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        Liquidacion ret = consorcio.cerrarLiquidacion();


        return ret;
    }

    public List<Gasto> getGastosLiquidacionVigente(String nombreConsorcio){
        manager = JPAUtility.getEntityManager();
        Integer idConsorcio = dbManager.getIdFromNombreConsorcio(nombreConsorcio);
        Consorcio consorcio = (Consorcio) manager.createQuery("FROM Consorcio WHERE id = " + idConsorcio).getSingleResult();
        List<Gasto> retorno = consorcio.getLiquidacionVigente().getGastos();

        return retorno;
    }
}
