package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.MonitorInfo;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * ChooseFunctionPage -  The page to choosing function
 * @author Group 15
 * @version v1.0
 * 2019/12/19 15:38:27
 *     
 * 2019 Group 15. All rights reserved.
 */
public class ChooseFunctionPage implements ActionListener{
	private CWFrame chooseFunctionFrame;
	private CWButton registerDoctorBtn, registerPatientBtn, checkPatientBtn, monitorEnvironmentBtn, addRecordBtn, backBtn;
	public static final ImageIcon background = new ImageIcon(new ImageIcon("src/img/LoginPage.jpg").getImage().getScaledInstance(Layout.getFrameWidth(), Layout.getFrameHeight(), Image.SCALE_DEFAULT));
	
	/**
	 * 
	 * ChooseFunctionPage -  Create a page for choose different functions
	 */
	public ChooseFunctionPage(){
		//Set Values
		chooseFunctionFrame=new CWFrame("Intelligent Operating Room System");
		//创建不同功能的按钮
		registerDoctorBtn =new CWButton("Register Doctor",36,new Color(44,133,141),73,27,18,5);
		chooseFunctionFrame.add(registerDoctorBtn);
		registerDoctorBtn.setHorizontalAlignment(JButton.LEFT);
		registerDoctorBtn.addActionListener(this);
		

		registerPatientBtn =new CWButton("Register Patient",36,new Color(44,133,141),73,33,18,5);
		chooseFunctionFrame.add(registerPatientBtn);
		registerPatientBtn.setHorizontalAlignment(JButton.LEFT);
		registerPatientBtn.addActionListener(this);
		
		checkPatientBtn =new CWButton("Check Records",36,new Color(44,133,141),73,39,18,5);
		chooseFunctionFrame.add(checkPatientBtn);
		checkPatientBtn.setHorizontalAlignment(JButton.LEFT);
		checkPatientBtn.addActionListener(this);
		
		monitorEnvironmentBtn =new CWButton("Environment Monitor",36,new Color(44,133,141),73,45,25,5);
		chooseFunctionFrame.add(monitorEnvironmentBtn);
		monitorEnvironmentBtn.setHorizontalAlignment(JButton.LEFT);
		monitorEnvironmentBtn.addActionListener(this);

		addRecordBtn =new CWButton("Add Record",36,new Color(44,133,141),73,51,18,5);
		chooseFunctionFrame.add(addRecordBtn);
		addRecordBtn.setHorizontalAlignment(JButton.LEFT);
		addRecordBtn.addActionListener(this);

		backBtn = new CWButton("Logout","Microsoft YaHei",50,new Color(53,48,126),75,74,20,5);
		chooseFunctionFrame.add(backBtn);
		backBtn.setHorizontalAlignment(JButton.RIGHT);
		backBtn.addActionListener(this);
		
		JLabel label = new JLabel(background);
		label.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
		JPanel imagePanel = (JPanel) chooseFunctionFrame.getContentPane();
		imagePanel.setOpaque(false);
		chooseFunctionFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	}
	
	/**   
	 * <p>Title: actionPerformed</p>   
	 * <p>Description: Listen to the action and make action when specific event occurs.</p>   
	 * @param e Event
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)   
	 */  
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(registerDoctorBtn)) {
			chooseFunctionFrame.dispose();
			new RegisterDoctorPage();
		}else if(e.getSource().equals(registerPatientBtn)) {
			chooseFunctionFrame.dispose();
			new RegisterPatientPage();
		}else if(e.getSource().equals(checkPatientBtn)) {
			chooseFunctionFrame.dispose();
			new CheckRecordPage();
		}else if(e.getSource().equals(monitorEnvironmentBtn)) {
			chooseFunctionFrame.dispose();
			new RoomMonitorPage();
		}else if(e.getSource().equals(addRecordBtn)){
			chooseFunctionFrame.dispose();
			new AddRecordPage();
		} else if(e.getSource().equals(backBtn)) {
			chooseFunctionFrame.dispose();
			new LoginPage();
		}
	}
}
