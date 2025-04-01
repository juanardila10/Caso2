import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Analizador extends Thread{
    public String nombreArchivo;
    public int numReferecia;
    public Hardware hardware;


    Analizador(String nombreArchivo, int numReferecia, Hardware hardware){
        this.numReferecia = numReferecia;
        this.nombreArchivo = nombreArchivo;
        this.hardware = hardware;
    }

    public void run(){
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
                for (int i = 0; i < 5; i++) { // Descartamos las primeras 5 líneas que no son de registros
                    linea = br.readLine();
                }
                for (int i=0;i<numReferecia;i++){
                    linea = br.readLine();
                    String[] partes = linea.split(",");
                    int pagina = Integer.parseInt(partes[1]);
                    String tipoAcceso=partes[3];
                    //System.out.println("Referencia: "+i);
                    hardware.accederPagina(pagina,tipoAcceso);
                    if (i%10000==0){
                        //System.out.println("Ciclo de reloj");
                        Thread.sleep(1);
                    }
                }
                hardware.pararEjecucion();
            } 
        catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } 
        catch (InterruptedException e) {
                    e.printStackTrace();
        }
    }


}
