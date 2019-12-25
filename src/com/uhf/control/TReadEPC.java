package com.uhf.control;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by hp on 2019/12/12.
 */
public class TReadEPC implements Runnable{
    private static Map<String,String> tagInfo;

    @Override
    public void run() {
       while(true){
            this.tagInfo = UhfUtil.readAll();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static Map<String,String> getTagInfo(){
        return tagInfo;
    }
}
