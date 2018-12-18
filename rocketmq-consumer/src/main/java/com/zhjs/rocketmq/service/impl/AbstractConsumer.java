package com.zhjs.rocketmq.service.impl;

import com.alibaba.rocketmq.common.message.MessageExt;
import com.zhjs.rocketmq.entity.MQEntity;
import com.zhjs.rocketmq.service.IConsumer;
import com.zhjs.rocketmq.utils.SerializableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Created by zhjs on 2018/12/14.
 */
@Service
public abstract class AbstractConsumer implements IConsumer {
    private Logger logger = LoggerFactory.getLogger(AbstractConsumer.class);

    private String classTypeName;

    @Override
    public void handlerMessage(MessageExt msg) {
        try{
            MQEntity entity = doStart(msg);
            execute(entity);
            doEnd(entity);
        }catch(Exception e){

        }
    }

    /**
     * 解析mq消息前置处理
     * @param msg
     * @throws ClassNotFoundException
     */
    protected MQEntity doStart(MessageExt msg) throws ClassNotFoundException {
        Class<? extends MQEntity> clazz = (Class<? extends MQEntity>) Class.forName(classTypeName);
        return SerializableUtil.parse(msg.getBody(), clazz);
    }

    public abstract void execute(MQEntity entity);

    private void doEnd(MQEntity entity){

    }

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }
}
