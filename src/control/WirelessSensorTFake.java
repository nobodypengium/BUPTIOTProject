package control;

import java.util.Random;

public class WirelessSensorTFake extends Thread {
    private volatile int temp=10;
    private volatile int hum=10;
    private volatile boolean warning = false;

    @Override
    public void run() {
        Random r = new Random();
        while(true){
            this.temp = r.nextInt(100);
            this.hum = r.nextInt(100);
            if(hum>75||temp>75){
                warning=true;
            } else{
                warning=false;
                //TODO:亮灯
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public int getHum() {
        return hum;
    }

    public int getTemp() {
        return temp;
    }

    public boolean isWarning() {
        return warning;
    }
}
