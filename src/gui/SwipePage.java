package gui;

import control.RFIDReaderT;
import database.XMLOption;
import entity.Doctor;
import test.TestThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 刷卡时显示的页面。
 */
public class SwipePage extends Thread implements ActionListener {
    private CWFrame swipeFrame;
    private CWButton backBtn;//刷卡后显示，点击登录
    private CWLabel swipeStatusLabel,userNameLabel,welcomeLabel;//（一直显示）实时显示刷卡状态，（刷卡后显示）用户名，（刷卡后显示）欢迎信息
    public static final ImageIcon background = new ImageIcon(new ImageIcon("src/img/LoginPage.jpg").getImage().getScaledInstance(Layout.getFrameWidth(), Layout.getFrameHeight(), Image.SCALE_DEFAULT));
    private RFIDReaderT rfidReaderT;//该线程每固定时间读取一次RFID TAG

    public SwipePage(){
        //Set Values
        swipeFrame =new CWFrame("Intelligent Operating Room System");

        //一开始就显示提示刷卡，会随着状态改变提示用户不合法，再试
        swipeStatusLabel =new CWLabel("Please Swipe",36,new Color(175, 117, 117),75,33,15,10, SwingConstants.LEFT);
        swipeFrame.add(swipeStatusLabel);

        //显示医生姓名
        userNameLabel =new CWLabel("Yang Peng",36,new Color(175, 117, 117),75,29,15,5, SwingConstants.LEFT);
        swipeFrame.add(userNameLabel);
        userNameLabel.setVisible(false);

        //显示欢迎信息
        welcomeLabel=new CWLabel("Welcome!",36,new Color(175, 117, 117),75,37,15,5, SwingConstants.LEFT);
        swipeFrame.add(welcomeLabel);
        welcomeLabel.setVisible(false);

        backBtn =new CWButton("Back",36,new Color(44,133,141),75,63,5,5);
        swipeFrame.add(backBtn);
        backBtn.addActionListener(this);

        JLabel label = new JLabel(background);
        label.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
        JPanel imagePanel = (JPanel) swipeFrame.getContentPane();
        imagePanel.setOpaque(false);
        swipeFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        //创建一个线程，启动刷卡，实时读取卡片信息并修改状态值（存在该线程对应的instance里） TODO:换上正确的thread
        rfidReaderT = new RFIDReaderT();
        rfidReaderT.start();
        this.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //退回到最初始欢迎界面LoginPage
        if(e.getSource().equals(backBtn)) {
            swipeFrame.dispose();
            new LoginPage();
        } 
    }

    @Override
    public void run() {
        while(true){
            String epc = rfidReaderT.getEpc();
            if(XMLOption.hasDoctor(epc)){ //如果有这个医生，显示这个医生的姓名1秒并跳到选择功能的界面
                Doctor doctor = XMLOption.getDoctor(epc);
                userNameLabel.setText(doctor.getName());
                userNameLabel.setVisible(true);
                welcomeLabel.setVisible(true);
                swipeStatusLabel.setVisible(false);
                try {
                    Thread.sleep(1000);//防止线程占用过量资源
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new ChooseFunctionPage();
                swipeFrame.dispose();
                rfidReaderT.clear();//清空所有已存储数据，避免下次读取时被上次缓存的数据影响
                this.stop();//Terminate
            } else if(epc==""){//如果还没读到不变
                swipeStatusLabel.setText("Please Swipe");
            } else {
                swipeStatusLabel.setText("<html><body>"+"Auth Failed"+"<br>"+"Try Again!"+"<body></html>");
            }
        }
    }
}
