package gui;

import javax.swing.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;

/**
 * 
 * LoginPage -  GUI for entering the system
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class LoginPage implements ActionListener{
	//所有的swing控件的变量全部是私有变量，都定义在每个窗口类的最前面
	private CWFrame loginFrame;
	private CWButton enterBtn, exitBtn;
	public static final ImageIcon background = new ImageIcon(new ImageIcon("src/img/LoginPage.jpg").getImage().getScaledInstance(Layout.getFrameWidth(), Layout.getFrameHeight(), Image.SCALE_DEFAULT));
	
	/**
	 * 
	 * LoginPage - Create a frame for entering the system.
	 */
	public LoginPage(){
		//Set Values
		loginFrame=new CWFrame("Intelligent Operating Room System");

		//每个控件自成段落
		enterBtn=new CWButton("Enter System",36,new Color(44,133,141),73,50,15,5);
		loginFrame.add(enterBtn);
		enterBtn.addActionListener(this);
		
		exitBtn=new CWButton("Exit",36,new Color(44,133,141),73,57,5,5);
		loginFrame.add(exitBtn);
		exitBtn.addActionListener(this);

		//设置背景
		JLabel label = new JLabel(background);
		label.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
		JPanel imagePanel = (JPanel) loginFrame.getContentPane();
		imagePanel.setOpaque(false);
		loginFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	}
	
	/**   
	 * <p>Title: actionPerformed</p>   
	 * <p>Description: Make action to the swing event.</p>   
	 * @param e Event
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)   
	 */  
	@Override
	public void actionPerformed(ActionEvent e) {
		//根据事件的来源（如按钮）调用函数
		if(e.getSource().equals(enterBtn)) {
			loginFrame.dispose();//关闭界面
			new SwipePage();//创建新的界面
		}
		else if(e.getSource().equals(exitBtn)) {
			loginFrame.dispose();
		}
	}
	
	
}
