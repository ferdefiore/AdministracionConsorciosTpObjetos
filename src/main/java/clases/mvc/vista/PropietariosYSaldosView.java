package clases.mvc.vista;

import clases.utils.Constantes;
import clases.utils.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class PropietariosYSaldosView {
    private EventBus bus;
    private JTextField txtNomYApe;
    private JTextField txtDni;
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
    private JButton mostrarTodoButton;


    public PropietariosYSaldosView(List<String> propietarios, List<String> unidadesFuncionales) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame(Constantes.tituloPropietariosYSaldosView);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        this.poblarPropietarios(propietarios);
        this.poblarUnidadesFuncionales(unidadesFuncionales);
        buscarButton.addActionListener(e -> bus.post(new PedirFiltroPersona(txtDni.getText(), txtNomYApe.getText())));
        buscarButton1.addActionListener(e -> {
            String comparador = Constantes.stringVacio;
            if (igualRadioButton.isSelected()) {
                comparador = Constantes.singoIgual;
            } else if (menorRadioButton.isSelected()) {
                comparador = Constantes.signoMenor;
            } else if (mayorRadioButton.isSelected()) {
                comparador = Constantes.signoMayor;
            }
            bus.post(new PedirFiltroUnidadFunciona(Float.valueOf(textField1.getText()), comparador));
        });
        mostrarTodoButton.addActionListener(e -> {
            txtNomYApe.setText(Constantes.stringVacio);
            txtDni.setText(Constantes.stringVacio);
            bus.post(new PedirFiltroPersona(Constantes.stringVacio, Constantes.stringVacio));
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bus.post(Constantes.terminarPropietariosYSaldos);
            }
        });
    }

    public void poblarUnidadesFuncionales(List<String> unidadesFuncionales) {
        list1.removeAll();
        String[] datos = new String[unidadesFuncionales.size()];
        for (int i = 0; i < unidadesFuncionales.size(); i++) {
            datos[i] = unidadesFuncionales.get(i);
        }
        list2.setListData(datos);
    }

    public void poblarPropietarios(List<String> propietarios) {
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

    public class PedirFiltroUnidadFunciona {
        public float monto;
        public String comparador;

        public PedirFiltroUnidadFunciona(float monto, String comparador) {
            this.monto = monto;
            this.comparador = comparador;
        }

    }
}
