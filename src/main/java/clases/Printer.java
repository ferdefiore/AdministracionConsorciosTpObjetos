package clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Printer {
    public static void printLiquidacionCierre(Liquidacion liquidacion) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\Fermin\\Desktop\\Prueba.txt"));
        osw.write(liquidacion.getConsorcio().getNombre());
        osw.write("Informe correspondiente al cierre de la liquidacion pertenenciente al periodo: " + liquidacion.getPeriodo().toString());
        osw.write(System.lineSeparator());
        osw.write("El total conformado por los gastos es de: " + liquidacion.getGastoParcial());
        osw.write(System.lineSeparator());
        for (Gasto g: liquidacion.getGastos()) {
            osw.write(g.toString());
            osw.write(System.lineSeparator());
        }
        osw.close();
    }

    public static void printLiquidacionParcial(Liquidacion liquidacion) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("C:\\Users\\Fermin\\Desktop\\Prueba.txt"));
        osw.write(liquidacion.getConsorcio().getNombre());
        osw.write("Informe parcial liquidacion pertenenciente al periodo: " + liquidacion.getPeriodo().toString());
        osw.write(System.lineSeparator());
        osw.write("El monto parcial conformado por los gastos es de: " + liquidacion.getGastoParcial());
        osw.write(System.lineSeparator());
        for (Gasto g: liquidacion.getGastos()) {
            osw.write(g.toString());
            osw.write(System.lineSeparator());
        }
        osw.close();
    }

}
