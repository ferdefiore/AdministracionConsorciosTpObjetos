package clases.filtro;

public class FiltroIgual extends FiltroSaldo {

    public FiltroIgual(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean seCumple(Object entrada) {
        double valorFiltro = (double) entrada;
        return (valorFiltro==saldo);
    }
}
