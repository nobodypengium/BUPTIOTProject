package gui;

import java.awt.*;


import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

/**
 *
 * CWTextField -  Rewrite JTextField to make it beautiful
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class CWTextField extends JTextField{
	private static final long serialVersionUID = 23335;
	
	/**
	 * 
	 * CWTextField - Create an JTextField with beautiful appearance (Can only set font size and position)
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWTextField(String name, int fontSize, int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(Color.BLACK);
		this.setBorder(null);
		this.setOpaque(false);
		MatteBorder border = new MatteBorder(0,0,2,0,Color.BLACK);
		this.setBorder(border);
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
	
	/**
	 * 
	 * CWTextField - Create an JTextField with beautiful appearance (Can also set font color).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param borderColor Border color
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWTextField(String name, int fontSize, Color borderColor, int x, int y, int width, int height) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(borderColor);
		this.setBorder(null);
		this.setOpaque(false);
		MatteBorder border = new MatteBorder(0,0,2,0,borderColor);
		this.setBorder(border);
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
}
