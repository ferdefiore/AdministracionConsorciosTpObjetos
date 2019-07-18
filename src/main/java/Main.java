import clases.mvc.vista.MenuPrincipal;
import clases.utils.DAOmanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class Main {
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static void main(String[] args){

        DAOmanager.getDAOmanager().inicDB();
        new MenuPrincipal();
    }
}
