package com.zhjs.rocketmq.service.impl;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.*;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import sun.rmi.rmic.iiop.ClassPathLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhjs on 2018/12/14.
 */
public class RocketMqConsumerImpl implements InitializingBean{

    private Logger logger = LoggerFactory.getLogger(RocketMqConsumerImpl.class);

    private DefaultMQPushConsumer consumer;

    private String namesrvAddr;

    private String consumerGroup;

    private String messageModel;

    private String messageListener;

    private Map<String, AbstractConsumer> handlermap = new HashMap<String, AbstractConsumer>();


    @Override
    public void afterPropertiesSet() throws Exception {
        initializingMessageSelector();
    }

    private void initializingMessageSelector() throws MQClientException {
        consumer = new DefaultMQPushConsumer();
        if(!StringUtils.isEmpty(this.consumerGroup) && this.consumerGroup.trim().length() > 0){
            consumer.setConsumerGroup(this.consumerGroup);
        }
        consumer.setNamesrvAddr(this.namesrvAddr);
        consumer.setConsumeMessageBatchMaxSize(1);
        if("BROADCASTING".equals(this.messageModel)){
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }else if ("CLUSTERING".equals(this.messageModel)){
            consumer.setMessageModel(MessageModel.CLUSTERING);
        }else{
            logger.error("messageModel should be BROADCASTING or CLUSTERING");
            throw new RuntimeException("messageModel should be BROADCASTING or CLUSTERING");
        }
        if(!CollectionUtils.isEmpty(handlermap)){
            for (String topic : handlermap.keySet()) {
                consumer.subscribe(topic,"*");
                logger.info("consumer subscribe topic:{},*",topic);
            }
        }else{
            logger.error("you should provide at least one message handler");
            throw new RuntimeException("you should provide at least one message handler");
        }

        //设置consumer第一次启动是从队列头部还是尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        if("CONCURRENTLY".equals(messageListener)){
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    try{
                        if(!CollectionUtils.isEmpty(msgs)){
                            for (MessageExt msg : msgs) {
                                logger.info("start consume message,msgId:{},topic:{},tags:{},body:{}",msg.getMsgId(),msg.getTopic(),msg.getTags(),msg.getBody());
                                AbstractConsumer handler = handlermap.get(msg.getTopic());
                                if(handler != null){
                                    handler.handlerMessage(msg);
                                }
                                logger.info("consumer consume topic:{} success",msg.getTopic());
                            }
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }catch(Exception e){
                        logger.error("consumer consume fail");
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
            });
        }else if ("ORDERLY".equals(messageListener)){
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    try{
                        if(!CollectionUtils.isEmpty(msgs)){
                            for (MessageExt msg : msgs) {
                                logger.info("start consume message,msgId:{},topic:{},tags:{},body:{}",msg.getMsgId(),msg.getTopic(),msg.getTags(),msg.getBody());
                                AbstractConsumer handler = handlermap.get(msg.getTopic());
                                if(handler != null){
                                    handler.handlerMessage(msg);
                                    //可以是url  根据topic取到对应的URL  发送http请求到消费者端
                                }
                                logger.info("consumer consume topic:{} success",msg.getTopic());
                            }
                        }
                        return ConsumeOrderlyStatus.SUCCESS;
                    }catch(Exception e){
                        logger.error("consumer consume fail");
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                }
            });
        }

        consumer.start();
        logger.info("comsumer start success...");
    }

    public void destroy() throws Exception {
        if (consumer != null) {
            consumer.shutdown();
            logger.debug( "consumer shutdown!" );
        }
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }


    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setMessageModel(String messageModel) {
        this.messageModel = messageModel;
    }


    public void setMessageListener(String messageListener) {
        this.messageListener = messageListener;
    }

    public void setHandlermap(Map<String, AbstractConsumer> handlermap) {
        this.handlermap = handlermap;
    }
}
