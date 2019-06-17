package com.yu.service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: yuchanglong
 * @Date: 2018-12-4
 * @Description: CC属性配置明细 服务层
 */
public interface McrTCcAttrConfigMxService  {


    List<Map<String, Object>> selectFullByCriteria(Map<String, Object> map);


}
