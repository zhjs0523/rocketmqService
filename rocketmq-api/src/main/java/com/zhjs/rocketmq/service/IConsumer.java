package com.zhjs.rocketmq.service;

import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Created by zhjs on 2018/12/14.
 */
public interface IConsumer {
    /**
     * 消费端解析消息
     * @param msg
     */
    void handlerMessage( MessageExt msg );
}
