package test;

import com.uhf.linkage.Linkage;
import gui.*;

import java.util.Random;

public class TestGUI {
    public static void main(String args[]) throws InterruptedException {
        //��ʼ���豸
        int i =  Linkage.getInstance().initial("COM4");
        if(i != 0){
            System.out.println("Cannot connect!");
        }

        new LoginPage();
    }
}
