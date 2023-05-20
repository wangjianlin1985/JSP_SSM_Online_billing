package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class ComputerUse {
    /*记录id*/
    private Integer cuId;
    public Integer getCuId(){
        return cuId;
    }
    public void setCuId(Integer cuId){
        this.cuId = cuId;
    }

    /*上机用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*使用电脑*/
    private Computer computerObj;
    public Computer getComputerObj() {
        return computerObj;
    }
    public void setComputerObj(Computer computerObj) {
        this.computerObj = computerObj;
    }

    /*计费方式*/
    private Charging chargingObj;
    public Charging getChargingObj() {
        return chargingObj;
    }
    public void setChargingObj(Charging chargingObj) {
        this.chargingObj = chargingObj;
    }

    /*上机时间*/
    @NotEmpty(message="上机时间不能为空")
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*下机时间*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*是否结账*/
    @NotEmpty(message="是否结账不能为空")
    private String jiezhangFlag;
    public String getJiezhangFlag() {
        return jiezhangFlag;
    }
    public void setJiezhangFlag(String jiezhangFlag) {
        this.jiezhangFlag = jiezhangFlag;
    }

    /*结账金额*/
    @NotNull(message="必须输入结账金额")
    private Float useMoney;
    public Float getUseMoney() {
        return useMoney;
    }
    public void setUseMoney(Float useMoney) {
        this.useMoney = useMoney;
    }

    /*备注信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonComputerUse=new JSONObject(); 
		jsonComputerUse.accumulate("cuId", this.getCuId());
		jsonComputerUse.accumulate("userObj", this.getUserObj().getName());
		jsonComputerUse.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonComputerUse.accumulate("computerObj", this.getComputerObj().getComputerName());
		jsonComputerUse.accumulate("computerObjPri", this.getComputerObj().getComputerNo());
		jsonComputerUse.accumulate("chargingObj", this.getChargingObj().getChargingName());
		jsonComputerUse.accumulate("chargingObjPri", this.getChargingObj().getChargingId());
		jsonComputerUse.accumulate("startTime", this.getStartTime().length()>19?this.getStartTime().substring(0,19):this.getStartTime());
		jsonComputerUse.accumulate("endTime", this.getEndTime());
		jsonComputerUse.accumulate("jiezhangFlag", this.getJiezhangFlag());
		jsonComputerUse.accumulate("useMoney", this.getUseMoney());
		jsonComputerUse.accumulate("memo", this.getMemo());
		return jsonComputerUse;
    }}