package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class DishOrder {
    /*点餐id*/
    private Integer orderId;
    public Integer getOrderId(){
        return orderId;
    }
    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    /*菜品名称*/
    private Dish dishObj;
    public Dish getDishObj() {
        return dishObj;
    }
    public void setDishObj(Dish dishObj) {
        this.dishObj = dishObj;
    }

    /*订餐数量*/
    @NotNull(message="必须输入订餐数量")
    private Integer dishNum;
    public Integer getDishNum() {
        return dishNum;
    }
    public void setDishNum(Integer dishNum) {
        this.dishNum = dishNum;
    }

    /*菜品金额*/
    @NotNull(message="必须输入菜品金额")
    private Float dishMoney;
    public Float getDishMoney() {
        return dishMoney;
    }
    public void setDishMoney(Float dishMoney) {
        this.dishMoney = dishMoney;
    }

    /*订单备注*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    /*点餐用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*点餐时间*/
    @NotEmpty(message="点餐时间不能为空")
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*订单状态*/
    @NotEmpty(message="订单状态不能为空")
    private String orderState;
    public String getOrderState() {
        return orderState;
    }
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDishOrder=new JSONObject(); 
		jsonDishOrder.accumulate("orderId", this.getOrderId());
		jsonDishOrder.accumulate("dishObj", this.getDishObj().getDishName());
		jsonDishOrder.accumulate("dishObjPri", this.getDishObj().getDishNo());
		jsonDishOrder.accumulate("dishNum", this.getDishNum());
		jsonDishOrder.accumulate("dishMoney", this.getDishMoney());
		jsonDishOrder.accumulate("orderMemo", this.getOrderMemo());
		jsonDishOrder.accumulate("userObj", this.getUserObj().getName());
		jsonDishOrder.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonDishOrder.accumulate("orderTime", this.getOrderTime().length()>19?this.getOrderTime().substring(0,19):this.getOrderTime());
		jsonDishOrder.accumulate("orderState", this.getOrderState());
		return jsonDishOrder;
    }}