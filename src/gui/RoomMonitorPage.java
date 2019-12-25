package gui;

import control.WireSensorTFake;
import control.WirelessSensorT;
import control.WirelessSensorTFake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomMonitorPage extends Thread implements ActionListener {
    private CWFrame roomMonitorFrame;
    private CWBorderPanel wiredNodePanel, wirelessNodePanel;//整个界面分为左右两部分，来自有线传感器的数据和来自无线传感器的数据
    private CWButton backBtn;//返回上个界面（选择）
    private CWLabel wiredTempLabel, wiredHumLabel, wirelessTempLabel, wirelessHumLabel, wiredWarningLabel, wirelessWarningLabel;//有线/无线的温湿度的标签/警报标签
    private CWLabel wiredTempValueLabel, wiredHumValueLabel, wirelessTempValueLabel, wirelessHumValueLabel;//有线/无线的温湿度的数字显示
    public static final ImageIcon background = new ImageIcon(new ImageIcon("src/img/OtherPage.jpg").getImage().getScaledInstance(Layout.getFrameWidth(), Layout.getFrameHeight(), Image.SCALE_DEFAULT));
    private WireSensorTFake wireSensorTFake;//该线程每固定时间读取有线温湿度信息通过DHT11，这个是个接口，用的时候替换掉
    private WirelessSensorTFake wirelessSensorTFake;//该线程使用无线节点，每隔固定时间读取温湿度信息
    private WirelessSensorT wirelessSensorT;//该线程每固定时间读取有线温湿度信息通过DHT11

    public RoomMonitorPage() {
        //Set Values
        roomMonitorFrame = new CWFrame("Intelligent Operating Room System");

        //左半部分，显示DHT11的数据
        wiredNodePanel = new CWBorderPanel(36, 7, 5, 40, 70, "Wired", new Color(144, 12, 62), new Color(144, 12, 62));
        roomMonitorFrame.add(wiredNodePanel);

        //右半部分，显示无线传感器的数据
        wirelessNodePanel = new CWBorderPanel(36, 53, 5, 40, 70, "Wireless", new Color(144, 12, 62), new Color(144, 12, 62));
        roomMonitorFrame.add(wirelessNodePanel);

        //左边2个标签与2个值，有线传感器数据与警报
        wiredTempLabel = new CWLabel("Temp:", 36, new Color(175, 117, 117), 15, 20, 10, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wiredTempLabel);

        wiredTempValueLabel = new CWLabel("66 C", 36, new Color(175, 117, 117), 25, 20, 15, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wiredTempValueLabel);

        wiredHumLabel = new CWLabel("Hum:", 36, new Color(175, 117, 117), 15, 30, 10, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wiredHumLabel);

        wiredHumValueLabel = new CWLabel("88 %", 36, new Color(175, 117, 117), 25, 30, 15, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wiredHumValueLabel);

        wiredWarningLabel = new CWLabel("NORMAL", 36, new Color(175, 117, 117), 19, 50, 16, 5, SwingConstants.CENTER);
        roomMonitorFrame.add(wiredWarningLabel);

        //右边2个标签与2个值，有线传感器数据与警报
        wirelessTempLabel = new CWLabel("Temp:", 36, new Color(175, 117, 117), 61, 20, 10, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wirelessTempLabel);

        wirelessTempValueLabel = new CWLabel("66 C", 36, new Color(175, 117, 117), 71, 20, 15, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wirelessTempValueLabel);

        wirelessHumLabel = new CWLabel("Hum:", 36, new Color(175, 117, 117), 61, 30, 10, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wirelessHumLabel);

        wirelessHumValueLabel = new CWLabel("88 %", 36, new Color(175, 117, 117), 71, 30, 15, 5, SwingConstants.LEFT);
        roomMonitorFrame.add(wirelessHumValueLabel);

        wirelessWarningLabel = new CWLabel("NORMAL", 36, new Color(175, 117, 117), 65, 50, 16, 5, SwingConstants.CENTER);
        roomMonitorFrame.add(wirelessWarningLabel);


        backBtn = new CWButton("BACK", "Microsoft YaHei", 50, new Color(53, 48, 126), 85, 80, 10, 5);
        roomMonitorFrame.add(backBtn);
        backBtn.addActionListener(this);

        JLabel label = new JLabel(background);
        label.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
        JPanel imagePanel = (JPanel) roomMonitorFrame.getContentPane();
        imagePanel.setOpaque(false);
        roomMonitorFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        //TODO:初始化两个传感器
        //创建线程，启动两个传感器的读取，实时读取传感器状态并返回 TODO:换上正确的thread
        wireSensorTFake = new WireSensorTFake();
        wirelessSensorT = new WirelessSensorT();
//        wirelessSensorTFake = new WirelessSensorTFake();
        wireSensorTFake.start();
        wirelessSensorT.start();
//        wirelessSensorTFake.start();
        this.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(backBtn)) {
            roomMonitorFrame.dispose();
            new ChooseFunctionPage();
            this.stop();
        }
    }

    @Override
    public void run() {

        //随时根据传感器获取的温湿度信息更改数据，并进行预警
        while (true) {
            //以下是有线部分
            this.wiredTempValueLabel.setText("" + wireSensorTFake.getTemp()+" C");
            this.wiredHumValueLabel.setText("" + wireSensorTFake.getHum()+" %");
            if (wireSensorTFake.isWarning()) {
                this.wiredWarningLabel.setText("WARNING");
                this.wiredWarningLabel.setForeground(new Color(144, 12, 62));
            } else {
                this.wiredWarningLabel.setText("Normal");
                this.wiredWarningLabel.setForeground(new Color(175, 117, 117));
                //TODO:亮灯
            }
            //以下是无线部分
            this.wirelessTempValueLabel.setText("" + wirelessSensorT.getTemp());
            this.wirelessHumValueLabel.setText("" + wirelessSensorT.getHum());
            if (wirelessSensorT.isWarning()) {
                this.wirelessWarningLabel.setText("WARNING");
                this.wirelessWarningLabel.setForeground(new Color(144, 12, 62));
            } else {
                this.wirelessWarningLabel.setText("Normal");
                this.wirelessWarningLabel.setForeground(new Color(175, 117, 117));
                //TODO:亮灯
            }
        }

    }
}
