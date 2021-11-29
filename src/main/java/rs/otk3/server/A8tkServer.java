package rs.otk3.server;



import org.json.JSONException;

import java.util.Vector;


public interface A8tkServer {


    public String  getTkinfolist() throws JSONException;

    public  String getTkinfoByno(String no);

    public  String upTkinfoByno(String no);

    public  String getK3tkinfoByno(String no);

    public  String upTkinfoall();
}
