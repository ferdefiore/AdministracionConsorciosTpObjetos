package clases.mvc.vista;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends Application {
    JFrame frame;
    private JPanel panel1;
    private JButton verLiquidacionesHistoricasButton;
    private JButton nuevoConsorcioButton;
    private JButton verLiquidacionVigenteButton;
    private JButton cerrarLiquidacionButton;
    private JButton nuevoGastoButton;
    private JButton nuevoPagoButton;
    private JButton modificarDatosButton;

    public MenuView(){
        frame = new JFrame("Bienvenido");
        frame.setSize(300,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this.panel1);
        frame.setResizable(false);
        nuevoGastoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarGasto();
            }
        });
        frame.setVisible(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

    public void setData(MenuView data) {
    }

    public void getData(MenuView data) {
    }

    public boolean isModified(MenuView data) {
        return false;
    }
}
