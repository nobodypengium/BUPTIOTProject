package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * 
 * CWLabel -  Rewrite JLabel to make it beautiful
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class CWLabel extends JLabel{
	private static final long serialVersionUID = 23334;
	
	/**
	 * 
	 * CWLabel -   Create an JLabel with beautiful appearance (Can only set font size and position).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWLabel(String name, int fontSize, int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(Color.BLACK);
		this.setHorizontalAlignment(RIGHT);
		this.setBorder(null);
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
	
	/**
	 * 
	 * CWLabel -   Create an JLabel with beautiful appearance (Can also set font color).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param fontColor Font color
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWLabel(String name, int fontSize,Color fontColor ,int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Arial",Font.BOLD,realFontSize));
		this.setForeground(fontColor);
		this.setHorizontalAlignment(RIGHT);
		this.setBorder(null);
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}

	public CWLabel(String name, int fontSize, Color fontColor ,int x, int y, int width, int height, int alignment) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Arial",Font.BOLD,realFontSize));
		this.setForeground(fontColor);
		this.setHorizontalAlignment(alignment);
		this.setBorder(null);
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
}
