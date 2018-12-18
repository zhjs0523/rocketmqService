package com.zhjs.rocketmq.service.impl;


import com.zhjs.rocketmq.dto.LoanRequest;
import com.zhjs.rocketmq.entity.MQEntity;

/**
 * @since:JDK1.8
 * @author:zhjs
 * @createDate:2018/8/24
 * @Desc:
 */



public class LoanRequestConsumer extends AbstractConsumer {

    @Override
    public void execute(MQEntity entity) {
        System.out.println("LoanRequestConsumer 消费消息");
        System.out.println(entity.toString());
    }

}