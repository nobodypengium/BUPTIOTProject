package entity;

public class Patient {
    private int id;
    private String name;
    private String epc;
    private String record="";

    public Patient(String name, String epc) {
        this.id = -1; //-1:还没有id，这是非法id标识，只有当写入数据库的时候才会被创建
        this.name = name;
        this.epc = epc;
        this.record="";
    }

    public Patient(int id, String name, String epc, String record) {
        this.id = id;
        this.name = name;
        this.epc = epc;
        this.record=record;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEpc(){
        return this.epc;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
