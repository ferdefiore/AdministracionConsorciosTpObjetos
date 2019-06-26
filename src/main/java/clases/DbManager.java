package clases;

import clases.TestClases.TestConsorcio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

//singleton
public class DbManager {

    public static DbManager dbManager;
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    private DbManager(){

    }

    public void inicDB(){
        emf = Persistence.createEntityManagerFactory("Persistencia");
        manager = emf.createEntityManager();
        String[] lista = {};
        TestConsorcio.main(lista);
    }

    public static DbManager getDbManager() {
        if (dbManager==null){
            dbManager = new DbManager();
        }
        return dbManager;
    }

    public List<String> getListaNombresConsorcios(){
        List<String> nombres = new ArrayList<>();
        manager.getTransaction().begin();
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio").getResultList();
        for (Consorcio c:resultList) {
        nombres.add(c.getNombre());
        }
        manager.getTransaction().commit();
        return nombres;
    }

    public Integer getIdFromNombreConsorcio(String nombreConsorcio){
        String queryGetIdFromNombre = "SELECT id FROM Consorcio WHERE nombre = '"+ nombreConsorcio +"'";
        List<Integer> idConsorcioDesdeNombre = (List<Integer>) manager.createQuery(queryGetIdFromNombre).getResultList();
        return idConsorcioDesdeNombre.get(0);
    }

    public List<Integer> getListaGastos(String nombreConsorcio) {
        manager.getTransaction().begin();
        String query =  "SELECT id " +
                        "FROM Gasto g JOIN Liquidacion l " +
                        "ON g.id_liq_perteneciente = l.id_liquidacion " +
                        "WHERE l.id_consorcio = " + getIdFromNombreConsorcio(nombreConsorcio) + " "+
                        "AND g.dtype = 'GastoCompuesto'";
        List<Integer> resultList = (List<Integer>) manager.createNativeQuery(query).getResultList();
        manager.getTransaction().commit();
        return resultList;
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio WHERE nombre = '"+ nombreConsorcio +"'").getResultList();
        GastoSimple gastoSimple = new GastoSimple(concepto,monto);
        resultList.get(0).getLiquidacionVigente().agregarGasto(gastoSimple);
        manager.persist(gastoSimple);
    }

    public void agregarNuevoGasto(String nombreConsorcio, String concepto) {
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio WHERE nombre = '"+ nombreConsorcio +"'").getResultList();
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto,new ArrayList<Gasto>());
        resultList.get(0).getLiquidacionVigente().agregarGasto(gastoCompuesto);
        manager.persist(gastoCompuesto);
    }

    public void agregarAGastoSimple(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        GastoSimple gastoSimple = new GastoSimple(concepto,monto);
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio WHERE nombre = '"+ nombreConsorcio +"'").getResultList();
        for (Gasto g: resultList.get(0).getLiquidacionVigente().getGastos()) {
            if (g.getId() == idGastoSeleccionado){
            GastoCompuesto compuesto = (GastoCompuesto) g;
            compuesto.agregarGasto(gastoSimple);
            }
        }
        manager.persist(gastoSimple);
    }

    public void agregarAGastoCompuesto(String nombreConsorcio, Integer idGastoSeleccionado, String concepto, Float monto) {
        Integer idConsorcio = getIdFromNombreConsorcio(nombreConsorcio);
        GastoCompuesto gastoCompuesto = new GastoCompuesto(concepto,new ArrayList<Gasto>());
        List<Consorcio> resultList = (List<Consorcio>) manager.createQuery("FROM Consorcio WHERE nombre = '"+ nombreConsorcio +"'").getResultList();
        for (Gasto g: resultList.get(0).getLiquidacionVigente().getGastos()) {
            if (g.getId() == idGastoSeleccionado){
                GastoCompuesto compuesto = (GastoCompuesto) g;
                compuesto.agregarGasto(gastoCompuesto);
            }
        }
        manager.persist(gastoCompuesto);
    }
}
