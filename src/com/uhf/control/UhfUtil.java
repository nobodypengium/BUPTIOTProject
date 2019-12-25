package com.uhf.control;

import com.uhf.linkage.Linkage;
import com.uhf.structures.RwData;
import com.uhf.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 2019/12/12.
 */
public class UhfUtil {
    public static Map<String, String> readAll() {
        //返回值，<Key(user还是epc...),Value(对应标签的值)>,
        Map<String,String> map = new HashMap<String, String>();

        byte[] password = StringUtils.stringToByte("00000000");
        RwData rwData = new RwData();
        int status = -1;

        // 如果读不到那就一直读直到读到，读取EPC
        while(status!=0){
            status = Linkage.getInstance().readTagSync(password ,1 , 2 , 6 , 3000 , rwData);//调用linkage中的epc读取函数 注意参数 password ,1 , 2(start addr) , 6(wordLen) , 3000 , rwData
        }

        // Invoking the epc reading function in linkage and note the arguments
        //添加循环验证，避免读取失败 Add loop validation to avoid read failure

        if (status == 0) {
            if (rwData.status == 0) {
                String epc = "";
                if (rwData.rwDataLen > 0) {
                    epc = StringUtils.byteToHexString(rwData.rwData,
                            rwData.rwDataLen);
                    map.put("EPC",epc);
                }
            }
        }

        status=-1;//初始化status以便启动下一次读取，下一次读取User区
        while(status!=0){
            status = Linkage.getInstance().readTagSync(password ,11 , 0 , 20 , 3000 , rwData);//调用linkage中的user读取函数 注意参数  Invoking the user reading function in linkage and note the arguments password,11,0,1,3000,rwData
        }
        if (status == 0) {
            if (rwData.status == 0) {
                String user = "";
                if (rwData.rwDataLen > 0) {
                    user = StringUtils.byteToHexString(rwData.rwData,
                            rwData.rwDataLen);
                    map.put("USER",user);
                }
            }
        }
        return map;
    }
}
