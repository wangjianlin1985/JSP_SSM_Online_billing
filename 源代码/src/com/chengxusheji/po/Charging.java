package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Charging {
    /*计费id*/
    private Integer chargingId;
    public Integer getChargingId(){
        return chargingId;
    }
    public void setChargingId(Integer chargingId){
        this.chargingId = chargingId;
    }

    /*计费名称*/
    @NotEmpty(message="计费名称不能为空")
    private String chargingName;
    public String getChargingName() {
        return chargingName;
    }
    public void setChargingName(String chargingName) {
        this.chargingName = chargingName;
    }

    /*计费金额*/
    @NotNull(message="必须输入计费金额")
    private Float chargingMoney;
    public Float getChargingMoney() {
        return chargingMoney;
    }
    public void setChargingMoney(Float chargingMoney) {
        this.chargingMoney = chargingMoney;
    }

    /*缴费算法*/
    @NotEmpty(message="缴费算法不能为空")
    private String moneyWay;
    public String getMoneyWay() {
        return moneyWay;
    }
    public void setMoneyWay(String moneyWay) {
        this.moneyWay = moneyWay;
    }

    /*计费说明*/
    @NotEmpty(message="计费说明不能为空")
    private String chargingMemo;
    public String getChargingMemo() {
        return chargingMemo;
    }
    public void setChargingMemo(String chargingMemo) {
        this.chargingMemo = chargingMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCharging=new JSONObject(); 
		jsonCharging.accumulate("chargingId", this.getChargingId());
		jsonCharging.accumulate("chargingName", this.getChargingName());
		jsonCharging.accumulate("chargingMoney", this.getChargingMoney());
		jsonCharging.accumulate("moneyWay", this.getMoneyWay());
		jsonCharging.accumulate("chargingMemo", this.getChargingMemo());
		return jsonCharging;
    }}