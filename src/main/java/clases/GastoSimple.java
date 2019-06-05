package clases;

import javax.persistence.Entity;

@Entity
public class GastoSimple extends Gasto{
    @Override
    public float getMonto() {
        return monto;
    }
    public void setMonto(float montoNuevo){
        monto = montoNuevo;
    }

    public GastoSimple(int id, String concepto, float monto) {
        super(id, concepto, monto);
    }
}
