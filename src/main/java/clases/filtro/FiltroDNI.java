package clases.filtro;

import clases.UnidadFuncional;

public class FiltroDNI extends FiltroTexto {
    @Override
    public boolean seCumple(UnidadFuncional uf) {
        return uf.getPropietario().getDni().equals(texto);
    }
}
