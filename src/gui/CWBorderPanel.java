package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CWBorderPanel extends JPanel {
    private static final long serialVersionUID = 33331;
    public CWBorderPanel(int fontSize, int x, int y, int width, int height, String title, Color lineColor, Color titleColor) {
        super();
        int realFontSize = Layout.calFontSize(fontSize);

        //置为透明
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
