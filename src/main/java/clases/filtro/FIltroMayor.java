package clases.filtro;

public class FIltroMayor extends FiltroSaldo {

    public FIltroMayor(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean seCumple(Object entrada) {
        double valorFiltro = (double) entrada;
        return (valorFiltro > saldo);
    }
}
