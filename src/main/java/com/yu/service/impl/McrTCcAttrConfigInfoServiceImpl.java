package com.yu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yu.domain.McrTCcAttrConfigInfo;
import com.yu.repository.McrTCcAttrConfigInfoDao;
import com.yu.service.McrTCcAttrConfigInfoService;
import com.yu.util.PageableHumpToLineUtil;
import com.yu.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private RedisTemplate<String, Object> redisTemplate ;

    @Override
    @Transactional
    public Integer insert(McrTCcAttrConfigInfo mcrTCcAttrConfigInfo) {
        log.info("mcrTCcAttrConfigInfo: {}", mcrTCcAttrConfigInfo);
        mcrTCcAttrConfigInfo.setUUID();
        if (redisTemplate == null){
            redisTemplate = (RedisTemplate<String, Object>) SpringBeanUtil.getBean("redisTemplate");
        }
        Set<String> keys = redisTemplate.keys("*:");
        log.info("keys:{}", keys);
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
}
