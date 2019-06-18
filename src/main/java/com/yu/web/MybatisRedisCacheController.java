package com.yu.web;

import com.github.pagehelper.PageInfo;
import com.yu.config.PayConfig;
import com.yu.domain.McrTCcAttrConfigInfo;
import com.yu.service.McrTCcAttrConfigInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: yuchanglong
 * @Date: 2019-6-14
 * @Description:
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class MybatisRedisCacheController {

    @Autowired
    private McrTCcAttrConfigInfoService mcrTCcAttrConfigInfoService;

    @Autowired
    private PayConfig payConfig;

    @PostMapping("/mybatis/redis/add")
    public String insert(@RequestBody McrTCcAttrConfigInfo mcrTCcAttrConfigInfo){
        Integer result = mcrTCcAttrConfigInfoService.insert(mcrTCcAttrConfigInfo);
        return String.valueOf(result);
    }

    @GetMapping("/mybatis/redis/cache")
    public PageInfo<McrTCcAttrConfigInfo> selectByCriteriaLike(McrTCcAttrConfigInfo criteria, Pageable pageable) {
        log.info("criteria :{} ; pageable:{}", criteria, pageable);
        return mcrTCcAttrConfigInfoService.selectByCriteriaLike(criteria, pageable);
    }

    @GetMapping("/mybatis/redis/cache/criteria")
    public List<Map<String, Object>> selectByCriteria(Map<String, Object> criteria) {
        log.info("criteria :{} ", criteria);
        return mcrTCcAttrConfigInfoService.selectByCriteria(criteria);
    }

    @PutMapping("/mybatis/redis/update")
    public String update(@RequestBody McrTCcAttrConfigInfo mcrTCcAttrConfigInfo){
        Integer result = mcrTCcAttrConfigInfoService.update(mcrTCcAttrConfigInfo);
        return String.valueOf(result);
    }

    @DeleteMapping("/mybatis/redis/delete")
    public String delete(String id){
        Integer result = mcrTCcAttrConfigInfoService.delete(id);
        return String.valueOf(result);
    }
}
