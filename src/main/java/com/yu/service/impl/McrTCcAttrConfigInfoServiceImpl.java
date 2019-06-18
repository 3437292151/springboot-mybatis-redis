package com.yu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yu.domain.McrTCcAttrConfigInfo;
import com.yu.domain.McrTCcAttrConfigMx;
import com.yu.repository.McrTCcAttrConfigInfoDao;
import com.yu.repository.McrTCcAttrConfigMxDao;
import com.yu.service.McrTCcAttrConfigInfoService;
import com.yu.service.mapper.McrTCcAttrConfigInfoToMapMapper;
import com.yu.util.PageableHumpToLineUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: yuchanglong
 * @Date: 2019-6-14
 * @Description:
 */
@Service
@Slf4j
public class McrTCcAttrConfigInfoServiceImpl implements McrTCcAttrConfigInfoService {

    @Autowired
    private McrTCcAttrConfigInfoDao mcrTCcAttrConfigInfoDao;

    @Autowired
    private McrTCcAttrConfigMxDao mcrTCcAttrConfigMxDao;

    @Autowired
    private McrTCcAttrConfigInfoToMapMapper mcrTCcAttrConfigInfoToMapMapper;

    @Override
    @Transactional
    public Integer insert(McrTCcAttrConfigInfo mcrTCcAttrConfigInfo) {
        log.info("mcrTCcAttrConfigInfo: {}", mcrTCcAttrConfigInfo);
        mcrTCcAttrConfigInfo.setUUID();

        int result = mcrTCcAttrConfigInfoDao.insertSelective(mcrTCcAttrConfigInfo);
        return result;
    }

    @Override
    public PageInfo<McrTCcAttrConfigInfo> selectByCriteriaLike(McrTCcAttrConfigInfo criteria, Pageable pageable) {
        BeanMap beanMap = BeanMap.create(criteria);
        Map<String, Object> criteriaMap = new HashMap<>();
        for (Object bean : beanMap.keySet()){
            criteriaMap.put(bean.toString(), beanMap.get(bean.toString()));
        }

        String orderBy = PageableHumpToLineUtil.getHumpToLineSort(pageable);
        Page page = PageHelper.startPage(pageable.getPageNumber()+1,pageable.getPageSize(),orderBy);
        List<McrTCcAttrConfigInfo> result = mcrTCcAttrConfigInfoDao.selectByCriteriaAndKeyWords(criteriaMap);
        PageInfo<McrTCcAttrConfigInfo> pageInfo = new PageInfo<>(result);
        pageInfo.setTotal(page.getTotal());//设置总条数
        pageInfo.setPages(page.getPages());//设置总页数
        pageInfo.setPageNum(pageable.getPageNumber());//当前页页码
        pageInfo.setPageSize(pageable.getPageSize());//设置页面条数
        return pageInfo;
    }

    @Override
    public Integer delete(String id) {
        int result = mcrTCcAttrConfigInfoDao.deleteByPrimaryKey(id);
        return result;
    }

    @Override
    public Integer update(McrTCcAttrConfigInfo mcrTCcAttrConfigInfo) {
        int result = mcrTCcAttrConfigInfoDao.updateByPrimaryKeySelective(mcrTCcAttrConfigInfo);
        return result;
    }

    /**
     * @Author yuchanglong
     * @Date 2018-12-5
     * @Description 条件查询（包括关键字查询）
     * @Param map（包括keyWords，属性id：attributeId）
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> selectByCriteria(Map<String, Object> maps) {
        log.info(" param: {};", maps);

        List<McrTCcAttrConfigInfo> mcrTCcAttrConfigInfos = mcrTCcAttrConfigInfoDao.selectByCriteriaAndKeyWords(maps);
        Object attributeId = maps.get("attributeId");
        maps = new HashMap<>();
        maps.put("attributeId", attributeId);
        List<McrTCcAttrConfigMx> mcrTCcAttrConfigMxList = mcrTCcAttrConfigMxDao.selectByCriteriaAndKeyWords(maps);
        Map<String, List<McrTCcAttrConfigMx>> mcrTCcAttrConfigMxMap = mcrTCcAttrConfigMxList.stream().collect(Collectors.groupingBy(McrTCcAttrConfigMx::getInfoId));
        mcrTCcAttrConfigInfos.forEach(e -> {
            e.setMcrTCcAttrConfigMxList(mcrTCcAttrConfigMxMap.get(e.getId()));
        });


        List<Map<String,Object>> result = mcrTCcAttrConfigInfoToMapMapper.toDto(mcrTCcAttrConfigInfos);
        return result;
    }
}
