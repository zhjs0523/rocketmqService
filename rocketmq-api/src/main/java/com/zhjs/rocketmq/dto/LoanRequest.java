package com.zhjs.rocketmq.dto;

/**
 * @since:JDK1.8
 * @author:zhjs
 * @createDate:2018/8/24
 * @Desc:贷款申请
 */


import com.zhjs.rocketmq.entity.MQEntity;

import java.math.BigDecimal;
import java.util.Date;

public class LoanRequest extends MQEntity {

    private static final long serialVersionUID = 5339647958993664240L;

    /** 贷款申请编号，前段生存的唯一编号 */
    private String applyNo;

    /** 额度编号   */
    private String creditNo;

    /**贷款人员工号 */
    private String employeeNo;

    /** 贷款金额 */
    private BigDecimal payamt;

    /**还款方式
     *     0：多次还本、分次付息
     1：分次还本、分次付息
     2：等额本金
     **/
    private String repayMode;

    /**还款期限  1个月、3个月、6个月、9个月、12个月、15个月、18个月、24个月；
     * 1，3， 6
     * */
    private String oprTerm;

    /**用途*/
    private String lendPurpose;

    //申请日期
    private Date applyDate;

    //贷款卡号
    private String loanCardNo;

    //开户行
    private String bankName;

    //联行行号
    private String bankCode ;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public BigDecimal getPayamt() {
        return payamt;
    }

    public void setPayamt(BigDecimal payamt) {
        this.payamt = payamt;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }

    public String getOprTerm() {
        return oprTerm;
    }

    public void setOprTerm(String oprTerm) {
        this.oprTerm = oprTerm;
    }

    public String getLendPurpose() {
        return lendPurpose;
    }

    public void setLendPurpose(String lendPurpose) {
        this.lendPurpose = lendPurpose;
    }

    public String getLoanCardNo() {
        return loanCardNo;
    }

    public void setLoanCardNo(String loanCardNo) {
        this.loanCardNo = loanCardNo;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getCreditNo() {
        return creditNo;
    }

    public void setCreditNo(String creditNo) {
        this.creditNo = creditNo;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }



}