/**  
* @Title: CWPasswordField.java  
* @Package gui  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
* @author Yang PENG  
* @date 2019/05/06 10:39:27
* @version V1.0  
*/ 
package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.border.MatteBorder;

/**
 * 
 * CWPasswordField -  Rewrite JPasswordField to make it beautiful
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class CWPasswordField extends JPasswordField{
private static final long serialVersionUID = 23340;
	/**
	 * 
	 * CWPasswordField -   Create an JPasswordField with beautiful appearance (Can only set font size and position).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWPasswordField(String name, int fontSize, int x, int y, int width, int height) {
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
	 * CWPasswordField -   Create an JPasswordField with beautiful appearance (Can also set font color).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param borderColor Border Color
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWPasswordField(String name, int fontSize, Color borderColor, int x, int y, int width, int height) {
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
