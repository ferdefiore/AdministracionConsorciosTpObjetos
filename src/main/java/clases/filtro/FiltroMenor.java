package clases.filtro;

public class FiltroMenor extends FiltroSaldo {

    public FiltroMenor(float saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean seCumple(Object entrada) {
        double valorFiltro = (double) entrada;
        return (valorFiltro < saldo);
    }
}
