package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class CallInfo {
    /*记录id*/
    private Integer callId;
    public Integer getCallId(){
        return callId;
    }
    public void setCallId(Integer callId){
        this.callId = callId;
    }

    /*呼叫会员*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*呼叫时间*/
    @NotEmpty(message="呼叫时间不能为空")
    private String callTime;
    public String getCallTime() {
        return callTime;
    }
    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    /*处理状态*/
    @NotEmpty(message="处理状态不能为空")
    private String handFlag;
    public String getHandFlag() {
        return handFlag;
    }
    public void setHandFlag(String handFlag) {
        this.handFlag = handFlag;
    }

    /*处理备注*/
    private String handMemo;
    public String getHandMemo() {
        return handMemo;
    }
    public void setHandMemo(String handMemo) {
        this.handMemo = handMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCallInfo=new JSONObject(); 
		jsonCallInfo.accumulate("callId", this.getCallId());
		jsonCallInfo.accumulate("userObj", this.getUserObj().getName());
		jsonCallInfo.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonCallInfo.accumulate("callTime", this.getCallTime().length()>19?this.getCallTime().substring(0,19):this.getCallTime());
		jsonCallInfo.accumulate("handFlag", this.getHandFlag());
		jsonCallInfo.accumulate("handMemo", this.getHandMemo());
		return jsonCallInfo;
    }}