import clases.DbManager;
import clases.TestClases.TestConsorcio;
import clases.mvc.controlador.AgregarGastoController;
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
            DbManager.getDbManager().inicDB();
        }
        if (hacer == 1) {
            File file = new File("C:\\Users\\Fermin\\test.mv.db");
            file.delete();
            String[] lista = {};
            TestConsorcio.main(lista);
            DbManager.getDbManager().inicDB();
        }
        new MenuView();
    }
}
