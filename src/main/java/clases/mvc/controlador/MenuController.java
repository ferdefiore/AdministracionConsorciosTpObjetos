package clases.mvc.controlador;

import clases.mvc.modelo.MenuModel;
import clases.mvc.vista.MenuView;

public class MenuController {
    private MenuView menuView;
    private MenuModel menuModel;

    public MenuController() {
        this.menuView = new MenuView();
        this.menuModel = new MenuModel();
    }

}
