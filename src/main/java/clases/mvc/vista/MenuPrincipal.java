package clases.mvc.vista;

import clases.mvc.controlador.*;
import clases.utils.Constantes;
import clases.utils.DAOmanager;
import clases.utils.EventBusFactory;
import clases.utils.JPAUtility;
import com.google.common.eventbus.EventBus;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MenuPrincipal extends Application {
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
    private JButton borrarBaseDeDatosButton;
    private JButton inicializarConCasosDeButton;

    public MenuPrincipal() throws IOException {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame(Constantes.tituloMenuView);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this.panel1);
        frame.setResizable(false);
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
        borrarBaseDeDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPAUtility.close();
                String localDir = System.getProperty("user.dir");
                File file = new File(localDir + Constantes.pathArchivoBaseDatos);
                file.delete();
                DAOmanager.getDAOmanager().inicDB();
            }
        });
        inicializarConCasosDeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPAUtility.close();
                String localDir = System.getProperty("user.dir");
                String pathOrigen = localDir + Constantes.nombreCarpetaContenedoraBaseDatosInicial;
                String fichero = Constantes.nombreBaseDatos;
                String pathDestino = localDir;
                File ficheroCopiar = new File(pathOrigen, fichero);
                File ficheroDestino = new File(pathDestino, fichero);
                if (ficheroCopiar.exists()) {
                    try {
                        Files.copy(Paths.get(ficheroCopiar.getAbsolutePath()), Paths.get(ficheroDestino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    System.out.println("El fichero "+fichero+" no existe en el directorio "+pathOrigen);
                }
                DAOmanager.getDAOmanager().inicDB();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

}
