package control;

import com.uhf.control.UhfUtil;

import java.util.Map;
import java.util.Random;

public class RFIDReaderT extends Thread {
    private static String epc="";
    private static String user = "";
    private Map<String,String> result;

    /**
     * ѭ������UhfUtil�еĶ�ȡ��������Map����������user��epc������result�У��ٷֱ�ŵ���Ӧ���ַ�����
     */
    @Override
    public void run() {
        //��ͣһ����Ը����������ʾ��ûˢ������Ϣ
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            result = UhfUtil.readAll();//��ȡTAG
            epc = result.get("EPC");
            user = result.get("USER");
            try {
                Thread.sleep(1000);//��ֹ���Ĺ����������
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
     * ��������Ѵ洢���ݣ������´ζ�ȡʱ���ϴλ��������Ӱ��
     */
    public void clear(){
        epc="";
        user="";
        result=null;
    }

}
