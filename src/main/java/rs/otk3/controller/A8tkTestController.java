package rs.otk3.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.otk3.server.A8tkServer;

@Controller
public class A8tkTestController {


    @Autowired
    private A8tkServer tkServer;


    /**
     * 根据指定no查询数据
     * Created by pzhh on 2021/11/28.
     */
    @RequestMapping(value = "/tkinfobyno/{no}", method = RequestMethod.GET)
    @ResponseBody
    public String getTkinfoByno(@PathVariable String no) {
       String re =  tkServer.getTkinfoByno(no);
       return re;
    }

    /**
     * 查询所有符合条件的数据
     * Created by pzhh on 2021/11/28.
     */
    @RequestMapping(value = "/tkallinfo")
    @ResponseBody
    public String getTkinfolist() throws JSONException {
        String re = tkServer.getTkinfolist();
        return re;
    }


    /**
     * 更新符合条件的数据
     * Created by pzhh on 2021/11/28.
     */
    @RequestMapping(value = "/uptkinfobyno/{no}", method = RequestMethod.GET)
    @ResponseBody
    public String upTkinfobyno(@PathVariable String no) throws JSONException {
        String re = tkServer.upTkinfoByno(no);
        return re;
    }

    /**
     * 查询所有符合条件的数据
     * Created by pzhh on 2021/11/28.
     */
    @RequestMapping(value = "/uptkinfoall")
    @ResponseBody
    public String upTkinfoall() throws JSONException {
        String re = tkServer.upTkinfoall();
        return re;
    }


    /**
     * 根据OA单据号查询K3数据
     * Created by pzhh on 2021/11/28.
     */
    @RequestMapping(value = "/k3tkinfo/{no}")
    @ResponseBody
    public String getK3tkinfo(@PathVariable String no) throws JSONException {
        String re = tkServer.getK3tkinfoByno(no);
        return re;
    }
}
