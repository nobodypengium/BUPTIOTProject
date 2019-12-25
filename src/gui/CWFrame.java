package gui;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * CWFrame -  Rewrite JFrame to make it beautiful
 * @author Group 15
 * @version v1.0
 * 2019/12/11 16:08:58
 *
 * 2019 Group 15. All rights reserved.
 */
public class CWFrame extends JFrame{

	private static final long serialVersionUID = 23332;
	
	/**
	 * 
	 * CWFrame -   Create an JFrame with beautiful appearance.
	 * @param name Frame name showing on the left up
	 */
	public CWFrame(String name) {
		super(name);
		this.setBounds(Layout.getAnchorX(), Layout.getAnchorY(), Layout.getFrameWidth(), Layout.getFrameHeight());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	/**
	 * 
	 * CWFrame -   Create an JFrame with beautiful appearance and background.
	 * @param name Frame name showing on the left up
	 * @param img Background
	 */
	public CWFrame(String name, ImageIcon img) {
		super(name);
		this.setBounds(Layout.getAnchorX(), Layout.getAnchorY(), Layout.getFrameWidth(), Layout.getFrameHeight());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.RED);
		if(img!=null) {
			JLabel imgLbl = new JLabel(img);
			imgLbl.setBounds(0, 0, Layout.getFrameWidth(), Layout.getFrameHeight());
			JPanel imagePanel = (JPanel) this.getContentPane();
			imagePanel.setOpaque(false);
			JButton fuck = new JButton("ddd");
			this.getContentPane().add(fuck);
		}
		
	}
	
//	public static Image getImage(String path)
//	{
//		BufferedImage bi = null;
//		try {
//			bi = ImageIO.read(new File(path));//.\\src\\img\\LoginPage.jpg
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//		return bi;
//	}
	
}
