package com.framework.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class GsonUtils {

    /**
     * 将对象转为Json字符串
     *
     * @param obj
     * @return
     */
    public static String parseJson(Object obj) {
        if (obj == null) return null;
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    /**
     * json字符串转对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object json2Obj(String json, Class clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        return gson.fromJson(json, clazz);
    }

    /**
     * 将对象转化成一个新对象
     *
     * @param src
     * @param clazz
     * @return
     */
    public static Object parseObj(Object src, Class clazz) {
        if (src == null) return null;
        String json = parseJson(src);
        return json2Obj(json, clazz);
    }

    /**
     * 转化列表
     *
     * @param list
     * @param clazz
     * @return
     */
    public static List<?> parseList(List<?> list, Class clazz) {
        if (list != null) {
            List newList = new ArrayList<>();
            for (Object object : list) {
                Object obj = parseObj(object, clazz);
                newList.add(obj);
            }
            return newList;
        } else {
            return null;
        }
    }

}
