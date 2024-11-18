package com.mdd.common.mapper.apif;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.apif.DataApif;
import com.mdd.common.vo.apif.DataApifVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 接口管理Mapper
 * @author xmg
 */
@Mapper
public interface DataApifMapper extends IBaseMapper<DataApif> {
    @Select("SELECT  a.id AS id, a.user_id AS userId, a.apif_id AS apifId, a.amount, a.pay_status AS payStatus, a.price, a.total AS totalPrice, b.NAME, b.is_free AS isFree, b.api_document AS apiDocument, b.description AS description FROM la_data_apif_order a LEFT JOIN la_data_apif b ON a.apif_id = b.id where a.user_id = #{userId} and  a.pay_status = #{payStatus}")
    List<DataApifVo> listUserNoPayApif(@Param("userId") String userId, @Param("payStatus") String payStatus);



    @Select("select a.id,a.apif_id as apifId,a.user_id as userId,a.api_key as apiKey,a.api_secret as apiSecret,a.total,a.remain,b.name,b.description,b.api_document as apiDocument from la_data_apif_user a LEFT JOIN la_data_apif b on a.apif_id = b.id where a.user_id =#{userId} and a.deleted = 0")
    List<DataApifVo> listUserPayApif(String userId);


    List<DataApifVo> listUserApif();
}
