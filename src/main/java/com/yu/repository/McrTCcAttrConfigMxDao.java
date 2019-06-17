package com.yu.repository;

import com.yu.domain.McrTCcAttrConfigMx;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface McrTCcAttrConfigMxDao {
    int deleteByPrimaryKey(String id);

    int insert(McrTCcAttrConfigMx record);

    int insertSelective(McrTCcAttrConfigMx record);

    McrTCcAttrConfigMx selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(McrTCcAttrConfigMx record);

    int updateByPrimaryKey(McrTCcAttrConfigMx record);

    //通过CC属性配置信息id查询
    List<McrTCcAttrConfigMx> selectByCcInfoId(String infoId);

    //批量插入
    Integer batchInsert(List<McrTCcAttrConfigMx> mcrTCcAttrConfigMxList);

    //根据配置类型与配置信息ID更新
    Integer updateByConfigIdAndInfoId(McrTCcAttrConfigMx mcrTCcAttrConfigMx);

    //根据CC属性配置信息 id 批量删除
    Integer batchDeleteByInfoIdArray(List<String> infoIdArray);

    //根据查询条件已经关键字查询
    List<McrTCcAttrConfigMx> selectByCriteriaAndKeyWords(Map<String, Object> criteria);

    List<Map<String, Object>> selectFullByCriteriaAndKeyWordsMap(Map<String, Object> criteria);

    //查询cc配置数据根据cc配置id数组
    List<String> selectCcConfigIdByCcConfigIdArray(List<String> ccConfigIdArray);

    //每个对象作为过滤条件，而数组为多个查询的结果集做并集
    List<McrTCcAttrConfigMx> selectByOrCriteria(List<McrTCcAttrConfigMx> mcrTCcAttrConfigMxList);

    List<McrTCcAttrConfigMx> verifyUpdate(Map<String, Object> criteria);

    //通过配置id过滤，并且配置id的值排序
    List<McrTCcAttrConfigMx> selectByConfigId(String ccConfigId);

    List<McrTCcAttrConfigMx> selectByInfoIdArray(List<String> infoIdArray);
}
