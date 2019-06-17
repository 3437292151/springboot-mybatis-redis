package com.yu.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @Author yuchanglong
 * @Date 2018-12-4
 * @Description CC属性配置信息 domain
 **/
@Data
public class McrTCcAttrConfigInfo implements Serializable{

    private String libId;

    private String disciplineId;

    private String attributeId;

    private Integer infoStatus;

    private List<McrTCcAttrConfigMx> mcrTCcAttrConfigMxList;

    private String id;

    private Integer sortNumber;

    private String defUser;

    private String defUserName;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime defDt;

    private String updUser;

    private String updUserName;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updDt;

    private String sdept;

    private String scmpy;

    public McrTCcAttrConfigInfo setUUID(){
        this.setId(UUID.randomUUID().toString());
        return this;
    }

}
