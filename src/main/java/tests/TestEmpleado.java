package tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestEmpleado {

    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Persistencia");
        manager = emf.createEntityManager();
        manager.getTransaction().begin();
        List<Empleado> empleados = (List<Empleado>) manager.createQuery("FROM Empleado").getResultList();
        System.out.println("En esta base hay "+empleados.size() + " empleados.");
        //todo crear trabajos armar listas y crear empleados, agregar el cascade para que fetchee trabajo en la base tmb
        Trabajo t1 = new Trabajo("t1");
        Trabajo t2 = new Trabajo("t2");
        Trabajo t3 = new Trabajo("t3");
        manager.persist(t1);
        manager.persist(t2);
        manager.persist(t3);
        manager.getTransaction().commit();
        System.out.println("llego a persistir los trabajos");

        manager.getTransaction().begin();
        Empleado em = new Empleado("pepe");
        em.getTrabajos().add(t1);
        em.getTrabajos().add(t2);
        manager.persist(em);
        manager.getTransaction().commit();
        System.out.println("llego a persistir em0");

        manager.getTransaction().begin();
        Empleado em1 = new Empleado("jose");
        em1.getTrabajos().add(t2);
        em1.getTrabajos().add(t3);
        manager.persist(em1);
        manager.getTransaction().commit();
        System.out.println("llego a persistir em1");

        empleados = (List<Empleado>) manager.createQuery("FROM Empleado").getResultList();
        System.out.println("En esta base hay "+empleados.size() + " empleados.");
        for (int i = 0; i < empleados.size(); i++) {
            System.out.println(empleados.get(i).toString());
        }

        List<Trabajo> trabajose1 = empleados.get(0).getTrabajos();
        for (int i = 0; i < trabajose1.size() ; i++) {
            System.out.println(trabajose1.get(i).getNombretrabajo());
        }



    }
}
