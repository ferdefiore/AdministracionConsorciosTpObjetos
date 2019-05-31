package clases.filtro;

public class FiltroIgual extends FiltroSaldo {

    public FiltroIgual(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean seCumple(UnidadFuncional uf) {
        return (uf.getSaldo() == saldo);
    }
}
