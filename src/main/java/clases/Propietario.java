package clases;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Propietario implements Observable {
    @Id
    private String dni;
    private String nombreApellido;
    private String mail;
    private String direccion;
    private String telefono;

    public Propietario() {
    }

    public Propietario(String dni, String nombreApellido, String mail, String direccion, String telefono) {
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.mail = mail;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }
    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //todo completar observable
    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }


}
