package clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Printer {
    public static void printLiquidacionCierre(Liquidacion liquidacion) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(liquidacion.getConsorcio().getNombre()+"InformeGastosYSaldos.txt"));
        osw.write("Consorcio: " + liquidacion.getConsorcio().getNombre());
        osw.write(System.lineSeparator());
        osw.write("--------------------------------------------------------------------------------------");
        osw.write(System.lineSeparator());
        osw.write("Informe correspondiente al cierre de la liquidacion pertenenciente al periodo: " + liquidacion.getPeriodo().toString());
        osw.write(System.lineSeparator());
        osw.write("--------------------------------------------------------------------------------------");
        osw.write(System.lineSeparator());
        osw.write("El total conformado por los gastos es de: " + liquidacion.getGastoParcial() +". Gastos Detallados: ");
        osw.write(System.lineSeparator());
        for (Gasto g: liquidacion.getGastos()) {
            osw.write(g.toString());
            osw.write(System.lineSeparator());
        }
        osw.close();

    }

    public static void printSaldosCierre(String nombreConsorcio,List<UnidadFuncional> ufs) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(nombreConsorcio+"InformeGastosYSaldos.txt",true));
        osw.write("--------------------------------------------------------------------------------------");
        osw.write(System.lineSeparator());
        osw.write("--------------------------------------------------------------------------------------");
        osw.write(System.lineSeparator());
        osw.write("Informe saldos: ");
        osw.write(System.lineSeparator());
        for (UnidadFuncional uf: ufs){
            osw.write(uf.toString());
            osw.write(System.lineSeparator());
        }
        osw.write("--------------------------------------------------------------------------------------");
        osw.write(System.lineSeparator());
        osw.close();
    }
}
