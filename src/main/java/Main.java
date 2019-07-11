import clases.utils.DAOmanager;
import clases.mvc.vista.MenuPrincipal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.IOException;

public class Main {
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static void main(String[] args) throws IOException {
        DAOmanager.getDAOmanager().inicDB();
        new MenuPrincipal();
    }
}
