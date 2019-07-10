import clases.DAOmanager;
import clases.mvc.vista.MenuView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.File;

public class Main {
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {

        Integer hacer = 1;

        if (hacer == 0) {
            DAOmanager.getDAOmanager().inicDB();
        }
        if (hacer == 1) {
            File file = new File("C:\\Users\\Fermin\\test.mv.db");
            file.delete();
            DAOmanager.getDAOmanager().inicDB();
        }
        new MenuView();
    }
}
