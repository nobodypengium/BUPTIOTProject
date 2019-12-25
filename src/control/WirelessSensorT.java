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
 * 主界面 The main interface
 *
 * @author yangle
 */
public class WirelessSensorT extends Thread {
    //每次都会被更新的温湿度
    private int temp;
    private int hum;
    private int illumination;
    private boolean warning=false;

    //用于存储串口信息的数据结构
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
    private void openSerialPort() {    // 获取串口名称
        try {
            serialport = SerialPortManager.openPort("COM6", 115200);
            if (serialport != null) {
                System.out.println("串口已打开" + "\r\n");
            }
        } catch (SerialPortParameterFailure e) {
            e.printStackTrace();
        } catch (NotASerialPort e) {
            e.printStackTrace();
        } catch (NoSuchPort e) {
            e.printStackTrace();
        } catch (PortInUse e) {
            e.printStackTrace();
            ShowUtils.warningMessage("串口已被占用！");
        }
        //添加串口监听：持续关注串口是否有来的数据，若有来的数据则写入本实例私有变量
        try {
            SerialPortManager.addListener(serialport, new SerialListener());
        } catch (TooManyListeners tooManyListeners) {
            tooManyListeners.printStackTrace();
        }
    }


    /**
     * closeSerialPort
     * 关闭串口 close the serial port
     *
     * @param evt 点击事件 Click event
     */
    private void closeSerialPort(java.awt.event.ActionEvent evt) {
        SerialPortManager.closePort(serialport);
        serialport = null;
    }


    /**
     * sendData
     * 发送数据 send data
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
         * * 处理监控到的串口事件
         */
        public void serialEvent(SerialPortEvent serialPortEvent) {

            switch (serialPortEvent.getEventType()) {

                case SerialPortEvent.BI: // 10通讯中断
                    ShowUtils.errorMessage("与串口设备通讯中断");
                    break;

                case SerialPortEvent.OE: // 7溢位（溢出）错误


                case SerialPortEvent.FE: // 9帧错误


                case SerialPortEvent.PE: // 8奇偶校验错误


                case SerialPortEvent.CD: // 6载波检测


                case SerialPortEvent.CTS: // 3清除待发送数据


                case SerialPortEvent.DSR: // 4待发送数据准备好了


                case SerialPortEvent.RI: // 5振铃指示


                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2输出缓冲区已清空
                    break;

                case SerialPortEvent.DATA_AVAILABLE: // 1串口存在可用数据

                    byte[] data = null;
                    try {
                        if (serialport == null) {
                            ShowUtils.errorMessage("串口对象为空！监听失败！");
                        } else {
                            // 读取串口数据，输出数据
                            data = SerialPortManager.readFromPort(serialport);
                            String output = "";
                            Date date = new Date();
                            DateFormat df3 = new SimpleDateFormat("yyy-MM-dd HH-mm-ss");
                            String dateString = df3.format(date);
                            output += "系统时间: " + dateString + " ";
                            output += "包头: " + ByteUtils.byteArrayToHexString(data, true).substring(0, 4) + " ";
                            output += "短地址: " + ByteUtils.byteArrayToHexString(data, true).substring(4, 8) + " ";
                            output += "MAC: " + ByteUtils.byteArrayToHexString(data, true).substring(9, 17) + ByteUtils.byteArrayToHexString(data, true).substring(19, 27) + " ";
                            output += "电压值: " + ByteUtils.byteArrayToHexString(data, true).substring(27, 31) + " ";
                            output += "节点功能: " + ByteUtils.byteArrayToHexString(data, true).substring(31, 36) + " ";
                            output += "错误标识: " + ByteUtils.byteArrayToHexString(data, true).substring(36, 40) + " ";
                            long temperaturedec = Long.parseLong(ByteUtils.byteArrayToHexString(data, true).substring(40, 44), 16);
                            temp = (int)temperaturedec;
                            output += "温度(十进制): " + temp + " ";
                            output += "温度: " + ByteUtils.byteArrayToHexString(data, true).substring(40, 44) + " ";
                            long humdec = Long.parseLong(ByteUtils.byteArrayToHexString(data, true).substring(45,49), 16);
                            hum = (int)humdec;
                            output += "湿度: " + ByteUtils.byteArrayToHexString(data, true).substring(45, 49) + " ";
                            long illudec = Long.parseLong(ByteUtils.byteArrayToHexString(data, true).substring(49,53), 16);
                            illumination = (int)illudec;
                            if(temp>6657||hum>908)//根据温湿度更改预警旗标
                                warning=true;
                            else
                                warning=false;
                            output += "光照: " + ByteUtils.byteArrayToHexString(data, true).substring(49, 53) + " ";
                            output += "包尾: " + ByteUtils.byteArrayToHexString(data, true).substring(54, 58) + " ";
//						String data1 = ByteUtils.byteArrayToHexString(data, true).substring(40,54);
//						String data1 = ByteUtils.byteArrayToHexString(data, true);
                            System.out.println(output + "\r\n");
                        }
                    } catch (Exception e) {
                        ShowUtils.errorMessage(e.toString());
                        // 发生读取错误时显示错误信息后退出系统
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
