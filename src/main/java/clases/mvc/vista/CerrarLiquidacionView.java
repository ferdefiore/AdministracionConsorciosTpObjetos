package clases.mvc.vista;

import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CerrarLiquidacionView {
    private JComboBox comboConsorcios;
    private JButton confirmarCierreButton;
    private JCheckBox generarArchivoDeTotalesCheckBox;
    private JPanel panel1;
    private JFrame frame;
    private EventBus bus;

    public CerrarLiquidacionView(List<String> listaConsorcios) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame(Constantes.tituloCerrarLiquidacionView);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocationRelativeTo(null);
        for (String nombre : listaConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        frame.setVisible(true);

        confirmarCierreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null == comboConsorcios.getSelectedItem()){
                    JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion + Constantes.comboVacio, Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else {
                    try {
                        if (generarArchivoDeTotalesCheckBox.isSelected()) {
                            bus.post(new CerrarLiquidacion((String) comboConsorcios.getSelectedItem(), true));
                        } else {
                            bus.post(new CerrarLiquidacion((String) comboConsorcios.getSelectedItem(), false));
                        }
                        bus.post(Constantes.terminarCerrarLiquidacion);
                        frame.dispose();
                    } catch (Exception exeption) {
                        JOptionPane.showMessageDialog(null, Constantes.mensajeExepcionValidacion + exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    public static class CerrarLiquidacion {
        public String nombreConsorcio;
        public boolean generarInforme;

        public CerrarLiquidacion(String nombreConsorcio, boolean generarInforme) {
            this.nombreConsorcio = nombreConsorcio;
            this.generarInforme = generarInforme;
        }
    }
}
