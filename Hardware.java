public class Hardware {
    int[] tablaPaginas;
    int[] leidos;
    int[] escritos;
    int[] memoriaRam;
    int hits = 0;
    int misses = 0;
    int marcosDisponibles;
    boolean ejecutando = true;

    public Hardware(int numPaginas, int numeroMarcos) {
        tablaPaginas = new int[numPaginas];
        leidos = new int[numPaginas];
        escritos = new int[numPaginas];
        memoriaRam = new int[numeroMarcos];
        marcosDisponibles = numeroMarcos;
        for (int i = 0; i < numPaginas; i++) {
            leidos[i] = 0;
            escritos[i] = 0;
            tablaPaginas[i] = -1;
        }
        for (int i = 0; i < numeroMarcos; i++) {
            memoriaRam[i] = -1;
        }
    }

    public synchronized void resetearLeidos(){
        for (int i = 0; i < leidos.length; i++) {
            leidos[i] = 0;
        }
    }
    
    public synchronized void accederPagina(int pagina,String tipoAcceso){
        int marcoActual=tablaPaginas[pagina];
        if (marcoActual==-1){
            misses++;
            if (marcosDisponibles!=0){
                tablaPaginas[pagina]=memoriaRam.length-marcosDisponibles;
                memoriaRam[memoriaRam.length-marcosDisponibles]=pagina;
                if (tipoAcceso.equals("R")){
                    leidos[pagina]=1;
                }
                else{
                    leidos[pagina]=1;
                    escritos[pagina]=1;
                }
                marcosDisponibles--;
            }
            else{
                int paginaCandidata=Integer.MAX_VALUE;
                int categoriaCandidata=Integer.MAX_VALUE;
                int i=0;
                while (i<memoriaRam.length){
                    if (leidos[memoriaRam[i]]==0 && escritos[memoriaRam[i]]==0){
                        if (categoriaCandidata>1){
                            paginaCandidata=memoriaRam[i];
                            categoriaCandidata=1;
                        }
                    }
                    else if (leidos[memoriaRam[i]]==0 && escritos[memoriaRam[i]]==1){
                        if (categoriaCandidata>2){
                            paginaCandidata=memoriaRam[i];
                            categoriaCandidata=2;
                        }
                    }
                    else if (leidos[memoriaRam[i]]==1 && escritos[memoriaRam[i]]==0){
                        if (categoriaCandidata>3){
                            paginaCandidata=memoriaRam[i];
                            categoriaCandidata=3;
                        }
                    }
                    else if (leidos[memoriaRam[i]]==1 && escritos[memoriaRam[i]]==1){
                        if (categoriaCandidata>4){
                            paginaCandidata=memoriaRam[i];
                            categoriaCandidata=4;
                        }
                    }
                    i++;
                }
                int marcoPaginaCandidata=tablaPaginas[paginaCandidata];
                tablaPaginas[paginaCandidata]=-1;
                leidos[paginaCandidata]=0;
                escritos[paginaCandidata]=0;
                tablaPaginas[pagina]=marcoPaginaCandidata;
                memoriaRam[marcoPaginaCandidata]=pagina;
                if (tipoAcceso.equals("R")){
                    leidos[pagina]=1;
                }
                else{
                    leidos[pagina]=1;
                    escritos[pagina]=1;
                }
            }
        }
        else{
            hits++;
            if (tipoAcceso.equals("R")){
                leidos[pagina]=1;
            }
            else{
                leidos[pagina]=1;
                escritos[pagina]=1;
            }
        }
    }

    public void pararEjecucion(){
        ejecutando=false;
    }
}
