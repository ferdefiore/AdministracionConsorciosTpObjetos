package clases.mvc.vista;

import clases.clasesRelacionales.Gasto;
import clases.clasesRelacionales.UnidadFuncional;
import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class LiquidacionVigenteView {
    private JList list1;
    private JPanel panel1;
    private JComboBox comboConsorcios;
    private JButton buscarButton;
    private JButton verSaldosButton;
    private EventBus bus;
    private JFrame frame;

    public LiquidacionVigenteView(List<String> consorcios) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame(Constantes.tituloLiquidacionVigenteView);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this.panel1);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for (String nombre : consorcios) {
            comboConsorcios.addItem(nombre);
        }

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null == comboConsorcios.getSelectedItem()){
                    JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion + Constantes.comboVacio, Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else {
                    try {
                        bus.post(new SolicitudGastosLiquidacion((String) comboConsorcios.getSelectedItem()));
                    } catch (Exception exeption) {
                        JOptionPane.showMessageDialog(null, Constantes.mensajeExepcionValidacion + exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        verSaldosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (null == comboConsorcios.getSelectedItem()){
                    JOptionPane.showMessageDialog(null, Constantes.mensajeErrorInicializacion + Constantes.comboVacio, Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else {
                    try {
                        bus.post(new SolicitudSaldosLiquidacion((String) comboConsorcios.getSelectedItem()));
                    } catch (Exception exeption) {
                        JOptionPane.showMessageDialog(null, Constantes.mensajeExepcionValidacion + exeption.getMessage(), Constantes.stringError, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bus.post(Constantes.terminarLiquidacionVigente);
            }
        });
    }

    public void poblarListaGasto(List<Gasto> gastos) {
        list1.removeAll();
        String[] datos = new String[gastos.size()];
        for (int i = 0; i < gastos.size(); i++) {
            datos[i] = gastos.get(i).toString();
        }
        list1.setListData(datos);
    }

    public void poblarListaUf(List<UnidadFuncional> ufs) {
        list1.removeAll();
        String[] datos = new String[ufs.size()];
        for (int i = 0; i < ufs.size(); i++) {
            datos[i] = ufs.get(i).toString();
        }
        list1.setListData(datos);
    }

    public class SolicitudGastosLiquidacion {
        public String nombreConsorcio;

        public SolicitudGastosLiquidacion(String nombreConsorcio) {
            this.nombreConsorcio = nombreConsorcio;
        }
    }

    public class SolicitudSaldosLiquidacion {
        public String nombreConsorcio;

        public SolicitudSaldosLiquidacion(String nombreConsorcio) {
            this.nombreConsorcio = nombreConsorcio;
        }
    }
}
