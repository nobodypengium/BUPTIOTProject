/*
 * MainFrame.java
 *
 * Created on 2016.8.19
 */

package com.yang.serialport.ui;

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
public class MainFrame extends JFrame {
	/**  *
	 程序界面宽度
	 */
	public static final int WIDTH = 1300;

	/**  *
	 程序界面高度
	 */
	public static final int HEIGHT = 400;

	private JTextArea dataView = new JTextArea();
	private JScrollPane scrollDataView = new JScrollPane(dataView);

//串口设置面板
	private JPanel serialPortPanel = new JPanel();
	private JLabel serialPortLabel = new JLabel("串口");
	private JLabel baudrateLabel = new JLabel("波特率");
	private JComboBox commChoice = new JComboBox();
	private JComboBox baudrateChoice = new JComboBox();

//操作面板

	private JPanel operatePanel = new JPanel();
	private JTextField dataInput = new JTextField();
	private JButton serialPortOperate = new JButton("打开串口");
	private JButton sendData = new JButton("发送数据");

	private List<String> commList = null; private SerialPort serialport;

	public MainFrame() {
		initView();
		initComponents();
		actionListener();
		initData();
	}
	private void initView() {    // 关闭程序
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);    // 禁止窗口最大化
		setResizable(true);

		//设置程序窗口居中显示
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment()          .getCenterPoint();
		setBounds(p.x - WIDTH / 2, p.y - HEIGHT / 2, WIDTH, HEIGHT);
		this.setLayout(null);

		setTitle("串口通讯"); }

	private void initComponents() {    // 数据显示
		dataView.setFocusable(false);
		scrollDataView.setBounds(10, 10, 1200, 200);
		add(scrollDataView);

		//串口设置
		serialPortPanel.setBorder(BorderFactory.createTitledBorder("串口设置"));
		serialPortPanel.setBounds(10, 220, 170, 100);
		serialPortPanel.setLayout(null);
		add(serialPortPanel);

		serialPortLabel.setForeground(Color.gray);
		serialPortLabel.setBounds(10, 25, 40, 20);
		serialPortPanel.add(serialPortLabel);

		commChoice.setFocusable(false);
		commChoice.setBounds(60, 25, 100, 20);
		serialPortPanel.add(commChoice);

		baudrateLabel.setForeground(Color.gray);
		baudrateLabel.setBounds(10, 60, 40, 20);
		serialPortPanel.add(baudrateLabel);

		baudrateChoice.setFocusable(false);
		baudrateChoice.setBounds(60, 60, 100, 20);
		serialPortPanel.add(baudrateChoice);

		//操作
		operatePanel.setBorder(BorderFactory.createTitledBorder("操作"));
		operatePanel.setBounds(200, 220, 285, 100);
		operatePanel.setLayout(null);
		add(operatePanel);

		dataInput.setBounds(25, 25, 235, 20);
		operatePanel.add(dataInput);

		serialPortOperate.setFocusable(false);
		serialPortOperate.setBounds(45, 60, 90, 20);
		operatePanel.add(serialPortOperate);

		sendData.setFocusable(false);
		sendData.setBounds(155, 60, 90, 20);
		operatePanel.add(sendData); }

	private void actionListener() {
		serialPortOperate.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("打开串口".equals(serialPortOperate.getText())
					&& serialport == null) {
				openSerialPort(e);
			} else {
				closeSerialPort(e);
			}
		}
		}
		);

		sendData.addActionListener(new ActionListener() {

			@Override       public void actionPerformed(ActionEvent e) {
				sendData(e);
			}
		}
		);
	}

	private void initData() {
		commList = SerialPortManager.findPort();    // 检查是否有可用串口，有则加入选项中
		if (commList == null || commList.size() < 1) {
			ShowUtils.warningMessage("没有搜索到有效串口！");
		} else {
			for (String s : commList) {
				commChoice.addItem(s);
			}
		}

		baudrateChoice.addItem("9600");
		baudrateChoice.addItem("19200");
		baudrateChoice.addItem("38400");
		baudrateChoice.addItem("57600");
		baudrateChoice.addItem("115200"); }

	/**
	 *  openSerialPort
	 *  打开串口  open the serial port
	 * 
	 * @param evt
	 *            点击事件 Click event
	 */
	private void openSerialPort(java.awt.event.ActionEvent evt) {    // 获取串口名称
		String commName = (String) commChoice.getSelectedItem();    // 获取波特率

		int baudrate = 9600;
		String bps = (String) baudrateChoice.getSelectedItem();
		baudrate = Integer.parseInt(bps);

		//检查串口名称是否获取正确
		if (commName == null || commName.equals("")) {
			ShowUtils.warningMessage("没有搜索到有效串口！");
		} else {
			try {
				serialport = SerialPortManager.openPort(commName, baudrate);
				if (serialport != null) {
					dataView.setText("串口已打开" + "\r\n");
					serialPortOperate.setText("关闭串口");
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
		}

		try {
			SerialPortManager.addListener(serialport, new SerialListener());
		} catch (TooManyListeners e) {
			e.printStackTrace();
		}
	}


	/**
	 * closeSerialPort
	 * 关闭串口 close the serial port
	 * 
	 * @param evt
	 *            点击事件 Click event
	 */
	private void closeSerialPort(java.awt.event.ActionEvent evt) {
		SerialPortManager.closePort(serialport);
		dataView.setText("串口已关闭" + "\r\n");
		serialPortOperate.setText("打开串口");
		serialport = null;
	}


	/**
	 * sendData
	 * 发送数据 send data
	 * 
	 * @param evt
	 *            点击事件 Click event
	 */
	private void sendData(java.awt.event.ActionEvent evt) {
		String data = dataInput.getText().toString();
		try {
			SerialPortManager.sendToPort(serialport,
				ByteUtils.hexStr2Byte(data));
		} catch (SendDataToSerialPortFailure e) {
			e.printStackTrace();
		} catch (SerialPortOutputStreamCloseFailure e) {
			e.printStackTrace();
		}
	}

	private class SerialListener implements SerialPortEventListener {
		/**
		 *  * 处理监控到的串口事件
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
						output += "包头: " + ByteUtils.byteArrayToHexString(data, true).substring(0,4) + " ";
						output += "短地址: " + ByteUtils.byteArrayToHexString(data, true).substring(4,8) + " ";
						output += "MAC: " + ByteUtils.byteArrayToHexString(data, true).substring(9,17) + ByteUtils.byteArrayToHexString(data, true).substring(19,27) + " ";
						output += "电压值: " + ByteUtils.byteArrayToHexString(data, true).substring(27,31) + " ";
						output += "节点功能: " + ByteUtils.byteArrayToHexString(data, true).substring(31,36) + " ";
						output += "错误标识: " + ByteUtils.byteArrayToHexString(data, true).substring(36,40) + " ";
						long temperaturedec = Long.parseLong(ByteUtils.byteArrayToHexString(data, true).substring(40, 44), 16);
						output += "温度(十进制): " + temperaturedec + " ";
						output += "温度: " + ByteUtils.byteArrayToHexString(data, true).substring(40,44) + " ";
						output += "湿度: " + ByteUtils.byteArrayToHexString(data, true).substring(45,49) + " ";
						output += "光照: " + ByteUtils.byteArrayToHexString(data, true).substring(49,53) + " ";
						output += "包尾: " + ByteUtils.byteArrayToHexString(data, true).substring(54,58) + " ";
//						String data1 = ByteUtils.byteArrayToHexString(data, true).substring(40,54);
//						String data1 = ByteUtils.byteArrayToHexString(data, true);
						dataView.append(output+ "\r\n");
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
	/**
	 * main
	 * 主函数 main
	 *
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame().setVisible(true);
			}
		}
		);
	}
}
