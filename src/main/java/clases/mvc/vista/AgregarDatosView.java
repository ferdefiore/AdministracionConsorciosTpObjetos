package clases.mvc.vista;

import clases.EventBusFactory;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class AgregarDatosView {
    private EventBus bus;
    private JFrame frame;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField txtNombreConsorcio;
    private JTextField txtCuitConsorcio;
    private JTextField txtDireccionConsorcio;
    private JTextField txtCiudadConsorcio;
    private JButton guardarConsorcioButton;
    private JTextField txtTipoUf;
    private JComboBox comboPropietarios;
    private JTextField txtCoeficienteUf;
    private JTextField txtPisoNumUf;
    private JTextField txtSaldoInicialUf;
    private JButton guardarUnidadFuncionalButton;
    private JTextField txtDniPropietario;
    private JTextField txtNombreYApellidoPropietario;
    private JButton guardarPropietarioButton;
    private JTextField txtMailPropietario;
    private JTextField txtDireccionPropietario;
    private JTextField txtTelefonoPropietario;
    private JComboBox comboConsorcios;

    public AgregarDatosView(List<String> consorcios, List<String> propietarios) {
        bus = EventBusFactory.getEventBus();
        bus.register(this);
        frame = new JFrame("Agregar Datos");
        frame.setSize(600,450);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this.panel1);
        frame.setLocation(300,250);
        frame.setVisible(true);
        this.actualizarListaConsorciosUf(consorcios);
        this.actualizarListaPropietarios(propietarios);

        guardarConsorcioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bus.post(new AgregarDatosView.CrearConsorcioNuevo(txtNombreConsorcio.getText(),txtCuitConsorcio.getText(),txtDireccionConsorcio.getText(),txtCiudadConsorcio.getText()));
                txtCiudadConsorcio.setText("");
                txtCuitConsorcio.setText("");
                txtDireccionConsorcio.setText("");
                txtNombreConsorcio.setText("");
            }
        });
        guardarUnidadFuncionalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            bus.post(new CrearUnidadFuncionalNueva((String)comboConsorcios.getSelectedItem(),txtTipoUf.getText(),(String)comboPropietarios.getSelectedItem(),Double.valueOf(txtCoeficienteUf.getText()),txtPisoNumUf.getText(),Double.valueOf(txtSaldoInicialUf.getText())));
            txtSaldoInicialUf.setText("");
            txtPisoNumUf.setText("");
            txtCoeficienteUf.setText("");
            txtTipoUf.setText("");
            }
        });
        guardarPropietarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bus.post(new CrearPropietarioNuevo(txtDniPropietario.getText(),txtNombreYApellidoPropietario.getText(),txtMailPropietario.getText(),txtDireccionPropietario.getText(),txtTelefonoPropietario.getText()));
                txtDireccionPropietario.setText("");
                txtDniPropietario.setText("");
                txtMailPropietario.setText("");
                txtTelefonoPropietario.setText("");
                txtNombreYApellidoPropietario.setText("");
            }
        });
    }

    public void actualizarListaConsorciosUf(List<String> listaConsorcios) {
        comboConsorcios.removeAllItems();
        for (String nombre:listaConsorcios) {
            comboConsorcios.addItem(nombre);
        }
        comboConsorcios.repaint();
    }

    public void actualizarListaPropietarios(List<String> listaPropietarios) {
        comboPropietarios.removeAllItems();
        for (String nombre:listaPropietarios) {
            comboPropietarios.addItem(nombre);
        }
    }

    public class CrearConsorcioNuevo{
        public String nombre;
        public String cuit;
        public String direccion;
        public String ciudad;

        public CrearConsorcioNuevo(String nombre, String cuit, String direccion, String ciudad) {
            this.nombre = nombre;
            this.cuit = cuit;
            this.direccion = direccion;
            this.ciudad = ciudad;
        }
    }

    public class CrearUnidadFuncionalNueva{
        public String nombreConsorcioPerteneciente;
        public String tipoUf;
        public String dniPropietario;
        public double coeficientePago;
        public String pisoNum;
        public double saldo;

        public CrearUnidadFuncionalNueva(String nombreConsorcioPerteneciente, String tipoUf, String dniPropietario, double coeficientePago, String pisoNum, double saldo) {
            this.nombreConsorcioPerteneciente = nombreConsorcioPerteneciente;
            this.tipoUf = tipoUf;
            this.dniPropietario = dniPropietario;
            this.coeficientePago = coeficientePago;
            this.pisoNum = pisoNum;
            this.saldo = saldo;
        }
    }

    public class CrearPropietarioNuevo{
        public String dni;
        public String nomYApe;
        public String mail;
        public String direccion;
        public String telefono;

        public CrearPropietarioNuevo(String dni, String nomYApe, String mail, String direccion, String telefono) {
            this.dni = dni;
            this.nomYApe = nomYApe;
            this.mail = mail;
            this.direccion = direccion;
            this.telefono = telefono;
        }
    }
}
