package clases;

public abstract class Gasto {
    protected String concepto;
    protected float monto;

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public abstract float getMonto();
}
