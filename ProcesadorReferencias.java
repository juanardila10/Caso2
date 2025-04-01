import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProcesadorReferencias {
    Scanner sc;
    public ProcesadorReferencias(Scanner sc) {
        this.sc = sc;
    }
    public void procesarReferencias(){
        //System.out.println("Ingrese el numero de marcos de pagina: ");
        int numMarcos = sc.nextInt();
        //System.out.println("Ingrese el nombre del archivo de referencias: ");
        String nombreArchivo = sc.next();

        Map<String, Integer> valores = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;

            for (int i = 0; i < 5; i++) { // Leer las primeras 4 líneas
                linea = br.readLine();
                String[] partes = linea.split("="); // Separar clave y valor
                if (partes.length == 2) {
                    valores.put(partes[0].trim(), Integer.parseInt(partes[1].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        int NR = valores.getOrDefault("NR", 0);
        int NP = valores.getOrDefault("NP", 0);

        Hardware hardware = new Hardware(NP,numMarcos);
        Tanenbaum tanenbaum = new Tanenbaum(hardware);
        Analizador analizador = new Analizador(nombreArchivo, NR, hardware);
        tanenbaum.start();
        analizador.start();
        try {
            tanenbaum.join();
            analizador.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(hardware.misses+","+hardware.hits);
        //Numero de hits
    }
}
