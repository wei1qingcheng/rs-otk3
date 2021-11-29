package rs.otk3.server.impl;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.otk3.bean.K3InfoVO;
import rs.otk3.bean.TkMainFromVO;
import rs.otk3.bean.TkSonFromVO;
import rs.otk3.controller.A8tkTestController;
import rs.otk3.mapper.master.TkMainFromMapper;
import rs.otk3.mapper.master.TkSonFromMapper;
import rs.otk3.mapper.second.K3MainFromMapper;
import rs.otk3.server.A8tkServer;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class A8tkServerImpl implements A8tkServer {

    private static final Logger logger = LoggerFactory.getLogger(A8tkServerImpl.class);


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

        logger.info("执行手动指定更新开始===>同步中间表k3数据到OA退库表中");
        //查询所有符合条件的数据
        List<TkMainFromVO> lis = null;
        try {
            lis = tkmainFromDAO.getTkinfoByno(no);
            logger.info("查询需要处理的数据长度===>" + lis.size());
        } catch (Exception e) {
            logger.error("查询A8需要处理的数据出现错误===>" + e);
            throw e;
        }
        Vector<TkSonFromVO> sonVec = new Vector<TkSonFromVO>();
        Vector<String> fidVec = new Vector<String>();
        //符合条件的数据去查询子表
        for (TkMainFromVO fvo : lis) {
            logger.info("需要处理的数据===>" + fvo.getOrderno());
            List<K3InfoVO> k3list = null;
            try {
                k3list = k3FromDAO.getK3infoByno(fvo.getOrderno());
                logger.info("对应处理的数据长度===>" + k3list.size());
            } catch (Exception e) {
                logger.error("查询K3需要处理的数据出现错误===>" + e);
                throw e;
            }
            int sort = 1;
            for (K3InfoVO k3vo : k3list) {
                logger.info("需要交换a8的数据===>" + k3vo.getK3orderno());
                TkSonFromVO sonvo = null;
                try {
                    sonvo = exChange(k3vo, sort, fvo.getId());
                    logger.info("需要交换k3的数据===>" + sonvo.getK3orderno());
                } catch (Exception e) {
                    logger.error("交换处理的数据出现错误===>" + e);
                    throw e;
                }
                sonVec.add(sonvo);
                if (!fidVec.contains(fvo.getId())) {
                    fidVec.add(fvo.getId());
                }
                sort++;
            }

        }
        if (!fidVec.isEmpty()) {
            List<String> fidlist = Collections.list(fidVec.elements());
            try {
                tksonFromDAO.deleteTksoninfobyfid(fidlist);
                logger.info("删除a8子表数据成功");
            } catch (Exception e) {
                logger.error("删除a8子表数据失败" + e);
                throw e;
            }
            Integer num = 100;
            Integer size = sonVec.size();
            Integer length = size / num + 1;

            for (int xx = 0; xx < length; xx++) {
                Vector vec = new Vector();
                for (int yy = 0; yy < num && (yy + xx * num) < size - 1; yy++) {
                    vec.add(sonVec.get(yy + xx * num));
                }
                List<TkSonFromVO> list = Collections.list(vec.elements());
                try {
                    tksonFromDAO.insertTksoninfolist(list);
                    logger.info("插入a8子表数据成功");
                } catch (Exception e) {
                    logger.error("插入a8子表数据失败" + e);
                    throw e;
                }


            }
            try {
                tkmainFromDAO.upTkmaininfolist(fidlist);
                logger.info("更新a8主表回写数据成功");
            } catch (Exception e) {
                logger.error("更新a8主表回写数据失败" + e);
                throw e;
            }

        }
        logger.info("执行手动指定更新结束===>同步中间表k3数据到OA退库表中");
        return "成功";
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

        logger.info("执行手动全部更新开始===>同步中间表k3数据到OA退库表中");
        //查询所有符合条件的数据
        List<TkMainFromVO> lis = null;
        try {
            lis = tkmainFromDAO.getTkinfolist();
            logger.info("查询需要处理的数据长度===>" + lis.size());
        } catch (Exception e) {
            logger.error("查询A8需要处理的数据出现错误===>" + e);
            throw e;
        }
        Vector<TkSonFromVO> sonVec = new Vector<TkSonFromVO>();
        Vector<String> fidVec = new Vector<String>();
        //符合条件的数据去查询子表
        for (TkMainFromVO fvo : lis) {
            logger.info("需要处理的数据===>" + fvo.getOrderno());
            List<K3InfoVO> k3list = null;
            try {
                k3list = k3FromDAO.getK3infoByno(fvo.getOrderno());
                logger.info("对应处理的数据长度===>" + k3list.size());
            } catch (Exception e) {
                logger.error("查询K3需要处理的数据出现错误===>" + e);
                throw e;
            }
            int sort = 1;
            for (K3InfoVO k3vo : k3list) {
                logger.info("需要交换a8的数据===>" + k3vo.getK3orderno());

                TkSonFromVO sonvo = null;
                try {
                    sonvo = exChange(k3vo, sort, fvo.getId());
                    logger.info("需要交换k3的数据===>" + sonvo.getK3orderno());
                } catch (Exception e) {
                    logger.error("交换处理的数据出现错误===>" + e);
                    throw e;
                }
                sonVec.add(sonvo);
                if (!fidVec.contains(fvo.getId())) {
                    fidVec.add(fvo.getId());
                }
                sort++;
            }

        }
        if (!fidVec.isEmpty()) {
            List<String> fidlist = Collections.list(fidVec.elements());
            try {
                tksonFromDAO.deleteTksoninfobyfid(fidlist);
                logger.info("删除a8子表数据成功");
            } catch (Exception e) {
                logger.error("删除a8子表数据失败" + e);
                throw e;
            }
            Integer num = 100;
            Integer size = sonVec.size();
            Integer length = size / num + 1;

            for (int xx = 0; xx < length; xx++) {
                Vector vec = new Vector();
                for (int yy = 0; yy < num && (yy + xx * num) < size - 1; yy++) {
                    vec.add(sonVec.get(yy + xx * num));
                }
                List<TkSonFromVO> list = Collections.list(vec.elements());
                try {
                    tksonFromDAO.insertTksoninfolist(list);
                    logger.info("插入a8子表数据成功");
                } catch (Exception e) {
                    logger.error("插入a8子表数据失败" + e);
                    throw e;
                }


            }
            try {
                tkmainFromDAO.upTkmaininfolist(fidlist);
                logger.info("更新a8主表回写数据成功");
            } catch (Exception e) {
                logger.error("更新a8主表回写数据失败" + e);
                throw e;
            }

        }
        logger.info("执行手动全部更新结束===>同步中间表k3数据到OA退库表中");
        return "成功";
    }


}
