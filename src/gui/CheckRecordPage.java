package gui;

import control.RFIDReaderT;
import database.XMLOption;
import entity.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckRecordPage extends Thread implements ActionListener {
    private CWFrame checkRecordFrame;
    private CWButton checkBtn, backBtn, swipeBtn;
    private CWLabel epcLabel, nameLabel,nameValueLabel, epcValueLabel, infoLabel;
    private CWBorderPanel infoPanel;
    //读取EPC线程
    private RFIDReaderT rfidReaderT;
    //添加背景
    public static final ImageIcon background = new ImageIcon(new ImageIcon("src/img/OtherPage.jpg").getImage()
            .getScaledInstance(Layout.getFrameWidth(), Layout.getFrameHeight(), Image.SCALE_DEFAULT));

    public CheckRecordPage(){
        // Set Values
        checkRecordFrame = new CWFrame("Intelligent Operating Room System");

        epcLabel = new CWLabel("EPC: ", 36, new Color(175, 117, 117), 3, 25, 15, 5,SwingConstants.LEFT);
        checkRecordFrame.add(epcLabel);

        nameLabel = new CWLabel("Name: ", 36, new Color(175, 117, 117), 3, 35, 15, 5,SwingConstants.LEFT);
        checkRecordFrame.add(nameLabel);

        epcValueLabel = new CWLabel("", 36, new Color(175, 117, 117), 19, 25, 34, 5, SwingConstants.LEFT);
        checkRecordFrame.add(epcValueLabel);

        nameValueLabel = new CWLabel("", 36, new Color(175, 117, 117), 19, 35, 34, 5,SwingConstants.LEFT);
        checkRecordFrame.add(nameValueLabel);

        swipeBtn = new CWButton("Swipe", 41, new Color(44, 133, 141), 24, 63, 26, 5);
        swipeBtn.setHorizontalAlignment(JButton.CENTER);
        checkRecordFrame.add(swipeBtn);
        swipeBtn.addActionListener(this);

        checkBtn = new CWButton("Check Record", 41, new Color(44, 133, 141), 50, 63, 26, 5);
        checkBtn.setHorizontalAlignment(JButton.CENTER);
        checkRecordFrame.add(checkBtn);
        checkBtn.addActionListener(this);

        infoPanel = new CWBorderPanel(36, 55, 7, 40, 40, "Info", new Color(144, 12, 62), new Color(144, 12, 62));
        checkRecordFrame.add(infoPanel);

        infoLabel = new CWLabel("", 36, new Color(144, 12, 62), 55, 9, 40,
                34);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        checkRecordFrame.add(infoLabel);
//        infoLabel.setVisible(false);

        backBtn = new CWButton("BACK", "Microsoft YaHei", 50, new Color(53, 48, 126), 85, 80, 10, 5);
        checkRecordFrame.add(backBtn);
        backBtn.addActionListener(this);

        JLabel label = new JLabel(background);
        label.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
        JPanel imagePanel = (JPanel) checkRecordFrame.getContentPane();
        imagePanel.setOpaque(false);
        checkRecordFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        //读取tag用线程
        rfidReaderT = new RFIDReaderT();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(swipeBtn)){//刷卡，每个病人一张卡，卡有唯一EPC
            this.infoLabel.setText("Swiping...");
            this.epcValueLabel.setText("");
            this.nameValueLabel.setText("");
            if(rfidReaderT.getState()!=State.TIMED_WAITING){//查询线程状态，如果还没被创建则创建，如果已经被创建则继续运行
                rfidReaderT.start();
                this.start();//启动获取读取到的Tag值并根据其更改界面元素
            }else{
                rfidReaderT.resume();
                this.resume();
            }
        } else if(e.getSource().equals(checkBtn)){//显示病人信息，包括姓名和病历
            Patient tmpPatient = XMLOption.getPatient(this.epcValueLabel.getText());
            if(tmpPatient==null){//读不出来显示错误信息
                this.infoLabel.setText("Error: No such patient!");
            } else{//读出来显示病历
                this.infoLabel.setText("<html><body>"+"NAME:"+tmpPatient.getName()+"<br>"+"RECORD:"+tmpPatient.getRecord()+"<body></html>");
            }
            //通过html换行将信息显示到一个label里
        } else if(e.getSource().equals(backBtn)){
            new ChooseFunctionPage();
            checkRecordFrame.dispose();
            rfidReaderT.stop();
            this.stop();
        }
    }

    @Override
    public void run() {
        while(true){
            if(rfidReaderT.getEpc() !=""){
                String tmpEpc = rfidReaderT.getEpc();
                epcValueLabel.setText(tmpEpc);
                infoLabel.setText("Tag Scanned!");
                Patient tmpPatient = XMLOption.getPatient(tmpEpc);
                if(tmpPatient!=null){
                    nameValueLabel.setText(tmpPatient.getName());//有这个病人把名字显示到界面上
                }else{
                    infoLabel.setText("No such patient!");//如果EPC错误，那么XML库里查不到这个病人，返回null
                }
                rfidReaderT.clear();//清空所有已存储数据，避免下次读取时被上次缓存的数据影响
                rfidReaderT.suspend();//读取到了tag信息将线程们放到wait状态
                this.suspend();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
