package rs.otk3.server.impl;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.otk3.bean.K3InfoVO;
import rs.otk3.bean.TkMainFromVO;
import rs.otk3.bean.TkSonFromVO;
import rs.otk3.mapper.master.TkMainFromMapper;
import rs.otk3.mapper.master.TkSonFromMapper;
import rs.otk3.mapper.second.K3MainFromMapper;
import rs.otk3.server.A8tkServer;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class A8tkServerImpl implements A8tkServer {


    @Autowired
    private TkMainFromMapper tkmainFromDAO;

    @Autowired
    private TkSonFromMapper tksonFromDAO;


    @Autowired
    private K3MainFromMapper k3FromDAO;

    @Override
    public String getTkinfolist() throws JSONException {
        List<TkMainFromVO> lis = tkmainFromDAO.getTkinfolist();
//        JSONObject json = new JSONObject();
        JSONArray jsonarr = new JSONArray();
        for (TkMainFromVO vo : lis) {
            JSONObject json = new JSONObject();
            json.put("no", vo.getOrderno());
            json.put("id", vo.getId());
            jsonarr.put(json);
        }
        return jsonarr.toString();
    }

    @Override
    public String getK3tkinfoByno(String no) {
        List<K3InfoVO> lis = k3FromDAO.getK3infoByno(no);
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    @Override
    public String getTkinfoByno(String no) {
        List<TkMainFromVO> lis = tkmainFromDAO.getTkinfoByno(no);
        StringBuffer sb = new StringBuffer();
        if (!lis.isEmpty()) {
            sb.append(" 查询数据:").append(lis.get(0).getOrderno());
            sb.append(" 是否质检问题").append(lis.get(0).getBispr());
            sb.append(" 是否回写成功").append(lis.get(0).getBisfinal());
        } else {
            sb.append("查询数据:").append(no).append("  不存在");
        }
        return sb.toString();
    }


    @Override
    public String upTkinfoByno(String no) {

        //查询出需要处理的单据
        List<TkMainFromVO> lis = tkmainFromDAO.getTkinfoByno(no);
        Vector<TkSonFromVO> sonVec = new Vector<TkSonFromVO>();
        Vector<String> fidVec = new Vector<String>();
        //符合条件的数据去查询子表
        for (TkMainFromVO fvo : lis) {
            List<K3InfoVO> k3list = k3FromDAO.getK3infoByno(fvo.getOrderno());
            int sort = 1;
            for (K3InfoVO k3vo : k3list) {
                TkSonFromVO sonvo = exChange(k3vo, sort, fvo.getId());
                sonVec.add(sonvo);
                if (!fidVec.contains(fvo.getId())) {
                    fidVec.add(fvo.getId());
                }
                sort++;
            }
        }
        if (!sonVec.isEmpty()) {
            List<String> fidlist = Collections.list(fidVec.elements());
            tksonFromDAO.deleteTksoninfobyfid(fidlist);
            Integer num = 100;
            Integer size = sonVec.size();
            Integer length = size / num + 1;

            for (int xx = 0; xx < length; xx++) {
                Vector vec = new Vector();
                for (int yy = 0; yy <num && (yy + xx * num) < size - 1; yy++) {
                    vec.add(sonVec.get(yy + xx * num));
                }
                List<TkSonFromVO> list = Collections.list(vec.elements());
                tksonFromDAO.insertTksoninfolist(list);
            }
            tkmainFromDAO.upTkmaininfolist(fidlist);

        }

        return lis.isEmpty() + "";

    }

    public TkSonFromVO exChange(K3InfoVO k3vo, int sort, String fid) {
        TkSonFromVO sonvo = new TkSonFromVO();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSS");
        Random rand = new Random();
        String formDate = sdf.format(date);
        sonvo.setID(formDate + (rand.nextInt(100)));
        sonvo.setFormmain_id(fid);
        sonvo.setSort(sort);
        sonvo.setNo(sort);
        sonvo.setFdate(k3vo.getFdate());
        sonvo.setA8orderno(k3vo.getA8orderno());
        sonvo.setK3orderno(k3vo.getK3orderno());
        sonvo.setK3maname(k3vo.getK3maname());
        sonvo.setK3mano(k3vo.getK3mano());
        sonvo.setK3ggxh(k3vo.getK3ggxh());
        sonvo.setK3dw(k3vo.getK3dw());
        sonvo.setK3ph(k3vo.getK3ph());
        sonvo.setK3manum(k3vo.getK3manum());
        sonvo.setMemo(k3vo.getMemo());
        sonvo.setK3reason(k3vo.getK3reason());
        sonvo.setK3outtype(k3vo.getK3outtype());
        return sonvo;
    }

    @Override
    public String upTkinfoall() {

        //查询所有符合条件的数据
        List<TkMainFromVO> lis = tkmainFromDAO.getTkinfolist();

        Vector<TkSonFromVO> sonVec = new Vector<TkSonFromVO>();
        Vector<String> fidVec = new Vector<String>();
        //符合条件的数据去查询子表
        for (TkMainFromVO fvo : lis) {
            List<K3InfoVO> k3list = k3FromDAO.getK3infoByno(fvo.getOrderno());
            int sort = 1;
            for (K3InfoVO k3vo : k3list) {
                TkSonFromVO sonvo = exChange(k3vo, sort, fvo.getId());
                sonVec.add(sonvo);
                if (!fidVec.contains(fvo.getId())) {
                    fidVec.add(fvo.getId());
                }
                sort++;
            }
        }
        if (!fidVec.isEmpty()) {
            List<String> fidlist = Collections.list(fidVec.elements());
            tksonFromDAO.deleteTksoninfobyfid(fidlist);
            Integer num = 100;
            Integer size = sonVec.size();
            Integer length = size / num + 1;

            for (int xx = 0; xx < length; xx++) {
                Vector vec = new Vector();
                for (int yy = 0; yy < num && (yy + xx * num) < size - 1; yy++) {
                    vec.add(sonVec.get(yy + xx * num));
                }
                List<TkSonFromVO> list = Collections.list(vec.elements());
                tksonFromDAO.insertTksoninfolist(list);
            }
            tkmainFromDAO.upTkmaininfolist(fidlist);

        }

        return "null";
    }


}
