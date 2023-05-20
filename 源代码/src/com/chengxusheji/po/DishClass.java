package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class DishClass {
    /*菜品分类id*/
    private Integer dishClassId;
    public Integer getDishClassId(){
        return dishClassId;
    }
    public void setDishClassId(Integer dishClassId){
        this.dishClassId = dishClassId;
    }

    /*菜品分类名称*/
    @NotEmpty(message="菜品分类名称不能为空")
    private String dishClassName;
    public String getDishClassName() {
        return dishClassName;
    }
    public void setDishClassName(String dishClassName) {
        this.dishClassName = dishClassName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonDishClass=new JSONObject(); 
		jsonDishClass.accumulate("dishClassId", this.getDishClassId());
		jsonDishClass.accumulate("dishClassName", this.getDishClassName());
		return jsonDishClass;
    }}