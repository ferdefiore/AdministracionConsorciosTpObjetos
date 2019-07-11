package clases.filtro;

public class FiltroTexto extends Filtro {
    private String textoContenido;

    public FiltroTexto(String texto) {
        this.textoContenido = texto;
    }

    @Override
    public boolean seCumple(Object entrada) {
        String persona = (String) entrada;
        return (persona.contains(textoContenido));
    }
}
