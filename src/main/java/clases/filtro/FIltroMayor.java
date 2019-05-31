package clases.filtro;

public class FIltroMayor extends FiltroSaldo {

    public FIltroMayor(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean seCumple(UnidadFuncional uf) {
        return (uf.getSaldo() > saldo);
    }
}
