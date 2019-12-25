package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
/**
 * 
 * CWButton -  Rewrite JButton to make it beautiful
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *     
 * 2019 Group 15. All rights reserved.
 */
@SuppressWarnings("unused")
public class CWButton extends JButton{

	private static final long serialVersionUID = 23331;
	
	/**
	 * 
	 * CWButton -   Create an JButton with beautiful appearance (Can only set font size and position).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWButton(String name, int fontSize, int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(Color.BLACK);
		this.setBorder(null);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
	
	/**
	 * 
	 * CWButton -   Create an java.awt button with beautiful appearance (Can also set font color).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param fontColor Font color
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWButton(String name, int fontSize, Color fontColor, int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(fontColor);
		this.setBorder(null);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
	
	/**
	 * 
	 * CWButton -   Create an java.awt button with beautiful appearance (Can also set font color and font type).
	 * @param name Text showing on the button
	 * @param fonttype Font type
	 * @param fontSize Font size
	 * @param fontColor Font color
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWButton(String name, String fonttype, int fontSize, Color fontColor, int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(fontColor);
		this.setBorder(null);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		int realX=Layout.calPosX(x);//通过百分比数据计算实际的像素数值
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
}
