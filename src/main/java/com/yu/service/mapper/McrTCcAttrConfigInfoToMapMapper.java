package com.yu.service.mapper;


import com.yu.domain.McrTCcAttrConfigInfo;
import com.yu.util.MapKeyValue;
import com.yu.util.MapObjUtil;
import org.mapstruct.Mapper;

import java.util.Map;

/**
 * @Auther: yuchanglong
 * @Date: 2018-11-27
 * @Description: McrTCcAttrConfigInfo Map mapping domain
 */
@Mapper(componentModel = "spring", uses = {})
public interface McrTCcAttrConfigInfoToMapMapper extends EntityMapper<Map<String, Object>, McrTCcAttrConfigInfo>{

    @Override
    default McrTCcAttrConfigInfo toEntity(Map<String, Object> dto) {
        if(dto == null) {
            return null;
        } else {
            McrTCcAttrConfigInfo mcrTCcAttrConfigInfo = MapObjUtil.map2Object(dto,  McrTCcAttrConfigInfo.class);
            return mcrTCcAttrConfigInfo;
        }
    }

    @Override
    default Map<String, Object> toDto(McrTCcAttrConfigInfo entity) {
        if(entity == null) {
            return null;
        } else {
            MapKeyValue mapKeyValue = new MapKeyValue();
            mapKeyValue.setKeyField("ccConfigId");
            mapKeyValue.setValueField("configValue");
            Map<String, Object> dto = MapObjUtil.object2Map(entity, mapKeyValue);
            return dto;
        }
    }


}
