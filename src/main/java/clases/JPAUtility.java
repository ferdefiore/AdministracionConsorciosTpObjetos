package clases;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class JPAUtility {
    private static EntityManagerFactory emFactory = null;

    public static EntityManager getEntityManager(){
        if (emFactory == null){
            emFactory = Persistence.createEntityManagerFactory("Persistencia");
        }
        return emFactory.createEntityManager();
    }
}