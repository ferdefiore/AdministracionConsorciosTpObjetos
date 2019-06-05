package clases.filtro;

import clases.UnidadFuncional;

public class FiltroNombreApellido extends FiltroTexto {

    @Override
    public boolean seCumple(UnidadFuncional uf) {
        return (uf.getPropietario().getNombreApellido().equals(texto));
        //todo cambiar equals por substring
    }
}
