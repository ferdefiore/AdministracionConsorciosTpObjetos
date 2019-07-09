package clases.mvc.vista;

import clases.EventBusFactory;
import clases.mvc.controlador.*;
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
    private JButton informesDeLiquidacionesHistoricasButton;
    private JButton nuevoConsorcioButton;
    private JButton verLiquidacionVigenteButton;
    private JButton cerrarLiquidacionButton;
    private JButton nuevoGastoButton;
    private JButton nuevoPagoButton;
    private JButton verPropietariosButton;
    private JButton verSaldosButton;

    public MenuView(){
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Bienvenido");
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this.panel1);
        frame.setResizable(false);
        //frame.setLocation(450,150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        nuevoGastoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarGastoController();
            }
        });
        nuevoPagoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NuevoPagoController();
            }
        });
        cerrarLiquidacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CerrarLiquidacionController();
            }
        });
        verLiquidacionVigenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LiquidacionVigenteController();
            }
        });
        nuevoConsorcioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgregarDatosController();
            }
        });
        informesDeLiquidacionesHistoricasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LiquidacionesHistoricasController();
            }
        });
        verPropietariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PropietariosYSaldosController();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

}
