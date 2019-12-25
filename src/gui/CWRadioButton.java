package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
/**
 * 
 * CWRadioButton -  Rewrite JRadioButton to make it beautiful
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class CWRadioButton extends JRadioButton {
	private static final long serialVersionUID = 23336;
	
	/**
	 * 
	 * CWRadioButton -   Create an JRadioButton with beautiful appearance (Can only set font size and position)
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWRadioButton(String name, int fontSize, int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(Color.BLACK);
		this.setBorder(null);
		this.setOpaque(false);
		this.setIcon(new ImageIcon());
		this.setIcon(null);
		int realX = Layout.calPosX(x);
		int realY = Layout.calPosY(y);
		int realW = Layout.calPosX(width);
		int realH = Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
	
	/**
	 * 
	 * CWRadioButton -   Create an JRadioButton with beautiful appearance (Can also set font color).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param fontColor Font color
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWRadioButton(String name, int fontSize,Color fontColor ,int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(fontColor);
		this.setBorder(null);
		this.setOpaque(false);
		this.setIcon(new ImageIcon());
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
}
