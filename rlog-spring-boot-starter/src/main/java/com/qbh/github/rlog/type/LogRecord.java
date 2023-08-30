package com.qbh.github.rlog.type;

import java.util.Arrays;
import java.util.Date;

/**
 * @author QBH
 * @date 2022/8/30
 */
public class LogRecord {
    private Object operator;

    private String uniqueId;

    private String bizNo;

    private String payload;

    private Object[] args;

    private Object returnValue;

    private Throwable throwable;

    /** SUCCESS = 1, FAIL = 0 */
    private Integer executeSuccess;

    private String errorMsg;

    private Date createdTime;

    public Object getOperator() {
        return operator;
    }

    public void setOperator(Object operator) {
        this.operator = operator;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }


    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getExecuteSuccess() {
        return executeSuccess;
    }

    public void setExecuteSuccess(Integer executeSuccess) {
        this.executeSuccess = executeSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String toString() {
        return "LogRecord{" + "operator=" + operator + ", uniqueId='" + uniqueId + '\''
            + ", bizNo='" + bizNo + '\'' + ", payload='" + payload + '\'' + ", args="
            + Arrays.toString(args) + ", returnValue=" + returnValue + ", throwable=" + throwable
            + ", executeSuccess=" + executeSuccess + ", errorMsg='" + errorMsg + '\''
            + ", createdTime=" + createdTime + '}';
    }
}
