package com.zhjs.rocketmq.service.impl;

import com.zhjs.rocketmq.dto.LoanRequest;
import com.zhjs.rocketmq.service.IProducer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhjs on 2018/12/14.
 */
public class Main {
    private IProducer producer;

    @Before
    public void befor() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        producer = (IProducer) context.getBean("producer");
    }

    @Test
    public void testSendMq() {
        LoanRequest loanRequest= new LoanRequest();
        loanRequest.setApplyNo("loan00005432231");
        loanRequest.setCreditNo("credit0000001");
        loanRequest.setEmployeeNo("test1");
        loanRequest.setLendPurpose("test1");
        loanRequest.setLoanCardNo("362226197403220017");
        loanRequest.setBankCode(null);
        loanRequest.setBankName(null);
        loanRequest.setOprTerm("12");
        loanRequest.setPayamt(new BigDecimal(100000));
        loanRequest.setRepayMode("0"); //0   1    2
        loanRequest.setApplyDate(new Date());
        producer.send("loanRequest", loanRequest);

    }
}
