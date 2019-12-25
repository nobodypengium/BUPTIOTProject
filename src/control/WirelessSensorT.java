/*
 * MainFrame.java
 *
 * Created on 2016.8.19
 */

package control;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.yang.serialport.exception.NoSuchPort;
import com.yang.serialport.exception.NotASerialPort;
import com.yang.serialport.exception.PortInUse;
import com.yang.serialport.exception.SendDataToSerialPortFailure;
import com.yang.serialport.exception.SerialPortOutputStreamCloseFailure;
import com.yang.serialport.exception.SerialPortParameterFailure;
import com.yang.serialport.exception.TooManyListeners;
import com.yang.serialport.manage.SerialPortManager;
import com.yang.serialport.utils.ByteUtils;
import com.yang.serialport.utils.ShowUtils;

import java.lang.String;

/**
 * ������ The main interface
 *
 * @author yangle
 */
public class WirelessSensorT extends Thread {
    //ÿ�ζ��ᱻ���µ���ʪ��
    private int temp;
    private int hum;
    private int illumination;
    private boolean warning=false;

    //���ڴ洢������Ϣ�����ݽṹ
    private SerialPort serialport;

//    public WirelessSensorT(){
//        openSerialPort();
//    }


    @Override
    public void run() {
        openSerialPort();
    }

    /**
     *
     */
    private void openSerialPort() {    // ��ȡ��������
        try {
            serialport = SerialPortManager.openPort("COM6", 115200);
            if (serialport != null) {
                System.out.println("�����Ѵ�" + "\r\n");
            }
        } catch (SerialPortParameterFailure e) {
            e.printStackTrace();
        } catch (NotASerialPort e) {
            e.printStackTrace();
        } catch (NoSuchPort e) {
            e.printStackTrace();
        } catch (PortInUse e) {
            e.printStackTrace();
            ShowUtils.warningMessage("�����ѱ�ռ�ã�");
        }
        //��Ӵ��ڼ�����������ע�����Ƿ����������ݣ���������������д�뱾ʵ��˽�б���
        try {
            SerialPortManager.addListener(serialport, new SerialListener());
        } catch (TooManyListeners tooManyListeners) {
            tooManyListeners.printStackTrace();
        }
    }


    /**
     * closeSerialPort
     * �رմ��� close the serial port
     *
     * @param evt ����¼� Click event
     */
    private void closeSerialPort(java.awt.event.ActionEvent evt) {
        SerialPortManager.closePort(serialport);
        serialport = null;
    }


    /**
     * sendData
     * �������� send data
     *
     */
    private void sendData(String data) {
        try {
            SerialPortManager.sendToPort(serialport,
                    ByteUtils.hexStr2Byte(data));
        } catch (SendDataToSerialPortFailure e) {
            e.printStackTrace();
        } catch (SerialPortOutputStreamCloseFailure e) {
            e.printStackTrace();
        }
    }

    public int getHum() {
        return hum;
    }

    public int getTemp() {
        return temp;
    }

    public int getIllumination() {
        return illumination;
    }

    public boolean isWarning() {
        return warning;
    }

    private class SerialListener implements SerialPortEventListener {
        /**
         * * �����ص��Ĵ����¼�
         */
        public void serialEvent(SerialPortEvent serialPortEvent) {

            switch (serialPortEvent.getEventType()) {

                case SerialPortEvent.BI: // 10ͨѶ�ж�
                    ShowUtils.errorMessage("�봮���豸ͨѶ�ж�");
                    break;

                case SerialPortEvent.OE: // 7��λ�����������


                case SerialPortEvent.FE: // 9֡����


                case SerialPortEvent.PE: // 8��żУ�����


                case SerialPortEvent.CD: // 6�ز����


                case SerialPortEvent.CTS: // 3�������������


                case SerialPortEvent.DSR: // 4����������׼������


                case SerialPortEvent.RI: // 5����ָʾ


                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2��������������
                    break;

                case SerialPortEvent.DATA_AVAILABLE: // 1���ڴ��ڿ�������

                    byte[] data = null;
                    try {
                        if (serialport == null) {
                            ShowUtils.errorMessage("���ڶ���Ϊ�գ�����ʧ�ܣ�");
                        } else {
                            // ��ȡ�������ݣ��������
                            data = SerialPortManager.readFromPort(serialport);
                            String output = "";
                            Date date = new Date();
                            DateFormat df3 = new SimpleDateFormat("yyy-MM-dd HH-mm-ss");
                            String dateString = df3.format(date);
                            output += "ϵͳʱ��: " + dateString + " ";
                            output += "��ͷ: " + ByteUtils.byteArrayToHexString(data, true).substring(0, 4) + " ";
                            output += "�̵�ַ: " + ByteUtils.byteArrayToHexString(data, true).substring(4, 8) + " ";
                            output += "MAC: " + ByteUtils.byteArrayToHexString(data, true).substring(9, 17) + ByteUtils.byteArrayToHexString(data, true).substring(19, 27) + " ";
                            output += "��ѹֵ: " + ByteUtils.byteArrayToHexString(data, true).substring(27, 31) + " ";
                            output += "�ڵ㹦��: " + ByteUtils.byteArrayToHexString(data, true).substring(31, 36) + " ";
                            output += "�����ʶ: " + ByteUtils.byteArrayToHexString(data, true).substring(36, 40) + " ";
                            long temperaturedec = Long.parseLong(ByteUtils.byteArrayToHexString(data, true).substring(40, 44), 16);
                            temp = (int)temperaturedec;
                            output += "�¶�(ʮ����): " + temp + " ";
                            output += "�¶�: " + ByteUtils.byteArrayToHexString(data, true).substring(40, 44) + " ";
                            long humdec = Long.parseLong(ByteUtils.byteArrayToHexString(data, true).substring(45,49), 16);
                            hum = (int)humdec;
                            output += "ʪ��: " + ByteUtils.byteArrayToHexString(data, true).substring(45, 49) + " ";
                            long illudec = Long.parseLong(ByteUtils.byteArrayToHexString(data, true).substring(49,53), 16);
                            illumination = (int)illudec;
                            if(temp>6657||hum>908)//������ʪ�ȸ���Ԥ�����
                                warning=true;
                            else
                                warning=false;
                            output += "����: " + ByteUtils.byteArrayToHexString(data, true).substring(49, 53) + " ";
                            output += "��β: " + ByteUtils.byteArrayToHexString(data, true).substring(54, 58) + " ";
//						String data1 = ByteUtils.byteArrayToHexString(data, true).substring(40,54);
//						String data1 = ByteUtils.byteArrayToHexString(data, true);
                            System.out.println(output + "\r\n");
                        }
                    } catch (Exception e) {
                        ShowUtils.errorMessage(e.toString());
                        // ������ȡ����ʱ��ʾ������Ϣ���˳�ϵͳ
                        System.exit(0);
                    }
                    break;
            }
        }

    }
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//                                            public void run() {
//                                                new WirelessSensorT();
//                                            }
//                                        }
//        );
//    }
}
