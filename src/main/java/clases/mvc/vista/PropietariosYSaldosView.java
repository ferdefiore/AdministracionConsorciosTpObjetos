package clases.mvc.vista;

import clases.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PropietariosYSaldosView {
    private EventBus bus;
    private JTextField nomYApe;
    private JTextField dni;
    private JFrame frame;
    private JPanel panel1;
    private JList list1;
    private JButton buscarButton;
    private JRadioButton menorRadioButton;
    private JRadioButton mayorRadioButton;
    private JRadioButton igualRadioButton;
    private JTextField textField1;
    private JButton buscarButton1;
    private JList list2;


    public PropietariosYSaldosView(List<String> propietarios, List<String> unidadesFuncionales) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Datos Propietarios");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.poblarPropietarios(propietarios);
        this.poblarUnidadesFuncionales(unidadesFuncionales);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bus.post(new PedirFiltroPersona(dni.getText(), nomYApe.getText()));
            }
        });
        buscarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comparador="";
                if (igualRadioButton.isSelected()){
                    comparador = "=";
                } else if (menorRadioButton.isSelected()){
                    comparador = "<";
                } else if (mayorRadioButton.isSelected()){
                    comparador = ">";
                }
                bus.post(new PedirFiltroUnidadFunciona(Float.valueOf(textField1.getText()),comparador));
            }
        });
    }

    public void poblarUnidadesFuncionales(List<String> unidadesFuncionales) {
        list1.removeAll();
        String[] datos = new String[unidadesFuncionales.size()];
        for(int i = 0; i < unidadesFuncionales.size(); i++){
            datos[i] = unidadesFuncionales.get(i);
        }
        list2.setListData(datos);
    }

    public void poblarPropietarios(List<String> propietarios){
        list1.removeAll();
        String[] datos = new String[propietarios.size()];
        for (int i = 0; i < propietarios.size(); i++) {
            datos[i] = propietarios.get(i);
        }
        list1.setListData(datos);
    }

    public class PedirFiltroPersona {
        public String dni;
        public String nya;

        public PedirFiltroPersona(String dni, String nya) {
            this.dni = dni;
            this.nya = nya;
        }
    }

    public class PedirFiltroUnidadFunciona{
        public float monto;
        public String comparador;

        public PedirFiltroUnidadFunciona(float monto, String comparador) {
            this.monto = monto;
            this.comparador = comparador;
        }
    }
}
