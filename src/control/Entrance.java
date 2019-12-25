package control;

import java.util.Timer;
import java.util.TimerTask;

import com.uhf.linkage.Linkage;
import gui.ChooseFunctionPage;
import gui.LoginPage;

/**   
 * Entrance -  The entrance of the program.
 * @author Group 15
 * @version v1.0
 * 2019/12/11 19:13:54
 *     
 * 2019 Group 15. All rights reserved.
 */
public class Entrance {
	public static void main(String args[]) throws InterruptedException {
		//初始化设备（读tag的设备 RFID reader）
		int i =  Linkage.getInstance().initial("COM4");
		if(i != 0){
			System.out.println("Cannot connect!");
		}

		new ChooseFunctionPage();
	}
}
