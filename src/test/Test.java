package test;

import com.uhf.control.TReadEPC;
import com.uhf.control.UhfUtil;
import com.uhf.linkage.Linkage;
import gui.Layout;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by hp on 2019/12/12.
 */
public class Test {
    public static void main(String args[]){
//        Thread t = new Thread(new TReadEPC());
//        t.setDaemon(false);
//        t.start();
//        while(true){
//            System.out.println("Fuck");
//            System.out.println(TReadEPC.getEPC());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        boolean startFlag = true;
        int i =  Linkage.getInstance().initial("COM3");

        if(i != 0){
            System.out.println("Cannot connect!");
        }

        while(true){
            System.out.println("fuck");
            Map<String,String> a = UhfUtil.readAll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(a.get("EPC")+a.get("USER"));
        }
    }
}
