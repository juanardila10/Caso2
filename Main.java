import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Imagen imagenOut;
        boolean ejecutando=true;
        while(ejecutando){
            //Solicitamos un input del 1 al 3
            //System.out.println("Seleccione una opción: ");
            //System.out.println("1. Generacion de referencias");
            //System.out.println("2. Calcular datos buscados: numero de fallas de pagina, porcentaje de hits, tiempos");
            //System.out.println("3. Salir");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            //Si la opción es 1, se ejecuta el filtro Sobel
            if (opcion==1){
                GeneradorReferencias generador = new GeneradorReferencias(sc);
                generador.generarReferencias();
            }
            else if (opcion==2){
                ProcesadorReferencias procesador = new ProcesadorReferencias(sc);
                procesador.procesarReferencias();
            }
            else if (opcion==3){
                ejecutando=false;
            }
            else{
                System.out.println("Opción no válida");
            }
        }
    }
}
