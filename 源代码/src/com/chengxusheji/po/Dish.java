package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Dish {
    /*菜品编号*/
    @NotEmpty(message="菜品编号不能为空")
    private String dishNo;
    public String getDishNo(){
        return dishNo;
    }
    public void setDishNo(String dishNo){
        this.dishNo = dishNo;
    }

    /*菜品类别*/
    private DishClass dishClassObj;
    public DishClass getDishClassObj() {
        return dishClassObj;
    }
    public void setDishClassObj(DishClass dishClassObj) {
        this.dishClassObj = dishClassObj;
    }

    /*菜品名称*/
    @NotEmpty(message="菜品名称不能为空")
    private String dishName;
    public String getDishName() {
        return dishName;
    }
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    /*菜品图片*/
    private String dishPhoto;
    public String getDishPhoto() {
        return dishPhoto;
    }
    public void setDishPhoto(String dishPhoto) {
        this.dishPhoto = dishPhoto;
    }

    /*菜品单价*/
    @NotNull(message="必须输入菜品单价")
    private Float dishPrice;
    public Float getDishPrice() {
        return dishPrice;
    }
    public void setDishPrice(Float dishPrice) {
        this.dishPrice = dishPrice;
    }

    /*是否推荐*/
    @NotEmpty(message="是否推荐不能为空")
    private String tuijianFlag;
    public String getTuijianFlag() {
        return tuijianFlag;
    }
    public void setTuijianFlag(String tuijianFlag) {
        this.tuijianFlag = tuijianFlag;
    }

    /*上架状态*/
    @NotEmpty(message="上架状态不能为空")
    private String upState;
    public String getUpState() {
        return upState;
    }
    public void setUpState(String upState) {
        this.upState = upState;
    }

    /*浏览量*/
    @NotNull(message="必须输入浏览量")
    private Integer viewNum;
    public Integer getViewNum() {
        return viewNum;
    }
    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    /*菜品描述*/
    @NotEmpty(message="菜品描述不能为空")
    private String dishDesc;
    public String getDishDesc() {
        return dishDesc;
    }
    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    /*添加时间*/
    @NotEmpty(message="添加时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDish=new JSONObject(); 
		jsonDish.accumulate("dishNo", this.getDishNo());
		jsonDish.accumulate("dishClassObj", this.getDishClassObj().getDishClassName());
		jsonDish.accumulate("dishClassObjPri", this.getDishClassObj().getDishClassId());
		jsonDish.accumulate("dishName", this.getDishName());
		jsonDish.accumulate("dishPhoto", this.getDishPhoto());
		jsonDish.accumulate("dishPrice", this.getDishPrice());
		jsonDish.accumulate("tuijianFlag", this.getTuijianFlag());
		jsonDish.accumulate("upState", this.getUpState());
		jsonDish.accumulate("viewNum", this.getViewNum());
		jsonDish.accumulate("dishDesc", this.getDishDesc());
		jsonDish.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonDish;
    }}