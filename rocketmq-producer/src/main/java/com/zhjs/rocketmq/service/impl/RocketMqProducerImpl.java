package com.zhjs.rocketmq.service.impl;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.common.message.Message;
import com.zhjs.rocketmq.entity.MQEntity;
import com.zhjs.rocketmq.service.IProducer;
import com.zhjs.rocketmq.utils.SerializableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by zhjs on 2018/12/14.
 */
@Service
public class RocketMqProducerImpl implements IProducer,InitializingBean{

    private DefaultMQProducer producer;

    private String producerGroup;

    private String namesrvAddr;

    private Boolean retryAnotherBrokerWhenNotStoreOK;

    private Logger logger = LoggerFactory.getLogger(RocketMqProducerImpl.class);

    @Override
    public void send(String topic, MQEntity entity) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
        String tags = entity.getClass().getName();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic,tags,keys, SerializableUtil.toByte(entity));

        try{
            producer.send(msg);
        }catch(Exception e){
            logger.error("消息发送失败，msg:{}",msg);
            throw new RuntimeException("消息发送失败！",e);
        }
    }

    @Override
    public void send(String topic, MQEntity entity, SendCallback sendCallback) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
        String tags = entity.getClass().getName();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic,tags,keys, SerializableUtil.toByte(entity));

        try{
            producer.send(msg,sendCallback);
        }catch(Exception e){
            logger.error("消息发送失败，msg:{}",msg);
            throw new RuntimeException("消息发送失败！",e);
        }
    }

    @Override
    public void sendOneway(String topic, MQEntity entity) {
        String keys = UUID.randomUUID().toString();
        entity.setMqKey(keys);
        String tags = entity.getClass().getName();
        logger.info("业务:{},tags:{},keys:{},entity:{}",topic, tags, keys, entity);
        Message msg = new Message(topic,tags,keys, SerializableUtil.toByte(entity));

        try{
            producer.sendOneway(msg);
        }catch(Exception e){
            logger.error("消息发送失败，msg:{}",msg);
            throw new RuntimeException("消息发送失败！",e);
        }
    }


    /**
     * spring容器初始化所有属性之后调用此方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultMQProducer();
        producer.setProducerGroup(this.producerGroup);
        producer.setNamesrvAddr(this.namesrvAddr);
        producer.setRetryAnotherBrokerWhenNotStoreOK(this.retryAnotherBrokerWhenNotStoreOK);
        producer.start();

        logger.info("rocketmq producer start....");
    }

    public void destroy(){
        if(null != producer){
            logger.info("rocketmq producer shutdown...");
            producer.shutdown();
        }
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public Boolean getRetryAnotherBrokerWhenNotStoreOK() {
        return retryAnotherBrokerWhenNotStoreOK;
    }

    public void setRetryAnotherBrokerWhenNotStoreOK(Boolean retryAnotherBrokerWhenNotStoreOK) {
        this.retryAnotherBrokerWhenNotStoreOK = retryAnotherBrokerWhenNotStoreOK;
    }
}
