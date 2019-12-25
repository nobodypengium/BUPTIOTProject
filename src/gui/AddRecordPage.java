package gui;

import control.RFIDReaderT;
import database.XMLOption;
import entity.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class AddRecordPage extends Thread implements ActionListener {
    private CWFrame addRecordFrame;
    private CWButton addRecordBtn,backBtn,swipeBtn;
    private CWTextArea recordTextArea;
    private CWLabel epcLabel, nameLabel,nameValueLabel, epcValueLabel,infoLabel;
    private CWBorderPanel infoPanel;
    //读取EPC线程
    private RFIDReaderT rfidReaderT;
    //添加背景
    public static final ImageIcon background = new ImageIcon(new ImageIcon("src/img/OtherPage.jpg").getImage()
            .getScaledInstance(Layout.getFrameWidth(), Layout.getFrameHeight(), Image.SCALE_DEFAULT));
    public AddRecordPage(){
        // Set Values
        addRecordFrame = new CWFrame("Intelligent Operating Room System");

        epcLabel = new CWLabel("EPC: ", 36, new Color(175, 117, 117), 3, 5, 10, 5,SwingConstants.LEFT);
        addRecordFrame.add(epcLabel);

        nameLabel = new CWLabel("Name: ", 36, new Color(175, 117, 117), 3, 10, 10, 5,SwingConstants.LEFT);
        addRecordFrame.add(nameLabel);

        epcValueLabel = new CWLabel("", 36, new Color(175, 117, 117), 13, 5, 35, 5,SwingConstants.LEFT);
        addRecordFrame.add(epcValueLabel);

        nameValueLabel = new CWLabel("", 36, new Color(175, 117, 117), 13, 10, 35, 5,SwingConstants.LEFT);
        addRecordFrame.add(nameValueLabel);

        recordTextArea = new CWTextArea(36,5,15,40,40,"Record Input",new Color(175, 117, 117),new Color(175, 117, 117),new Color(175, 117, 117));
        addRecordFrame.add(recordTextArea);

        swipeBtn = new CWButton("Swipe", 41, new Color(44, 133, 141), 24, 63, 26, 5);
        swipeBtn.setHorizontalAlignment(JButton.CENTER);
        addRecordFrame.add(swipeBtn);
        swipeBtn.addActionListener(this);

        addRecordBtn = new CWButton("Add Record", 41, new Color(44, 133, 141), 50, 63, 26, 5);
        addRecordBtn.setHorizontalAlignment(JButton.CENTER);
        addRecordFrame.add(addRecordBtn);
        addRecordBtn.addActionListener(this);

        infoPanel = new CWBorderPanel(36, 55, 7, 40, 40, "Info", new Color(144, 12, 62), new Color(144, 12, 62));
        addRecordFrame.add(infoPanel);

        infoLabel = new CWLabel("", 36, new Color(144, 12, 62), 55, 9, 40,
                34);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        addRecordFrame.add(infoLabel);
//        infoLabel.setVisible(false);

        backBtn = new CWButton("BACK","Microsoft YaHei",50,new Color(53,48,126),85,80,10,5);
        addRecordFrame.add(backBtn);
        backBtn.addActionListener(this);

        JLabel label = new JLabel(background);
        label.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
        JPanel imagePanel = (JPanel) addRecordFrame.getContentPane();
        imagePanel.setOpaque(false);
        addRecordFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

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
        } else if(e.getSource().equals(addRecordBtn)){//添加病历
            Patient tmpPatient = XMLOption.getPatient(this.epcValueLabel.getText()); //根据EPC码读出病人
            int errorCode = XMLOption.changePatient(tmpPatient.getEpc(),"record",tmpPatient.getRecord()+this.recordTextArea.getText());
            //通过html换行将信息显示到一个label里
            if(errorCode==0)//0成功
                this.infoLabel.setText("<html><body>"+"NAME:"+tmpPatient.getName()+"<br>"+"RECORD:"+tmpPatient.getRecord()+this.recordTextArea.getText()+"<body></html>");
            else
                this.infoLabel.setText("No such patient!");
        } else if(e.getSource().equals(backBtn)){
            new ChooseFunctionPage();
            addRecordFrame.dispose();
            rfidReaderT.clear();
            rfidReaderT.stop();
            this.stop();
        }
    }

    @Override
    public void run() {
        while(true){
            if(rfidReaderT.getEpc()!=""){
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
