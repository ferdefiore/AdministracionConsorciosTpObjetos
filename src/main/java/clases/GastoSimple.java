package clases;

import javax.persistence.Entity;

@Entity
public class GastoSimple extends Gasto{
    public GastoSimple() {
    }

    public GastoSimple(int id, String concepto, float monto) {
        super(id, concepto, monto);
    }

    public void setMonto(float montoNuevo){
        monto = montoNuevo;
    }

    @Override
    public float getMonto() {
        return monto;
    }

}
