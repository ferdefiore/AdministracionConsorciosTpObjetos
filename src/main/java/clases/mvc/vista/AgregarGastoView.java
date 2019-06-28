package clases.mvc.vista;

import clases.DbManager;
import clases.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class AgregarGastoView {
    private EventBus bus;
    private JFrame frame;
    private JPanel panel;
    private JComboBox comboConsorcios;
    private JComboBox comboGastos;
    private JTextField textMonto;
    private JTextField textConcepto;
    private JButton guardarGastoButton;
    private JCheckBox checkCompuesto;

    public AgregarGastoView(List<String> listaNombresConsorcios, List<Integer> listaIdGastos) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Agregar gastos");
        frame.setSize(600,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel);
        DbManager dbManager = DbManager.getDbManager();
        for (String nombre: listaNombresConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        for (Integer idGasto: listaIdGastos) {
            comboGastos.addItem(idGasto);
        }
        frame.setVisible(true);

        guardarGastoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreConsorcio = (String)comboConsorcios.getSelectedItem();
                String idGasto = (String) comboGastos.getSelectedItem();
                Integer idGastoSeleccionado = 0;
                if (!(idGasto.equals("Nuevo Gasto"))){
                    idGastoSeleccionado = Integer.valueOf((String)comboGastos.getSelectedItem());
                }
                String concepto = textConcepto.getText();
                Float monto = 0f;
                if (!(textMonto.getText().equals(""))){
                    monto = Float.valueOf(textMonto.getText());
                }
                if (comboGastos.getSelectedItem().equals("Nuevo Gasto") && !checkCompuesto.isSelected()){
                    bus.post(new AgregarNuevoGasto(nombreConsorcio,concepto,monto));
                } else if (comboGastos.getSelectedItem().equals("Nuevo Gasto") && checkCompuesto.isSelected()){
                    bus.post(new AgregarNuevoGasto(nombreConsorcio,concepto));
                } else if (!(comboGastos.getSelectedItem().equals("Nuevo Gasto")) && !checkCompuesto.isSelected()){
                    bus.post(new AgregarAGasto(nombreConsorcio,idGastoSeleccionado,concepto,monto));
                } else {
                    bus.post(new AgregarAGasto(nombreConsorcio,idGastoSeleccionado,concepto));
                }
                frame.dispose();
            }
        });
        checkCompuesto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkCompuesto.isSelected()){
                    textMonto.setText("");
                    textMonto.setEnabled(false);
                }else{
                    textMonto.setEnabled(true);
                }

            }
        });
        comboConsorcios.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                bus.post(new SolicitudListaGastos((String) comboConsorcios.getSelectedItem()));
            }
        });
    }
    public void poblarGastos(List<Integer> gastos){
        comboGastos.removeAllItems();
        for (Integer idGasto: gastos) {
            comboGastos.addItem(idGasto);
        }
        comboGastos.updateUI();
    }
    public static class SolicitudListaGastos{
        public String nombreConsorcio;
        public SolicitudListaGastos(String nombre){
            nombreConsorcio = nombre;
        }
    }
    public static class AgregarNuevoGasto {
        public String nombreConsorcio;
        public String concepto;
        public Float monto;
        public AgregarNuevoGasto(String nombreConsorcio, String concepto, Float monto) {
            this.nombreConsorcio =nombreConsorcio;
            this.concepto = concepto;
            this.monto = monto;
        }
        public AgregarNuevoGasto(String nombreConsorcio, String concepto) {
            this.nombreConsorcio =nombreConsorcio;
            this.concepto = concepto;
            this.monto = -1f;
        }
    }
    public static class AgregarAGasto {
        public String nombreConsorcio;
        public String concepto;
        public Float monto;
        public Integer idGastoSeleccionado;

        public AgregarAGasto(String nombreConsorcio,Integer idGastoSeleccionado, String concepto, Float monto) {
            this.nombreConsorcio =nombreConsorcio;
            this.idGastoSeleccionado = idGastoSeleccionado;
            this.concepto = concepto;
            this.monto = monto;
        }
        public AgregarAGasto(String nombreConsorcio,Integer idGastoSeleccionado, String concepto) {
            this.nombreConsorcio =nombreConsorcio;
            this.idGastoSeleccionado = idGastoSeleccionado;
            this.concepto = concepto;
            this.monto = -1f;
        }
    }
}
