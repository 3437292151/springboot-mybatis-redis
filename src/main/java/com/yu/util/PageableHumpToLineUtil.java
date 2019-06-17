package com.yu.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Pageable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: yuchanglong
 * @Date: 2018-12-18
 * @Description: 驼峰转下划线 工具
 */
public class PageableHumpToLineUtil {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * @Author yuchanglong
     * @Date 2018-12-18
     * @Description 获取sort值驼峰转下划线
     * @Param Pageable
     * @return java.lang.String
     **/
    public static String getHumpToLineSort(Pageable pageable){
        if (pageable.getSort() == null || StringUtils.equals(pageable.getSort().toString(), "UNSORTED")){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        String[] split = pageable.getSort().toString().split(",");
        for (String str : split){
            String[] order = str.split(": ");
            sb.append(humpToLine(order[0])).append(" ").append(order[1]).append(", ");
        }
        return sb.delete(sb.length()-2, sb.length()).toString();
    }

    /**
     * @Author yuchanglong
     * @Date 2018-12-18
     * @Description 驼峰转下划线
     * @Param humStr
     * @return java.lang.String
     **/
    public static String humpToLine(String humStr){
        Matcher humpMatcher = humpPattern.matcher(humStr);
        StringBuffer sb = new StringBuffer();
        while(humpMatcher.find()){
            humpMatcher.appendReplacement(sb, "_" + humpMatcher.group(0).toLowerCase());
        }
        humpMatcher.appendTail(sb);
        return sb.toString();
    }

}
