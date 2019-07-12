package clases.filtro;

public class FiltroOr extends Filtro{
    private Filtro f1;
    private Filtro f2;

    @Override
    public boolean seCumple(Object entrada) {
        return (f1.seCumple(entrada) || f2.seCumple(entrada));
    }

    public FiltroOr(Filtro f1, Filtro f2) {
        this.f1 = f1;
        this.f2 = f2;
    }
}
