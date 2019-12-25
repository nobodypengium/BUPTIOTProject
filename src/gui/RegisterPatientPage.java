package gui;

import control.RFIDReaderT;
import database.XMLOption;
import entity.Doctor;
import entity.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class RegisterPatientPage extends Thread implements ActionListener {
    private CWFrame registerPatientFrame;
    private CWButton registerBtn,backBtn,swipeBtn;
    private CWTextField nameTF;
    private CWLabel epcLabel, nameLabel, epcValueLabel,infoLabel;
    private CWBorderPanel infoPanel;
    //读取EPC线程
    private RFIDReaderT rfidReaderT;
    //添加背景
    public static final ImageIcon background = new ImageIcon(new ImageIcon("src/img/OtherPage.jpg").getImage()
            .getScaledInstance(Layout.getFrameWidth(), Layout.getFrameHeight(), Image.SCALE_DEFAULT));
    public RegisterPatientPage(){
        // Set Values
        registerPatientFrame = new CWFrame("Intelligent Operating Room System");

        epcLabel = new CWLabel("EPC: ", 36, new Color(175, 117, 117), 3, 25, 15, 5);
        registerPatientFrame.add(epcLabel);

        nameLabel = new CWLabel("Name: ", 36, new Color(175, 117, 117), 3, 35, 15, 5);
        registerPatientFrame.add(nameLabel);

        epcValueLabel = new CWLabel("", 36, new Color(175, 117, 117), 19, 25, 34, 5,SwingConstants.LEFT);
        registerPatientFrame.add(epcValueLabel);

        nameTF = new CWTextField("", 36, new Color(175, 117, 117), 19, 35, 34, 5);
        registerPatientFrame.add(nameTF);

        swipeBtn = new CWButton("Swipe", 41, new Color(44, 133, 141), 24, 63, 26, 5);
        swipeBtn.setHorizontalAlignment(JButton.CENTER);
        registerPatientFrame.add(swipeBtn);
        swipeBtn.addActionListener(this);

        registerBtn = new CWButton("Register Patient", 41, new Color(44, 133, 141), 50, 63, 26, 5);
        registerBtn.setHorizontalAlignment(JButton.CENTER);
        registerPatientFrame.add(registerBtn);
        registerBtn.addActionListener(this);

        infoPanel = new CWBorderPanel(36, 55, 7, 40, 40, "Info", new Color(144, 12, 62), new Color(144, 12, 62));
        registerPatientFrame.add(infoPanel);

        infoLabel = new CWLabel("", 36, new Color(144, 12, 62), 55, 9, 40,
                34);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        registerPatientFrame.add(infoLabel);
//        infoLabel.setVisible(false);

        backBtn = new CWButton("BACK","Microsoft YaHei",50,new Color(53,48,126),85,80,10,5);
        registerPatientFrame.add(backBtn);
        backBtn.addActionListener(this);

        JLabel label = new JLabel(background);
        label.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
        JPanel imagePanel = (JPanel) registerPatientFrame.getContentPane();
        imagePanel.setOpaque(false);
        registerPatientFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

        //读取tag用线程
        rfidReaderT = new RFIDReaderT();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(swipeBtn)){
            this.infoLabel.setText("Swiping...");
            this.epcValueLabel.setText("");
            if(rfidReaderT.getState()!=State.TIMED_WAITING){//查询线程状态，如果还没被创建则创建，如果已经被创建则继续运行
                rfidReaderT.start();
                this.start();//启动获取读取到的Tag值并根据其更改界面元素
            }else{
                rfidReaderT.resume();
                this.resume();
            }
        } else if(e.getSource().equals(registerBtn)){
            Patient patient = new Patient(nameTF.getText(),epcValueLabel.getText());
            int id = XMLOption.setPatient(patient);//将欲创建的医生信息写入XML数据库并返回ID
            DecimalFormat df = new DecimalFormat("00000");
            String idStr = df.format(id);
            //通过html换行将信息显示到一个label里
            this.infoLabel.setText("<html><body>"+"NAME:"+this.nameTF.getText()+"<br>"+"EPC:"+this.epcValueLabel.getText()+"<br>"+"ID:"+idStr+"<body></html>");
        }else if(e.getSource().equals(backBtn)){
            registerPatientFrame.dispose();
            new ChooseFunctionPage();
            rfidReaderT.stop();
            this.stop();
        }
    }

    @Override
    public void run() {
        while(true){
            if(rfidReaderT.getEpc() !=""){
                epcValueLabel.setText(rfidReaderT.getEpc());
                infoLabel.setText("Tag Scanned!");
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
