package com.yu.service;

import com.github.pagehelper.PageInfo;
import com.yu.domain.McrTCcAttrConfigInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


/**
 * @Auther: yuchanglong
 * @Date: 2018-12-3
 * @Description: CC属性配置信息 服务层
 */
public interface McrTCcAttrConfigInfoService {

    Integer insert(McrTCcAttrConfigInfo mcrTCcAttrConfigInfo);

    /**
     * @Author yuchanglong
     * @Date 2018-12-18
     * @Description 条件模糊查询
     * @Param criteria
     * @Param pageable
     * @return com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    PageInfo<McrTCcAttrConfigInfo> selectByCriteriaLike(McrTCcAttrConfigInfo criteria, Pageable pageable);

    Integer delete(String id);

    Integer update(McrTCcAttrConfigInfo mcrTCcAttrConfigInfo);

    List<Map<String, Object>> selectByCriteria(Map<String, Object> maps);
}
