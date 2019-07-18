package clases.mvc.vista;

import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class NuevoPagoView {
    private EventBus bus;
    private JFrame frame;
    private JComboBox comboConsorcios;
    private JComboBox comboUnidadesFuncionales;
    private JTextField textMonto;
    private JButton guardarPagoButton;
    private JPanel panel1;

    public NuevoPagoView(List<String> nombresConsorcios, List<String> listaUnidadesFuncionales) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame(Constantes.tituloNuevoPagoView);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        for (String nombre : nombresConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        this.poblarUnidadesFuncionales(listaUnidadesFuncionales);

        comboConsorcios.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    bus.post(new SolicitudListaUf((String) comboConsorcios.getSelectedItem()));
                }
            }
        });

        guardarPagoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String[] comboUF = ((String)comboUnidadesFuncionales.getSelectedItem()).split(Constantes.stringEspacio,2);
                    Integer idUf = Integer.valueOf(comboUF[Constantes.ceroInteger]);
                    bus.post(new GenerarPago(idUf, Double.valueOf(textMonto.getText())));
                    bus.post(Constantes.terminarAgregarPago);
                    frame.dispose();
                }catch (Exception exeption){
                    JOptionPane.showMessageDialog(null, Constantes.mensajeExepcionValidacion +  exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void poblarUnidadesFuncionales(List<String> listaUnidadesFuncionalesConsorcio) {
        comboUnidadesFuncionales.removeAllItems();
        for (String unidadFuncional : listaUnidadesFuncionalesConsorcio) {
            comboUnidadesFuncionales.addItem(unidadFuncional);
        }
    }

    public static class SolicitudListaUf {
        public String nombreConsorcio;

        public SolicitudListaUf(String nombre) {
            nombreConsorcio = nombre;
        }
    }

    public static class GenerarPago {
        public Integer idUnidadFuncional;
        public Double monto;

        public GenerarPago(Integer idUnidadFuncional, Double monto) {
            this.idUnidadFuncional = idUnidadFuncional;
            this.monto = monto;
        }
    }
}
