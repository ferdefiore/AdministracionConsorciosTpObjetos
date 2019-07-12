package clases.mvc.vista;

import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class LiquidacionesHistoricasView {
    private EventBus bus;
    private JComboBox comboConsorcios;
    private JComboBox comboPeriodos;
    private JButton importarLiquidacionAArchivoButton;
    private JPanel panel1;
    private JFrame frame;


    public LiquidacionesHistoricasView(List<String> nombresConsorcios, List<String> listaHistoricas) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame(Constantes.tituloLiquidacionesHistoricasView);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocationRelativeTo(null);
        for (String nombreConsorcio : nombresConsorcios) {
            comboConsorcios.addItem(nombreConsorcio);
        }
        this.poblarLiquidacionesHistoricas(listaHistoricas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bus.post(Constantes.terminarLiquidacionesHistoricas);
            }
        });

        comboConsorcios.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                bus.post(new SolicitudPeriodos((String) comboConsorcios.getSelectedItem()));
            }
        });
        importarLiquidacionAArchivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    bus.post(new ImprimirLiquidacion((String) comboConsorcios.getSelectedItem(), (String) comboPeriodos.getSelectedItem()));
                }catch (Exception exeption){
                    JOptionPane.showMessageDialog(null, Constantes.mensajeExepcionValidacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void poblarLiquidacionesHistoricas(List<String> periodos) {
        comboPeriodos.removeAllItems();
        for (String p : periodos) {
            comboPeriodos.addItem(p);
        }
    }

    public class SolicitudPeriodos {
        public String nombreConsorcio;

        public SolicitudPeriodos(String nombreConsorcio) {
            this.nombreConsorcio = nombreConsorcio;
        }
    }

    public class ImprimirLiquidacion {
        public String nombreConsorcio;
        public String periodo;

        public ImprimirLiquidacion(String nombreConsorcio, String periodo) {
            this.nombreConsorcio = nombreConsorcio;
            this.periodo = periodo;
        }
    }
}
