package clases.filtro;

import clases.UnidadFuncional;

public class FiltroMenor extends FiltroSaldo{

    public FiltroMenor(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean seCumple(UnidadFuncional uf) {
        return (uf.getSaldo() < saldo);
    }
}
