package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

/**
 * 
 * CWTitleLabel -  Rewrite JLabel to make it beautiful
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class CWTitleLabel extends JLabel{
	private static final long serialVersionUID = 23336;
	/**
	 * 
	 * CWTitleLabel -   Create an JTitleLabel with beautiful appearance (Can also set font color).
	 * @param name Text showing on the button
	 * @param fontSize Font size
	 * @param fontColor Font color
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 * @param title Word to show on the left up of the boarder
	 * @param lineColor Boarder color
	 * @param titleColor Title(on the boarder) color
	 */
	public CWTitleLabel(String name, int fontSize, Color fontColor ,int x, int y, int width, int height, String title, Color lineColor, Color titleColor) {
		super(name);
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Comic Sans MS",Font.BOLD,realFontSize));
		this.setForeground(fontColor);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		this.setBorder(null);
		
		if(title!="") {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lineColor,5),title,TitledBorder.LEFT,TitledBorder.CENTER,new Font("Comic Sans MS",Font.BOLD,realFontSize),titleColor));
		}
		
		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
}
