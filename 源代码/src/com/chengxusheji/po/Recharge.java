package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Recharge {
    /*充值id*/
    private Integer rechargeId;
    public Integer getRechargeId(){
        return rechargeId;
    }
    public void setRechargeId(Integer rechargeId){
        this.rechargeId = rechargeId;
    }

    /*充值用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*充值金额*/
    @NotNull(message="必须输入充值金额")
    private Float chargeMoney;
    public Float getChargeMoney() {
        return chargeMoney;
    }
    public void setChargeMoney(Float chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    /*充值时间*/
    @NotEmpty(message="充值时间不能为空")
    private String chargeTime;
    public String getChargeTime() {
        return chargeTime;
    }
    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    /*备注信息*/
    private String chargeMemo;
    public String getChargeMemo() {
        return chargeMemo;
    }
    public void setChargeMemo(String chargeMemo) {
        this.chargeMemo = chargeMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonRecharge=new JSONObject(); 
		jsonRecharge.accumulate("rechargeId", this.getRechargeId());
		jsonRecharge.accumulate("userObj", this.getUserObj().getName());
		jsonRecharge.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonRecharge.accumulate("chargeMoney", this.getChargeMoney());
		jsonRecharge.accumulate("chargeTime", this.getChargeTime().length()>19?this.getChargeTime().substring(0,19):this.getChargeTime());
		jsonRecharge.accumulate("chargeMemo", this.getChargeMemo());
		return jsonRecharge;
    }}