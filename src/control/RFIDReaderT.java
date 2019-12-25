package control;

import com.uhf.control.UhfUtil;

import java.util.Map;
import java.util.Random;

public class RFIDReaderT extends Thread {
    private static String epc="";
    private static String user = "";
    private Map<String,String> result;

    /**
     * 循环调用UhfUtil中的读取函数，存Map变量（含有user和epc区）在result中，再分别放到对应的字符串中
     */
    @Override
    public void run() {
        //暂停一会儿以给窗体机会显示还没刷卡的信息
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            result = UhfUtil.readAll();//读取TAG
            epc = result.get("EPC");
            user = result.get("USER");
            try {
                Thread.sleep(1000);//防止读的过快造成阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getEpc() {
        return epc;
    }

    public static String getUser() {
        return user;
    }

    /**
     * 清空所有已存储数据，避免下次读取时被上次缓存的数据影响
     */
    public void clear(){
        epc="";
        user="";
        result=null;
    }

}
