public class Tanenbaum extends Thread{
    public Hardware hardware;

    Tanenbaum(Hardware hardware){
        this.hardware = hardware;

    }

    public void run(){
        while(hardware.ejecutando){
            hardware.resetearLeidos();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    

}
