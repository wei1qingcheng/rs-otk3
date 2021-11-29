package rs.otk3.bean;


import java.io.Serializable;


/**
 * 退库申请单信息查询
 * Created by pzhh on 2021/11/25.
 */
public class K3InfoVO implements Serializable {

    private static final long serialVersionUID = -1L;

    //单据日期  k3红字出库单单据日期
    private String fdate;

    //单据编号  k3红字出库单据编号
    private String k3orderno;

    //虚仓单号 a8退库申请单单据编号
    private String a8orderno;

    //产品长代码  k3物料代码
    private String k3mano;

    //产品名称  k3物料名称
    private String k3maname;

    //规格型号  规格型号
    private String k3ggxh;

    //单位  单位
    private String k3dw;

    //批号  批号
    private String k3ph;
    //实发数量  实发数量
    private String k3manum;
    //备注
    private String memo;
    //商务核查：
    private String k3reason;
    //退货类别:
    private String k3outtype;

    public String getFdate() {
        return fdate;
    }

    public void setFdate(String fdate) {
        this.fdate = fdate;
    }

    public String getK3orderno() {
        return k3orderno;
    }

    public void setK3orderno(String k3orderno) {
        this.k3orderno = k3orderno;
    }

    public String getA8orderno() {
        return a8orderno;
    }

    public void setA8orderno(String a8orderno) {
        this.a8orderno = a8orderno;
    }

    public String getK3mano() {
        return k3mano;
    }

    public void setK3mano(String k3mano) {
        this.k3mano = k3mano;
    }

    public String getK3maname() {
        return k3maname;
    }

    public void setK3maname(String k3maname) {
        this.k3maname = k3maname;
    }

    public String getK3ggxh() {
        return k3ggxh;
    }

    public void setK3ggxh(String k3ggxh) {
        this.k3ggxh = k3ggxh;
    }

    public String getK3dw() {
        return k3dw;
    }

    public void setK3dw(String k3dw) {
        this.k3dw = k3dw;
    }

    public String getK3ph() {
        return k3ph;
    }

    public void setK3ph(String k3ph) {
        this.k3ph = k3ph;
    }

    public String getK3manum() {
        return k3manum;
    }

    public void setK3manum(String k3manum) {
        this.k3manum = k3manum;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getK3reason() {
        return k3reason;
    }

    public void setK3reason(String k3reason) {
        this.k3reason = k3reason;
    }

    public String getK3outtype() {
        return k3outtype;
    }

    public void setK3outtype(String k3outtype) {
        this.k3outtype = k3outtype;
    }
}
