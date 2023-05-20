package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.Computer;
import com.chengxusheji.po.Charging;
import com.chengxusheji.po.ComputerUse;

import com.chengxusheji.mapper.ComputerUseMapper;
@Service
public class ComputerUseService {

	@Resource ComputerUseMapper computerUseMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加上机记录记录*/
    public void addComputerUse(ComputerUse computerUse) throws Exception {
    	computerUseMapper.addComputerUse(computerUse);
    }

    /*按照查询条件分页查询上机记录记录*/
    public ArrayList<ComputerUse> queryComputerUse(UserInfo userObj,Computer computerObj,Charging chargingObj,String startTime,String jiezhangFlag,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_computerUse.userObj='" + userObj.getUser_name() + "'";
    	if(null != computerObj &&  computerObj.getComputerNo() != null  && !computerObj.getComputerNo().equals(""))  where += " and t_computerUse.computerObj='" + computerObj.getComputerNo() + "'";
    	if(null != chargingObj && chargingObj.getChargingId()!= null && chargingObj.getChargingId()!= 0)  where += " and t_computerUse.chargingObj=" + chargingObj.getChargingId();
    	if(!startTime.equals("")) where = where + " and t_computerUse.startTime like '%" + startTime + "%'";
    	if(!jiezhangFlag.equals("")) where = where + " and t_computerUse.jiezhangFlag like '%" + jiezhangFlag + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return computerUseMapper.queryComputerUse(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<ComputerUse> queryComputerUse(UserInfo userObj,Computer computerObj,Charging chargingObj,String startTime,String jiezhangFlag) throws Exception  { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_computerUse.userObj='" + userObj.getUser_name() + "'";
    	if(null != computerObj &&  computerObj.getComputerNo() != null && !computerObj.getComputerNo().equals(""))  where += " and t_computerUse.computerObj='" + computerObj.getComputerNo() + "'";
    	if(null != chargingObj && chargingObj.getChargingId()!= null && chargingObj.getChargingId()!= 0)  where += " and t_computerUse.chargingObj=" + chargingObj.getChargingId();
    	if(!startTime.equals("")) where = where + " and t_computerUse.startTime like '%" + startTime + "%'";
    	if(!jiezhangFlag.equals("")) where = where + " and t_computerUse.jiezhangFlag like '%" + jiezhangFlag + "%'";
    	return computerUseMapper.queryComputerUseList(where);
    }

    /*查询所有上机记录记录*/
    public ArrayList<ComputerUse> queryAllComputerUse()  throws Exception {
        return computerUseMapper.queryComputerUseList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(UserInfo userObj,Computer computerObj,Charging chargingObj,String startTime,String jiezhangFlag) throws Exception {
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_computerUse.userObj='" + userObj.getUser_name() + "'";
    	if(null != computerObj &&  computerObj.getComputerNo() != null && !computerObj.getComputerNo().equals(""))  where += " and t_computerUse.computerObj='" + computerObj.getComputerNo() + "'";
    	if(null != chargingObj && chargingObj.getChargingId()!= null && chargingObj.getChargingId()!= 0)  where += " and t_computerUse.chargingObj=" + chargingObj.getChargingId();
    	if(!startTime.equals("")) where = where + " and t_computerUse.startTime like '%" + startTime + "%'";
    	if(!jiezhangFlag.equals("")) where = where + " and t_computerUse.jiezhangFlag like '%" + jiezhangFlag + "%'";
        recordNumber = computerUseMapper.queryComputerUseCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取上机记录记录*/
    public ComputerUse getComputerUse(int cuId) throws Exception  {
        ComputerUse computerUse = computerUseMapper.getComputerUse(cuId);
        return computerUse;
    }

    /*更新上机记录记录*/
    public void updateComputerUse(ComputerUse computerUse) throws Exception {
        computerUseMapper.updateComputerUse(computerUse);
    }

    /*删除一条上机记录记录*/
    public void deleteComputerUse (int cuId) throws Exception {
        computerUseMapper.deleteComputerUse(cuId);
    }

    /*删除多条上机记录信息*/
    public int deleteComputerUses (String cuIds) throws Exception {
    	String _cuIds[] = cuIds.split(",");
    	for(String _cuId: _cuIds) {
    		computerUseMapper.deleteComputerUse(Integer.parseInt(_cuId));
    	}
    	return _cuIds.length;
    }
}
