package rs.otk3.mapper.master;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rs.otk3.bean.TkMainFromVO;
import rs.otk3.bean.TkSonFromVO;

import java.util.List;

@Mapper
public interface TkMainFromMapper {

    /**
     * 获取符合条件的退库单信息
     * @param no
     */

    List<TkMainFromVO>  getTkinfoByno(@Param("no") String no);

//    List<TkMainFromVO>  getTkinfoBynoandnoother(@Param("no") String no);

    List<TkMainFromVO> getTkinfolist();

    void  upTkmaininfolist(@Param("list")List<String> ids);
}
