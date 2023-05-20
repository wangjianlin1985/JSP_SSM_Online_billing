package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Computer {
    /*计算机编号*/
    @NotEmpty(message="计算机编号不能为空")
    private String computerNo;
    public String getComputerNo(){
        return computerNo;
    }
    public void setComputerNo(String computerNo){
        this.computerNo = computerNo;
    }

    /*计算机名称*/
    @NotEmpty(message="计算机名称不能为空")
    private String computerName;
    public String getComputerName() {
        return computerName;
    }
    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    /*所在区域*/
    @NotEmpty(message="所在区域不能为空")
    private String area;
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    /*计算机照片*/
    private String computerPhoto;
    public String getComputerPhoto() {
        return computerPhoto;
    }
    public void setComputerPhoto(String computerPhoto) {
        this.computerPhoto = computerPhoto;
    }

    /*计算机描述*/
    @NotEmpty(message="计算机描述不能为空")
    private String computerDesc;
    public String getComputerDesc() {
        return computerDesc;
    }
    public void setComputerDesc(String computerDesc) {
        this.computerDesc = computerDesc;
    }

    /*电脑状态*/
    @NotEmpty(message="电脑状态不能为空")
    private String computerState;
    public String getComputerState() {
        return computerState;
    }
    public void setComputerState(String computerState) {
        this.computerState = computerState;
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
    	JSONObject jsonComputer=new JSONObject(); 
		jsonComputer.accumulate("computerNo", this.getComputerNo());
		jsonComputer.accumulate("computerName", this.getComputerName());
		jsonComputer.accumulate("area", this.getArea());
		jsonComputer.accumulate("computerPhoto", this.getComputerPhoto());
		jsonComputer.accumulate("computerDesc", this.getComputerDesc());
		jsonComputer.accumulate("computerState", this.getComputerState());
		jsonComputer.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonComputer;
    }}