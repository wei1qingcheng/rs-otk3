package rs.otk3.mapper.second;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rs.otk3.bean.K3InfoVO;

import java.util.List;

@Mapper
public interface K3MainFromMapper {

    /**
     * 获取符合条件的退库单信息
     * @param no
     */

    List<K3InfoVO>  getK3infoByno(@Param("no") String no);

}
