package com.uhf.structures;

/**
 * Created by hp on 2019/10/10.
 */
public class FunctionSpecific {
    public int memBank;
    public int function;
    public int startAddress;
    public int wordLen;
    public String targetEPC;
    public String writeDataString;
    public int area;
    public int maxNum;
    public int specify = 0;

    public String func2String() {
        String functionStr = "";
        switch (this.function) {
            case 1:
                functionStr = "Read";
                break;
            case 2:
                functionStr = "Write";
                break;
            case 3:
                functionStr = "Inventory";
                break;
            default:
                functionStr = "Not a desired function! Input again!";
                break;
        }
        return functionStr;
    }

    public String memBank2String() {
        String membStr = "";
        switch (this.function) {
            case 1:
                membStr = "EPC";
                break;
            case 2:
                membStr = "USER";
                break;
            case 3:
                membStr = "TID";
                break;
            default:
                membStr = "Not a desired memory bank! Input again!";
                break;
        }
        return membStr;
    }

    public String area2String() {
        String areaStr = "";
        switch (this.function) {
            case 1:
                areaStr = "EPC";
                break;
            case 2:
                areaStr = "EPC + TID";
                break;
            case 3:
                areaStr = "EPC + USER";
                break;
            default:
                areaStr = "Not a desired area! Input again!";
                break;
        }
        return areaStr;
    }
}
