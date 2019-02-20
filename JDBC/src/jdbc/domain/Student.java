package jdbc.domain;

/**
 * @author Maple
 * @date 2019.01.13 20:38
 * JavaBean类,用于封装数据.
 * 每一个类对应数据库中的一张表,而表中的每一个字段对应表中的每一个属性.
 */
public class Student {
    //私有字段: 对应数据库中的字段名
    private int sid;
    private String sname;
    private int gid;

    //无参构造
    public Student() {
    }

    //getter和setter方法
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
