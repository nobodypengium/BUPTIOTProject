package test;

import com.yang.serialport.exception.NoSuchPort;
import com.yang.serialport.exception.NotASerialPort;
import com.yang.serialport.exception.PortInUse;
import com.yang.serialport.exception.SerialPortParameterFailure;
import com.yang.serialport.manage.SerialPortManager;
import com.yang.serialport.utils.ByteUtils;
import com.yang.serialport.utils.ShowUtils;
import control.WirelessSensorT;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp on 2019/12/19.
 */
public class TestSerial {

    public static void main(String args[]){
        WirelessSensorT t = new WirelessSensorT();
        t.run();
    }
}
