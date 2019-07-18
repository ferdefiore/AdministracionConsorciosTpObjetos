package clases.clasesRelacionales;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Propietario {
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

    public String getNombreApellido() {
        return nombreApellido;
    }

    @Override
    public String toString() {
        return (dni + ", " + nombreApellido + ", " + mail + " ," + direccion + ", " + telefono);
    }
}
