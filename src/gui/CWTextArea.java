package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class CWTextArea extends JTextArea {
	private static final long serialVersionUID = 23333;
	
	/**
	 * 
	 * CWTextArea -   Create an JTextArea with beautiful appearance
	 * @param fontSize Font size
	 * @param x Relative position for x axis
	 * @param y Relative position for y axis
	 * @param width Relative width
	 * @param height Relative height
	 */
	public CWTextArea(int fontSize, int x, int y, int width, int height, String title, Color lineColor, Color titleColor,Color fontColor) {
		int realFontSize = Layout.calFontSize(fontSize);
		this.setFont(new Font("Arial",Font.BOLD,realFontSize));
		this.setForeground(fontColor);

		this.setBackground(null);
		this.setOpaque(false);

		if(title!="") {
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lineColor,5),title, TitledBorder.LEFT,TitledBorder.CENTER,new Font("Comic Sans MS",Font.BOLD,realFontSize),titleColor));
		}

		int realX=Layout.calPosX(x);
		int realY=Layout.calPosY(y);
		int realW=Layout.calPosX(width);
		int realH=Layout.calPosY(height);
		this.setBounds(realX, realY, realW, realH);
	}
	
}
