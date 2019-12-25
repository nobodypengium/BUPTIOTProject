package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * 
 * Layout -  A boarder class to control the layout. It is the core of auto adaption of resolution ratio on different screen.
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class Layout {
	private static double framePrompX = 0.6, framePrompY=0.6;
	private static double frameAnchorPrompX=0.2,frameAnchorPrompY=0.2;
	public static final ImageIcon bg_cv = new ImageIcon("src/icon/img.jpg");
	
	/**
	 * 
	 * getFrameWidth -  Get the frame width suitable to users screen.
	 * @return      
	 * int - Absolute frame width
	 */
	public static int getFrameWidth() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)((double)screenSize.width*(double)framePrompX);
		return width;
	}
	
	/**
	 * 
	 * getFrameHeight -  Get the frame height suitable to users screen. 
	 * @return      
	 * int - Absolute frame height
	 */
	public static int getFrameHeight() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height=(int)((double)screenSize.height*(double)framePrompY);
		return height;
	}
	
	/**
	 * 
	 * getAnchorX - Get the anchor position of frame (left up point)
	 * @return      
	 * int - Absolute anchor point position.
	 */
	public static int getAnchorX() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int anchorX = (int)((double)screenSize.width*(double)frameAnchorPrompX);
		return anchorX;
	}
	
	/**
	 * 
	 * getAnchorY -  Get the anchor position of frame (left up point)
	 * @return      
	 * int - Absolute anchor point position.
	 */
	public static int getAnchorY() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int anchorY = (int)((double)screenSize.height*(double)frameAnchorPrompY);
		return anchorY;
	}
	
	/**
	 * 
	 * calPosX -  Calculate the position of swings by given relative position
	 * @param relativePos Relative position of swings (in percentage)
	 * @return      
	 * int - Absolute position of swings.
	 */
	public static int calPosX(int relativePos) {
		int realPos=relativePos*Layout.getFrameWidth()/100;
		return realPos;
	}
	
	/**
	 * 
	 * calPosY -  Calculate the position of swings by given relative position
	 * @param relativePos Relative position of swings (in percentage)
	 * @return      
	 * int - Absolute position of swings.
	 */
	public static int calPosY(int relativePos) {
		int realPos=relativePos*Layout.getFrameHeight()/100;
		return realPos;
	}

	/**
	 * 根据屏幕对角线信息向上取整算映射后的字体大小
	 * @param relativeFontSize
	 * @return
	 */
	public static int calFontSize(int relativeFontSize){
		double diagonal = Math.sqrt(Math.pow(Layout.getFrameHeight(),2)+Math.pow(Layout.getFrameWidth(),2));
		int realFontSize = (int) (relativeFontSize/(diagonal/917.36));
		realFontSize++;
//		realFontSize -= 11;
		return  realFontSize;
	}
	
}
