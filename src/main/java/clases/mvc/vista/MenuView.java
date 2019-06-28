package clases.mvc.vista;

import clases.EventBusFactory;
import clases.mvc.controlador.AgregarGastoController;
import clases.mvc.controlador.NuevoPagoController;
import com.google.common.eventbus.EventBus;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends Application {
    EventBus bus;
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
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Bienvenido");
        frame.setSize(300,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this.panel1);
        frame.setResizable(false);
        nuevoGastoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarGastoController();
            }
        });
        frame.setVisible(true);
        nuevoPagoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NuevoPagoController();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

}
