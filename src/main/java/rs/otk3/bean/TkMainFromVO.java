package rs.otk3.bean;


import java.io.Serializable;


/**
 * 退库申请单信息查询
 * Created by pzhh on 2021/11/25.
 */
public class TkMainFromVO implements Serializable {

    private static final long serialVersionUID = -1L;

    private String orderno;

    private String bispr;

    private String bisfinal;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getBispr() {
        return bispr;
    }

    public void setBispr(String bispr) {
        this.bispr = bispr;
    }

    public String getBisfinal() {
        return bisfinal;
    }

    public void setBisfinal(String bisfinal) {
        this.bisfinal = bisfinal;
    }
}
