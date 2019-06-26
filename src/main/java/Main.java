import clases.DbManager;
import clases.mvc.controlador.MenuController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.DatabaseMetaData;

public class Main {
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        DbManager.getDbManager().inicDB();
        new MenuController();

    }
}
