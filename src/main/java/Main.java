import clases.DbManager;
import clases.mvc.controlador.AgregarGastoController;
import clases.mvc.vista.MenuView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main {
    private static EntityManager manager;
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        DbManager.getDbManager().inicDB();
        new MenuView();

    }
}
