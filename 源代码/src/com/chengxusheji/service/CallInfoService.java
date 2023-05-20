package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.CallInfo;

import com.chengxusheji.mapper.CallInfoMapper;
@Service
public class CallInfoService {

	@Resource CallInfoMapper callInfoMapper;
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

    /*添加呼叫网管记录*/
    public void addCallInfo(CallInfo callInfo) throws Exception {
    	callInfoMapper.addCallInfo(callInfo);
    }

    /*按照查询条件分页查询呼叫网管记录*/
    public ArrayList<CallInfo> queryCallInfo(UserInfo userObj,String callTime,String handFlag,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_callInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!callTime.equals("")) where = where + " and t_callInfo.callTime like '%" + callTime + "%'";
    	if(!handFlag.equals("")) where = where + " and t_callInfo.handFlag like '%" + handFlag + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return callInfoMapper.queryCallInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<CallInfo> queryCallInfo(UserInfo userObj,String callTime,String handFlag) throws Exception  { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_callInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!callTime.equals("")) where = where + " and t_callInfo.callTime like '%" + callTime + "%'";
    	if(!handFlag.equals("")) where = where + " and t_callInfo.handFlag like '%" + handFlag + "%'";
    	return callInfoMapper.queryCallInfoList(where);
    }

    /*查询所有呼叫网管记录*/
    public ArrayList<CallInfo> queryAllCallInfo()  throws Exception {
        return callInfoMapper.queryCallInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(UserInfo userObj,String callTime,String handFlag) throws Exception {
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_callInfo.userObj='" + userObj.getUser_name() + "'";
    	if(!callTime.equals("")) where = where + " and t_callInfo.callTime like '%" + callTime + "%'";
    	if(!handFlag.equals("")) where = where + " and t_callInfo.handFlag like '%" + handFlag + "%'";
        recordNumber = callInfoMapper.queryCallInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取呼叫网管记录*/
    public CallInfo getCallInfo(int callId) throws Exception  {
        CallInfo callInfo = callInfoMapper.getCallInfo(callId);
        return callInfo;
    }

    
    /*获取到最大的呼叫id*/
    public int getMaxCallId() throws Exception  {
        int maxCallId = callInfoMapper.getMaxCallId();
        return maxCallId;
    }

    
    
    /*更新呼叫网管记录*/
    public void updateCallInfo(CallInfo callInfo) throws Exception {
        callInfoMapper.updateCallInfo(callInfo);
    }

    /*删除一条呼叫网管记录*/
    public void deleteCallInfo (int callId) throws Exception {
        callInfoMapper.deleteCallInfo(callId);
    }

    /*删除多条呼叫网管信息*/
    public int deleteCallInfos (String callIds) throws Exception {
    	String _callIds[] = callIds.split(",");
    	for(String _callId: _callIds) {
    		callInfoMapper.deleteCallInfo(Integer.parseInt(_callId));
    	}
    	return _callIds.length;
    }
}
