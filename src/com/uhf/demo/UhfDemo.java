package com.uhf.demo;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.uhf.detailwith.InventoryDetailWith;
import com.uhf.linkage.Linkage;
import com.uhf.structures.InventoryArea;
import com.uhf.structures.RwData;
import com.uhf.utils.StringUtils;
import com.uhf.structures.FunctionSpecific;

public class UhfDemo {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		boolean startFlag = true;
		int i =  Linkage.getInstance().initial("COM3");

		if(i != 0){
			System.out.println("Cannot connect!");
		}

		while(startFlag && i == 0){
			//Get all information.
			FunctionSpecific functionSpecific = new FunctionSpecific(); // Store the specific info
			String specificFunctionStr = ""; // Store the specific info as string to show user and check
			boolean haveRun = false;

			Scanner sc = new Scanner(System.in);
			System.out.println("Choose a function: (1) Read, (2) Write, (3) Inventory");
			functionSpecific.function = sc.nextInt();
			specificFunctionStr += functionSpecific.func2String();
			System.out.println("Specify the start address: ");
			functionSpecific.startAddress = sc.nextInt();
			specificFunctionStr += functionSpecific.startAddress;
			System.out.println("Specify the word length: ");
			functionSpecific.wordLen = sc.nextInt();
			specificFunctionStr += functionSpecific.wordLen;

			// Read/Write
			if(functionSpecific.function != 3){
				System.out.println("Choose memBank: (1) EPC,(2) USER,(3) TID");
				functionSpecific.memBank = sc.nextInt();
				specificFunctionStr += functionSpecific.memBank2String();
			} else if (functionSpecific.function == 3){
				System.out.println("Choose area: (0) Only EPC,(1) EPC + TID,(2) EPC + USER");
				functionSpecific.area = sc.nextInt();
				specificFunctionStr += functionSpecific.area2String();
				System.out.println("Times you want to inventory:");
				functionSpecific.maxNum = sc.nextInt();
				specificFunctionStr += functionSpecific.maxNum;
			}


			// If we need to write to the tag specified by EPC
			if(functionSpecific.function==2 && functionSpecific.memBank == 1){
				System.out.println("Would you like to specify the tag by EPC? (1) Specify (2) Not Specify");
				functionSpecific.specify = sc.nextInt();
				if(functionSpecific.specify == 1){
					System.out.println("Enter the EPC for the desired tag: ");
					functionSpecific.targetEPC = sc.next();
					specificFunctionStr += functionSpecific.targetEPC;
				}
			}

			//If we want to write
			if(functionSpecific.function == 2){
				System.out.println("Enter the data you want to write:");
				functionSpecific.writeDataString = sc.next();
				specificFunctionStr += functionSpecific.writeDataString;
			}

			System.out.println(specificFunctionStr);
			System.out.println("Please check, (true) to continue, (false) to change: ");
			if(sc.nextBoolean()==true){
				haveRun = true; //Contain the output help info

				if(functionSpecific.function == 3)
					startInventory(functionSpecific.area, functionSpecific.startAddress, functionSpecific.wordLen, functionSpecific.maxNum);
				else if(functionSpecific.function == 1 && functionSpecific.memBank == 1)
					epcReadSync(functionSpecific.startAddress,functionSpecific.wordLen);
				else if(functionSpecific.function == 1 && functionSpecific.memBank == 2)
					userReadSync(functionSpecific.startAddress,functionSpecific.wordLen);
				else if(functionSpecific.function == 1 && functionSpecific.memBank == 3)
					tidReadSync(functionSpecific.startAddress,functionSpecific.wordLen);
				else if(functionSpecific.function == 2 && functionSpecific.memBank == 1 && functionSpecific.specify!=1)
					epcWriteSync(functionSpecific.startAddress,functionSpecific.wordLen, functionSpecific.writeDataString);
				else if(functionSpecific.function == 2 && functionSpecific.memBank == 2 && functionSpecific.specify!=1)
					userWriteSync(functionSpecific.startAddress,functionSpecific.wordLen,functionSpecific.writeDataString);
				else if(functionSpecific.function == 2 && functionSpecific.memBank == 1 && functionSpecific.specify==1)
					epcWriteSyncSpecific(functionSpecific.startAddress,functionSpecific.wordLen,functionSpecific.writeDataString,functionSpecific.targetEPC);
				else {
					System.out.println("Input WRONG! Check your input and input again!!!");
					haveRun = false;
				}
			} else{
				System.out.println("false");
				haveRun = false;
			}


			//Whether to start a new iteration or terminate
			if(haveRun){
				System.out.println("Enter (true) to start or (false) to terminate: ");
				startFlag = sc.nextBoolean();
			}
		}
		Linkage.getInstance().deinit();
	}

	// epc区的同步读取
	public static void epcReadSync(int startAddr, int wordLen) {
		byte[] password = StringUtils.stringToByte("00000000");
		RwData rwData = new RwData();
		int status = -1;

		// 如果读不到那就一直读直到读到
		while(status!=0){
			status = Linkage.getInstance().readTagSync(password ,1 , startAddr , wordLen , 3000 , rwData);//调用linkage中的epc读取函数 注意参数 password ,1 , 2 , 6 , 3000 , rwData
		}

		// Invoking the epc reading function in linkage and note the arguments
		//添加循环验证，避免读取失败 Add loop validation to avoid read failure

		if (status == 0) {
			if (rwData.status == 0) {
				String result = "";
				String epc = "";
				if (rwData.rwDataLen > 0) {
					result = StringUtils.byteToHexString(rwData.rwData,
							rwData.rwDataLen);
				}
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				System.out.println("result====" + result);// 3200
				System.out.println("epc====" + epc);// 320030007F263000DDD90140
				System.out.println("read success");
				return;
			}
		}
		System.out.println("read failed");
	}

	public static void epcWriteSync(int startAddr, int wordLen, String writeDataString) {
		byte[] password = StringUtils.stringToByte("00000000");
		byte[] writeData = StringUtils.stringToByte(writeDataString);
		RwData rwData = new RwData();
		int status = -1;

		while(status!=0){ //循环写一直读到成功
			status = Linkage.getInstance().writeTagSync(password, 1, startAddr, wordLen, writeData, 500, rwData);//调用linkage中的epc写入函数 注意参数 password, 1, 2, 1, writeData, 500, rwData
		}

		// Invoking the epc writing function in linkage and note the arguments
		//添加循环验证，避免读取失败 Add loop validation to avoid write failure
		if (status == 0) {
			if (rwData.status == 0) {
				String epc = "";
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				System.out.println("epc====" + epc);
				System.out.println("epc write success");
				return;
			}
		}
		System.out.println("epc write failed");
	}

	public static void epcWriteSyncSpecific(int startAddr, int wordLen, String writeDataString, String targetEpc) {
		byte[] password = StringUtils.stringToByte("00000000");
		byte[] writeData = StringUtils.stringToByte(writeDataString);
//		String targetEpc="11118888630D02141670673F";
		RwData rwData = new RwData();
		int status = 1;

		while(status==1){ //循环写一直读到成功
			status = Linkage.getInstance().readTagSync(password ,1 , 2 , 6 , 3000 , rwData);
			String tmpEpc =StringUtils.byteToHexString(rwData.epc, rwData.epcLen); //rwData.epcLen
			if(tmpEpc.equals(targetEpc))
				status = Linkage.getInstance().writeTagSync(password, 1, startAddr, wordLen, writeData, 500, rwData);//调用linkage中的epc写入函数 注意参数 password, 1, 2, 1, writeData, 500, rwData
			else{
				status=-2; //Not the desired tag.
				System.out.println("Not the desired tag");
			}
		}

		// Invoking the epc writing function in linkage and note the arguments
		//添加循环验证，避免读取失败 Add loop validation to avoid write failure
		if (status == 0) {
			if (rwData.status == 0) {
				String epc = "";
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				System.out.println("epc====" + epc);
				System.out.println("epc write success");
				return;
			}
		}
		System.out.println("epc write failed");
	}

	public static void userReadSync(int startAddr, int wordLen) {
		RwData rwData = new RwData();
		byte[] password = StringUtils.stringToByte("00000000");
		int status = -1;
		while(status!=0){ //循环直到读取成功
			status = Linkage.getInstance().readTagSync(password,11,startAddr,wordLen,3000,rwData);//调用linkage中的user读取函数 注意参数  Invoking the user reading function in linkage and note the arguments password,11,0,1,3000,rwData
		}
		//添加循环验证，避免读取失败 Add loop validation to avoid read failure
		if (status == 0) {
			String result = "";
			String epc = "";
			if (rwData.status == 0) {
				if (rwData.rwDataLen > 0) {
					result = StringUtils.byteToHexString(rwData.rwData,
							rwData.rwDataLen);
				}
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				System.out.println("userData====" + result);
				System.out.println("epc====" + epc);
				System.out.println("user read success");
				return;
			}
		}
		System.out.println("user read failed");
	}

	public static void tidReadSync(int startAddr, int wordLen) {
		RwData rwData = new RwData();
		byte[] password = StringUtils.stringToByte("00000000");
		int status = -1;

		while(status!=0) { //循环直到读取成功
			status = Linkage.getInstance().readTagSync(password,10,startAddr,wordLen,3000,rwData);//调用linkage中的tid读取函数 注意参数  Invoking the tid reading function in linkage and note the arguments password,10,0,2,3000,rwData
		}

		//添加循环验证，避免读取失败 Add loop validation to avoid read failure
		if (status == 0) {
			String result = "";
			String epc = "";
			if (rwData.status == 0) {
				if (rwData.rwDataLen > 0) {
					result = StringUtils.byteToHexString(rwData.rwData,
							rwData.rwDataLen);
				}
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				System.out.println("tidData====" + result);
				System.out.println("epc====" + epc);
				System.out.println("tid read success");
				return;
			}
		}
		System.out.println("tid read failed");
	}

	public static void userWriteSync(int startAddr, int wordLen, String writeDataString) {
		byte[] password = StringUtils.stringToByte("00000000");
		byte[] writeData = StringUtils.stringToByte(writeDataString);
		RwData rwData = new RwData();

		int status = -1;
		while(status!=0) { //循环直到读取成功
			status =  Linkage.getInstance().writeTagSync(password, 11, startAddr, wordLen, writeData, 500, rwData);//调用linkage中的user写入函数 注意参数  Invoking the user writing function in linkage and note the arguments
		}
		//添加循环验证，避免读取失败 Add loop validation to avoid write failure
			if (status == 0) {
				if (rwData.status == 0) {
					String epc = "";
					if (rwData.epcLen > 0) {
						epc = StringUtils
								.byteToHexString(rwData.epc, rwData.epcLen);
					}
					System.out.println("epc" + epc);
					System.out.println("user write success");
					return;
				}
			}
		System.out.println("user write failed");
	}

	public static void startInventory(int area, int startAddr, int wordLen, int maxNum) {// 开始盘点 startInventory
		InventoryArea inventory = new InventoryArea();
		inventory.setValue(area, startAddr, wordLen); //2, 0, 6
		Linkage.getInstance().setInventoryArea(inventory);
		InventoryDetailWith.tagCount = 0;// 获取个数  Get the number
		Linkage.getInstance().startInventory(2, 0);
		InventoryDetailWith.startTime = System.currentTimeMillis();// 盘点的开始时间 Start time of Inventory

		while (InventoryDetailWith.totalCount < maxNum) {

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		stopInventory();// 进行停止盘点 stopInventory

		for (Map<String, Object> _map : InventoryDetailWith.list) {
			System.out.println(_map);
			System.out.println("天线号(antennaPort)：" + _map.get("antennaPort"));
			System.out.println("epc码：" + _map.get("epc"));
			System.out.println("TID/USER码：" + _map.get("externalData"));
			System.out.println("次数(count)：" + _map.get("count"));
			System.out.println("Rssi：" + _map.get("rssi"));
		}

		long m_lEndTime = System.currentTimeMillis();// 当前时间 The current time
		double Rate = Math.ceil((InventoryDetailWith.tagCount * 1.0) * 1000
				/ (m_lEndTime - InventoryDetailWith.startTime));

		long total_time = m_lEndTime - InventoryDetailWith.startTime;
		String dateStr = StringUtils.getTimeFromMillisecond(total_time);
		int tag = InventoryDetailWith.list.size();
		System.out.println("盘点速率(Inventory rate)：" + Rate);

		if (tag != 0) {
			System.out.println("盘点时间(Inventory time)：" + dateStr);
		} else {
			System.out.println("盘点时间(Inventory time)：" + "0时0分0秒0毫秒");
		}
		System.out.println("标签个数(The number of tag)：" + tag);

	}

	public static void stopInventory() {// 停止盘点 stopInventory
			Linkage.getInstance().stopInventory();
			System.out.println("Inventory has been stopped");
	}

	// 盘点区域获取 getInventoryArea
	public static void getInventoryArea() {
		InventoryArea inventoryArea = new InventoryArea();
		int status = Linkage.getInstance().getInventoryArea(inventoryArea);
		if (status == 0) {
			System.out.println("area:" + inventoryArea.area);
			System.out.println("startAddr:" + inventoryArea.startAddr);
			System.out.println("wordLen:" + inventoryArea.wordLen);
			System.out.println("getInventoryArea success");
			return;
		}
		System.out.println("getInventoryArea failed");
	}
 
	// 盘点区域设置 setInventoryArea
	public static void setInventoryArea() {
		InventoryArea inventoryArea = new InventoryArea();
		inventoryArea.setValue(2, 0, 6);// 2为epc+user
		int status = Linkage.getInstance().setInventoryArea(inventoryArea);
		if (status == 0) {
			System.out.println("setInventoryArea success");
			return;
		}
		System.out.println("setInventoryArea failed");
	}

}
