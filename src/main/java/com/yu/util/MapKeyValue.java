package com.yu.util;

import lombok.Data;

/**
 * @Auther: yuchanglong
 * @Date: 2018-12-4
 * @Description: Map 对应的key字段，value字段
 */
@Data
public class MapKeyValue {

    private String keyField;

    private String valueField;

    public MapKeyValue(String keyField, String valueField) {
        this.keyField = keyField;
        this.valueField = valueField;
    }
    public MapKeyValue(){}
}
