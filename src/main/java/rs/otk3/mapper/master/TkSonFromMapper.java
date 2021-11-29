package rs.otk3.mapper.master;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import rs.otk3.bean.TkMainFromVO;
import rs.otk3.bean.TkSonFromVO;

import java.util.List;

@Mapper
public interface TkSonFromMapper {

    /**
     * 根据单据号查询子表数据
     * @param no
     */
    List<TkSonFromVO>  getTksoninfoByno(@Param("no") String no);

    /**
     * 根据子表主键查询子表数据
     * @param no
     */
    List<TkMainFromVO> getTksoninfolist(@Param("no") String no);

    /**
     * 插入子表数据
     *
     */
    void  insertTksoninfolist(@Param("list")List<TkSonFromVO> ids);

    /**
     * 删除子表数据 by id
     * @param no
     */
    void deleteTksoninfobyid(@Param("no") String no);


    /**
     * 删除子表数据 by fid
     *
     */
    void deleteTksoninfobyfid(@Param("list")List<String> ids);


    /**
     * 删除子表数据 by no
     * @param no
     */
    List<TkMainFromVO> deleteTksoninfobyno(@Param("no") String no);

}
