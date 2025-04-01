import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GeneradorReferencias {
    Scanner sc;

    public GeneradorReferencias(Scanner sc) {
        this.sc =sc;
    }

    public void generarReferencias(){
        Imagen imagenIn;
        
        //Pedimos el tamaño de pagina
        //System.out.println("Ingrese el tamaño de página: ");
        int tamPagina = sc.nextInt();
        //Pedimos el nombre del archivo
        //System.out.println("Ingrese el nombre del archivo: ");
        String nombreArchivo = sc.next();
        //Creamos la imagen
        imagenIn = new Imagen(nombreArchivo);
        //Escribimos las referencias, calculamos las que son constantes
        int bytesInicioSobelX=imagenIn.alto*imagenIn.ancho*3;
        int bytesInicioSobelY=bytesInicioSobelX+36;
        int bytesInicioRespuesta=bytesInicioSobelY+36;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("referencias.txt"))) {
            writer.write("TP="+tamPagina);
            writer.newLine(); // Salto de línea
            writer.write("NF="+imagenIn.alto);
            writer.newLine(); // Salto de línea
            writer.write("NC="+imagenIn.ancho);
            writer.newLine(); // Salto de línea
            //Se hace un ciclo sobre todas las filas menos la primera y la ultima (imagenIn.alto-2)
                //Se hace un ciclo sobre todas las columnas menos la primera y la ultima (imagenIn.ancho-2)
                    //En cada iteracion se hace un ciclo de -1 a 1 (3 iteraciones) para las filas 
                        //En cada iteracion se hace un ciclo de -1 a 1 (3 iteraciones) para las columnas
                            //En cada iteracion se hacen 9 referencias (3 para cada color de la imagen y 6 para los filtros de sobel)
                    //Se hacen 3 llamados a la imagen de salida (3 colores)
            //(imagenIn.alto-2)*(imagenIn.ancho-2)*(3*3*9+3)            
            int numReferecia=(imagenIn.alto-2)*(imagenIn.ancho-2)*84;
            writer.write("NR="+numReferecia);
            writer.newLine();
            int paginas=(int) Math.ceil((imagenIn.alto*imagenIn.ancho*6+72)/((float) tamPagina));
            writer.write("NP="+paginas);
            //Se hace el simil al codigo del filtro sobel iterando sobre la segunda a la penultima fila
            for (int i=1;i<imagenIn.alto-1;i++){
                //Se hace el simil al codigo del filtro sobel iterando sobre la segunda a la penultima columna
                for (int j=1;j<imagenIn.ancho-1;j++){
                    //Se hace un ciclo de -1 a 1 (3 iteraciones) para las filas
                    for (int ki = -1; ki <= 1; ki++) {
                        //Se hace un ciclo de -1 a 1 (3 iteraciones) para las columnas
                        for (int kj = -1; kj <= 1; kj++) {
                            writer.newLine();
                            int fila=i+ki;
                            int columna=j+kj;
                            int valorInicial=fila*imagenIn.ancho*3+columna*3;
                            int paginaRojo=valorInicial/tamPagina;
                            int desplazamientoRojo=valorInicial%tamPagina;
                            // int red = imagenIn.imagen[i+ki][j+kj][0]; Linea en el codigo original        
                            writer.write("Imagen["+fila+"]["+columna+"].r,"+paginaRojo+","+desplazamientoRojo+",R");
                            writer.newLine();
                            int paginaVerde=(valorInicial+1)/tamPagina;
                            int desplazamiento=(valorInicial+1)%tamPagina;
                            // int green = imagenIn.imagen[i+ki][j+kj][1]; Linea en el codigo original
                            writer.write("Imagen["+fila+"]["+columna+"].g,"+paginaVerde+","+desplazamiento+",R");
                            writer.newLine();
                            int paginaAzul=(valorInicial+2)/tamPagina;
                            int desplazamientoAzul=(valorInicial+2)%tamPagina;
                            // int blue = imagenIn.imagen[i+ki][j+kj][2]; Linea en el codigo original
                            writer.write("Imagen["+fila+"]["+columna+"].b,"+paginaAzul+","+desplazamientoAzul+",R");
                            
                            int paginaSobelX=(bytesInicioSobelX+(ki+1)*12+(kj+1)*4)/tamPagina;
                            int desplazamientoSobelX=(bytesInicioSobelX+(ki+1)*12+(kj+1)*4)%tamPagina;
                            String mensajeSobelX="SOBEL_X["+(ki+1)+"]["+(kj+1)+"],"+paginaSobelX+","+desplazamientoSobelX+",R";
                            // Lineas en el codigo original que se escriben en el ciclo
                            // gradXRed += red * SOBEL_X[ki + 1][kj + 1];
                            // gradXGreen += green * SOBEL_X[ki + 1][kj + 1];
                            // gradXBlue += blue * SOBEL_X[ki + 1][kj + 1];
                            for (int k=0;k<3;k++){
                                writer.newLine();
                                writer.write(mensajeSobelX);
                            }
                            int paginaSobelY=(bytesInicioSobelY+(ki+1)*12+(kj+1)*4)/tamPagina;
                            int desplazamientoSobelY=(bytesInicioSobelY+(ki+1)*12+(kj+1)*4)%tamPagina;
                            String mensajeSobelY="SOBEL_Y["+(ki+1)+"]["+(kj+1)+"],"+paginaSobelY+","+desplazamientoSobelY+",R";
                            // Lineas en el codigo original que se escriben en el ciclo
                            // gradYRed += red * SOBEL_Y[ki + 1][kj + 1];
                            // gradYGreen += green * SOBEL_Y[ki + 1][kj + 1];
                            // gradYBlue += blue * SOBEL_Y[ki + 1][kj + 1];
                            for (int k=0;k<3;k++){
                                writer.newLine();
                                writer.write(mensajeSobelY);
                            }
                        }
                    }
                    int valorInicialRespuesta=bytesInicioRespuesta+(i)*(imagenIn.ancho)*3+(j)*3;
                    int paginaRespuestaRojo=(valorInicialRespuesta)/tamPagina;
                    int desplazamientoRespuestaRojo=(valorInicialRespuesta)%tamPagina;
                    writer.newLine();
                    //imagenOut.imagen[i][j][0] = (byte)red; Linea en el codigo original
                    writer.write("Rta["+i+"]["+j+"].r,"+paginaRespuestaRojo+","+desplazamientoRespuestaRojo+",W");
                    int paginaRespuestaVerde=(valorInicialRespuesta+1)/tamPagina;
                    int desplazamientoRespuestaVerde=(valorInicialRespuesta+1)%tamPagina;
                    writer.newLine();
                    //imagenOut.imagen[i][j][1] = (byte)green; Linea en el codigo original
                    writer.write("Rta["+i+"]["+j+"].g,"+paginaRespuestaVerde+","+desplazamientoRespuestaVerde+",W");
                    int paginaRespuestaAzul=(valorInicialRespuesta+2)/tamPagina;
                    int desplazamientoRespuestaAzul=(valorInicialRespuesta+2)%tamPagina;
                    writer.newLine();
                    //imagenOut.imagen[i][j][2] = (byte)blue; Linea en el codigo original
                    writer.write("Rta["+i+"]["+j+"].b,"+paginaRespuestaAzul+","+desplazamientoRespuestaAzul+",W");
                }
            }
        //System.out.println("Archivo escrito exitosamente.");
        } 
        catch (IOException e) {
            System.out.println(e.getMessage());
        }      
    }
}
