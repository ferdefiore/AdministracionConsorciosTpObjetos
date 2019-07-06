package clases.TestClases;

import clases.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class TestConsorcio {
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        manager = JPAUtility.getEntityManager();
        manager.getTransaction().begin();
        List<UnidadFuncional> ufs = new ArrayList<>();
        Propietario p1 = new Propietario("1","Fermin De Fiore","FFF","NECO","1555");
        Propietario p2 = new Propietario("2","Ignacio Fermin DF","FFF","NECO","1555");
        UnidadFuncional uf1= new UnidadFuncional("dpto",p1,0.5,"1A", 0f);
        UnidadFuncional uf2= new UnidadFuncional("dpto",p2,0.5,"1B", 0f);
        Liquidacion lq = new Liquidacion(YearMonth.of(2019,4),new ArrayList<>(),new Consorcio());
        Gasto g1 = new GastoSimple("pruebaGasto1",15F);
        Gasto g2 = new GastoSimple("pruebaGasto2",30F);
        List<Gasto> lg3= new ArrayList<>();
        Gasto g3 = new GastoCompuesto("pruebaCompuesto",lg3);
        Consorcio c1 = new Consorcio("Prueba","123","Abc","Neco",lq, ufs);
        //LiquidacionesHistoricas lqh = new LiquidacionesHistoricas(1);
        //lqh.agregarHistorica(lq.getId_liquidacion(),lq);
        lg3.add(g1);
        lg3.add(g2);
        ufs.add(uf1);
        ufs.add(uf2);
        lq.agregarGasto(g1);
        lq.agregarGasto(g2);
        lq.agregarGasto(g3);
        lq.setConsorcio(c1);
        manager.persist(c1);
        //manager.persist(lqh);
        List<Consorcio> prueba = (List<Consorcio>) manager.createQuery("FROM Consorcio").getResultList();
        for (int i = 0; i < prueba.size(); i++) {
            System.out.println(prueba.get(i).toString());
        }
        //c1.setNombre("Cambio de nombre correcto");
        prueba.clear();
        prueba = (List<Consorcio>) manager.createQuery("FROM Consorcio").getResultList();
        for (int i = 0; i < prueba.size(); i++) {
            System.out.println(prueba.get(i).toString());

            List<Gasto> gastosVigente=prueba.get(i).getLiquidacionVigente().getGastos();
            for (int j=0; j<gastosVigente.size(); j++){
                System.out.println(gastosVigente.get(j).getConcepto());
            }
        }
        manager.getTransaction().commit();
        List<Liquidacion> consulta = (List<Liquidacion>) manager.createQuery("FROM Liquidacion").getResultList();
        System.out.println(consulta.get(0).getPeriodo().toString());
        manager.close();
        JPAUtility.getEntityManager().close();
    }
}
