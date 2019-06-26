package clases.mvc.vista;

import clases.DbManager;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class AgregarGasto {
    private JFrame frame;
    private JPanel panel;
    private JComboBox comboConsorcios;
    private JComboBox comboGastos;
    private JTextField textMonto;
    private JTextField textConcepto;
    private JButton guardarGastoButton;
    private JCheckBox checkCompuesto;

    public AgregarGasto() {
        frame = new JFrame("Agregar gastos");
        frame.setSize(600,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel);
        DbManager dbManager = DbManager.getDbManager();
        List<String> listaNombresConsorcios = dbManager.getListaNombresConsorcios();
        for (String nombre: listaNombresConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        //para rellenar ni bien abre el combodegastos
        List<Integer> listaIdGastos = dbManager.getListaGastos((String) comboConsorcios.getSelectedItem());
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
                    //creo gasto nuevo simple
                    dbManager.agregarNuevoGasto(nombreConsorcio,concepto,monto);
                } else if (comboGastos.getSelectedItem().equals("Nuevo Gasto") && checkCompuesto.isSelected()){
                    //creo gasto nuevo compuesto
                    dbManager.agregarNuevoGasto(nombreConsorcio,concepto);
                } else if (!(comboGastos.getSelectedItem().equals("Nuevo Gasto")) && !checkCompuesto.isSelected()){
                    //creo gasto viejo simple
                    dbManager.agregarAGastoSimple(nombreConsorcio,idGastoSeleccionado,concepto,monto);
                } else {
                    //creo gasto viejo compuesto
                    dbManager.agregarAGastoCompuesto(nombreConsorcio,idGastoSeleccionado,concepto,monto);
                }
            }
        });
        comboConsorcios.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                List<Integer> lista = dbManager.getListaGastos((String) comboConsorcios.getSelectedItem());
                for (Integer idGasto: lista) {
                    comboGastos.addItem(idGasto);
                }
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
    }
}
