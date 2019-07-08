package clases.mvc.vista;

import clases.EventBusFactory;
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
    private JFileChooser jFileChooser;
    EventBus bus;

    public CerrarLiquidacionView(List<String> listaConsorcios) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Cierre de liquidacion");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocation(450,250);
        for (String nombre: listaConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        frame.setVisible(true);

        confirmarCierreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(generarArchivoDeTotalesCheckBox.isSelected()){
                    bus.post(new CerrarLiquidacion((String)comboConsorcios.getSelectedItem(),true));
                }else{
                    bus.post(new CerrarLiquidacion((String)comboConsorcios.getSelectedItem(),false));
                }
                bus.post("Termino cerrar liquidacion");
                frame.dispose();
            }
        });
    }

    public static class CerrarLiquidacion{
        public String nombreConsorcio;
        public boolean generarInforme;

        public CerrarLiquidacion(String nombreConsorcio, boolean generarInforme) {
            this.nombreConsorcio = nombreConsorcio;
            this.generarInforme = generarInforme;
        }
    }
}
