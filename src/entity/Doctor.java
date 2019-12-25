package entity;

public class Doctor {
    private int id;
    private String name;
    private String epc;

    public Doctor(String name, String epc) {
        this.id = -1; //-1:还没有id，这是非法id标识，只有当写入数据库的时候才会被创建
        this.name = name;
        this.epc = epc;
    }

    public Doctor(int id, String name, String epc) {
        this.id = id;
        this.name = name;
        this.epc = epc;
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
}
