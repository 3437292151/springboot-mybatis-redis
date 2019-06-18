package com.yu.util;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author yuchanglong
 * @Date 2018-12-6
 * @Description 实体与Map装换工具
 **/
@Slf4j
public class MapObjUtil {

    private final static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * @Author yuchanglong
     * @Date 2018-12-6
     * @Description 实体对象转成Map
     * @Param obj 实体对象
     * @Param mapKeyValue
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public static Map<String, Object> object2Map(Object obj, @Nullable MapKeyValue mapKeyValue) {
        Map<String, Object> map = new HashMap<String, Object>(2);

        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        List<Field> fields = getAllField(clazz);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if (filedTypeName.equalsIgnoreCase("java.util.list") && !StringUtils.contains(field.getGenericType().getTypeName(),"java.lang")){
                    List dataList = (List) field.get(obj);
                    map.putAll(ListObject2Map(dataList, mapKeyValue));
                }else if(StringUtils.contains(filedTypeName,"java.time")){
                    if (field.get(obj) != null){
                        map.put(field.getName(), df.format((TemporalAccessor)field.get(obj)));
                    }else {
                        map.put(field.getName(), null);
                    }
                }else {
                    map.put(field.getName(), field.get(obj));
                }
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
        }
        return map;

    }

    /**
     * @Author yuchanglong
     * @Date 2018-12-13
     * @Description 对象数组中属性值转Map
     * @Param dataList
     * @Param mapKeyValue 属性值所对应的属性对象
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public static Map<String, Object> ListObject2Map(List dataList, MapKeyValue mapKeyValue){
        Map<String, Object> map = new HashMap<String, Object>(2);
        if (dataList == null){
            return map;
        }
        try {
            for (Object o : dataList){
                if (mapKeyValue == null){
                    throw new RuntimeException("没有实例化需要映射的key属性与value属性");
                }
                String[] keySplit = mapKeyValue.getKeyField().split("\\.");
                String[] valueSplit = mapKeyValue.getValueField().split("\\.");

                Object key = getFieldValue(o, keySplit, keySplit.length);
                Object value = getFieldValue(o, valueSplit, valueSplit.length);
                if (StringUtils.isBlank(key.toString())){
                    break;
                }
                map.put(key.toString(), value);
            }
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException: {}", e);
        }
        return map;
    }

    /**
     * @Author yuchanglong
     * @Date 2019-4-8
     * @Description 获取属性说对应的值
     * @Param data
     * @Param feildNames
     * @Param length
     * @return java.lang.reflect.Field
     **/
    private static Object getFieldValue(Object data, String[] feildNames, int length) throws IllegalAccessException{
        if (length > 0){
            List<Field> allField = getAllField(data.getClass(), new ArrayList());
            int index = feildNames.length - length;
            List<Field> fieldList = allField.stream().filter(e -> StringUtils.equals(feildNames[index], e.getName())).collect(Collectors.toList());
            if (fieldList.size() > 0){
                Field field = fieldList.get(0);
                field.setAccessible(true);
                Object result = field.get(data);
                length --;
                result = getFieldValue(result, feildNames, length);
                return result;
            }
            return "";
        }
        return data;
    }
    /**
     * @Author yuchanglong
     * @Date 2018-12-6
     * @Description Map转成实体对象
     * @Param map   map实体对象包含属性
     * @Param clazz 实体对象类型
     * @return T
     **/
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        T obj = null;
        /*try {
            obj = clazz.newInstance();
            List<Field> fieldList = getAllField(clazz);
            for (Field field : fieldList) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                String filedTypeName = field.getType().getName();
                if(map.get(field.getName()) != null){
                    if (filedTypeName.equalsIgnoreCase("java.time.localdate")) {
                        field.set(obj, LocalDate.parse(String.valueOf(map.get(field.getName())), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }else
                    if(filedTypeName.equalsIgnoreCase("java.time.localdatetime")){
                        field.set(obj, LocalDateTime.parse(String.valueOf(map.get(field.getName())), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    }else
                    if (filedTypeName.equals("java.lang.Integer")){
                        field.set(obj, Integer.valueOf((String) map.get(field.getName())));
                    }else
                    if (filedTypeName.equals("java.lang.Double")){
                        field.set(obj, Double.valueOf(map.get(field.getName()).toString()));
                    }else {
                        field.set(obj, map.get(field.getName()));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception: {}", e);
        }

        return obj;*/
        obj = JSON.parseObject(JSON.toJSONString(map), clazz);

        return obj;
    }

    /**
     * @Author yuchanglong
     * @Date 2018-12-6
     * @Description 获取所有属性
     * @Param clazz
     * @Param returnField
     * @return java.util.List<java.lang.reflect.Field>5
     **/
    private static List<Field> getAllField(Class clazz, List<Field> ... returnField){
        List<Field> fieldList = new ArrayList<>();
        if (returnField != null && returnField.length > 0){
            fieldList = returnField[0];
        }
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class supClass = clazz.getSuperclass();
        if (supClass != null && !StringUtils.equals(supClass.getSimpleName(),"Object")){
            getAllField(supClass, fieldList);
        }
        return fieldList;
    }
}
