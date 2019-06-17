package com.yu.domain;

import lombok.Data;

import java.util.UUID;

/**
 * @Author yuchanglong
 * @Date 2018-12-4
 * @Description CC属性配置信息明细 domain
 **/
@Data
public class McrTCcAttrConfigMx {
    private String id;

    private String configCategory;

    private String configValue;

    private String infoId;

    private String ccConfigId;

    public McrTCcAttrConfigMx setUUID(){
        this.setId(UUID.randomUUID().toString());
        return this;
    }

    public McrTCcAttrConfigMx(String id, String configCategory, String configValue, String infoId, String ccConfigId) {
        this.id = id;
        this.configCategory = configCategory;
        this.configValue = configValue;
        this.infoId = infoId;
        this.ccConfigId = ccConfigId;
    }

    public McrTCcAttrConfigMx() {
        super();
    }

}
