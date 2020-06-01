package com.springboot.mybatis.entity;

import org.springframework.stereotype.Component;

@Component
public class GoodParams {
    // 编码
    private int gpId;
    // 产品参数名
    private String gpParamName;
    // 产品参数值
    private String gpParamValue;
    // 产品id
    private int goodsId;
    // 产品序号
    private int gpOrder;

    public int getGpId() {
        return gpId;
    }

    public void setGpId(int gpId) {
        this.gpId = gpId;
    }

    public String getGpParamName() {
        return gpParamName;
    }

    public void setGpParamName(String gpParamName) {
        this.gpParamName = gpParamName;
    }

    public String getGpParamValue() {
        return gpParamValue;
    }

    public void setGpParamValue(String gpParamValue) {
        this.gpParamValue = gpParamValue;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGpOrder() {
        return gpOrder;
    }

    public void setGpOrder(int gpOrder) {
        this.gpOrder = gpOrder;
    }
}
