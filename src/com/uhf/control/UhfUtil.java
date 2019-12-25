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
        //����ֵ��<Key(user����epc...),Value(��Ӧ��ǩ��ֵ)>,
        Map<String,String> map = new HashMap<String, String>();

        byte[] password = StringUtils.stringToByte("00000000");
        RwData rwData = new RwData();
        int status = -1;

        // ����������Ǿ�һֱ��ֱ����������ȡEPC
        while(status!=0){
            status = Linkage.getInstance().readTagSync(password ,1 , 2 , 6 , 3000 , rwData);//����linkage�е�epc��ȡ���� ע����� password ,1 , 2(start addr) , 6(wordLen) , 3000 , rwData
        }

        // Invoking the epc reading function in linkage and note the arguments
        //���ѭ����֤�������ȡʧ�� Add loop validation to avoid read failure

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

        status=-1;//��ʼ��status�Ա�������һ�ζ�ȡ����һ�ζ�ȡUser��
        while(status!=0){
            status = Linkage.getInstance().readTagSync(password ,11 , 0 , 20 , 3000 , rwData);//����linkage�е�user��ȡ���� ע�����  Invoking the user reading function in linkage and note the arguments password,11,0,1,3000,rwData
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
