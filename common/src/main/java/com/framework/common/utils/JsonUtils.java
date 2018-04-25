package com.framework.common.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static final String EMPTY = "";
    /**
     * 空的 {@code JSON} 数据 - <code>"{}"</code>。
     */
    public static final String EMPTY_JSON = "{}";
    /**
     * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。
     */
    public static final String EMPTY_JSON_ARRAY = "[]";
    /**
     * 默认的 {@code JSON} 日期/时间字段的格式化模式。
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";

    public static boolean isEmpty(String inStr) {
        boolean reTag = false;
        if (inStr == null || "".equals(inStr)) {
            reTag = true;
        }
        return reTag;
    }

    /**
     * 将对象转成json
     * <strong>此方法可以转换任意对象,注意对象成员有Date类型 可以加上@JsonFormate(pattern="format")注解</strong>
     *
     * @param obj
     * @return
     */
    public static String toFastJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.warn("目标对象 " + obj.getClass().getName()
                    + " 转换 JSON 字符串时，发生异常！", e);
            return null;
        }
    }

    /**
     * 将对象转成json
     * <strong>此方法可以转换任意对象,pattern默认yyyy-MM-dd HH:mm:ss</strong>
     *
     * @param obj
     * @param pattern
     * @return
     */
    public static String toFastJson(Object obj, String pattern) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            if (pattern == null) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            mapper.setDateFormat(dateFormat);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.warn("目标对象 " + obj.getClass().getName()
                    + " 转换 JSON 字符串时，发生异常！", e);
            return null;
        }
    }

    /**
     * 讲给的的JSON 字符串 转化成指定的类型对象
     * <strong>此方法可以转换任意对象,注意对象成员有Date类型 可以加上@JsonFormate(pattern="format")注解</strong>
     *
     * @param json
     * @param reference
     * @return
     */
    public static <T> T fromJson(String json, TypeReference<T> reference) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, reference);
        } catch (Exception e) {
            logger.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e);
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error(json + " 无法转换为 " + clazz.getTypeName() + " 对象!", e);
            return null;
        }
    }

    /**
     * 将JSON 字符串 转化成指定的类型对象
     * <strong>此方法可以转换任意对象,pattern默认yyyy-MM-dd HH:mm:ss</strong>
     *
     * @param json
     * @param reference
     * @param pattern   指定日期格式
     * @return
     */
    public static <T> T fromJson(String json, TypeReference<T> reference, String pattern) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            if (pattern == null) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            mapper.setDateFormat(dateFormat);
            return mapper.readValue(json, reference);
        } catch (JsonParseException e) {
            logger.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e);
            return null;
        } catch (JsonMappingException e) {
            logger.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e);
            return null;
        } catch (IOException e) {
            logger.error(json + " 无法转换为 " + reference.getType().getTypeName() + " 对象!", e);
            return null;
        }
    }

}  

