package com.jiujiu.xxgame.model;

public class ChargeResult {
    private Integer resultCode = 1;
    private String resultMsg = "";
    private String orderId = "";

    public ChargeResult(Integer resultCode, String resultMsg, String orderId) {
        this.resultCode = resultCode;
        this.orderId = orderId;
        this.resultMsg = resultMsg;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ChargeResult{" +
                "resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
