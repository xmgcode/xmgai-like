package com.mdd.common.mapper.hot;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.hot.DataContents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 数据目录Mapper
 * @author xmg
 */
@Mapper
public interface DataContentsMapper extends IBaseMapper<DataContents> {

//    @Select("select * from la_data_contents")
    List<DataContents> selectAll();
}
