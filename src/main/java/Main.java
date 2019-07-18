import clases.mvc.vista.MenuPrincipal;
import clases.utils.DAOmanager;

public class Main {

    public static void main(String[] args){
        DAOmanager.getDAOmanager().inicDB();
        new MenuPrincipal();
    }
}
