package com.zhjs.rocketmq.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhjs on 2018/12/14.
 */
public class MQEntity implements Serializable{

    private Map<String, Object> extObj = new LinkedHashMap<String, Object>();

    private String mqId ;

    private String mqKey;

    /**
     * 添加附加字段
     * @param key
     * @param value
     */
    public void addExt(String key , Object value){
        extObj.put(key, value);
    }

    /**
     * 获取附加字段
     * @param key
     */
    public void getExt(String key ){
        extObj.get(key);
    }

    public String getMqId() {
        return mqId;
    }

    public void setMqId(String mqId) {
        this.mqId = mqId;
    }

    public String getMqKey() {
        return mqKey;
    }

    public void setMqKey(String mqKey) {
        this.mqKey = mqKey;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
