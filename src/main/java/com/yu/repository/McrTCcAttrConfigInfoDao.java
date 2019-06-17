package com.yu.repository;

import com.yu.domain.McrTCcAttrConfigInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface McrTCcAttrConfigInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(McrTCcAttrConfigInfo record);

    int insertSelective(McrTCcAttrConfigInfo record);

    McrTCcAttrConfigInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(McrTCcAttrConfigInfo record);

    int updateByPrimaryKey(McrTCcAttrConfigInfo record);

    //根据主键获取完整信息
    McrTCcAttrConfigInfo selectFullByPrimaryKey(String id);

    //根据查询条件获取完整信息
    List<McrTCcAttrConfigInfo> selectFullByCriteriaAndKeyWords(Map<String, Object> criteria);

    List<McrTCcAttrConfigInfo> selectByCriteriaAndKeyWords(Map<String, Object> criteria);

    //根据查询条件获取完整信息
    //List<McrTCcAttrConfigInfo> selectFullByCriteriaKeyWords(Map<String, Object> criteria);

    //批量删除
    int batchDelete(List<String> idArray);

    //条件模糊查询
    List<Map<String, Object>> selectFullByCriteriaLike(Map<String, Object> criteria);

    //通过id数组查询数据
    List<McrTCcAttrConfigInfo> selectByIdArray(List<String> idArray);

    //综合条件查询
    List<McrTCcAttrConfigInfo> selectByCriteria(Map<String, Object> criteria);
}
