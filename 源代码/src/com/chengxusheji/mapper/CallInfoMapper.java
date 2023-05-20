package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.CallInfo;

public interface CallInfoMapper {
	/*添加呼叫网管信息*/
	public void addCallInfo(CallInfo callInfo) throws Exception;

	/*按照查询条件分页查询呼叫网管记录*/
	public ArrayList<CallInfo> queryCallInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有呼叫网管记录*/
	public ArrayList<CallInfo> queryCallInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的呼叫网管记录数*/
	public int queryCallInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条呼叫网管记录*/
	public CallInfo getCallInfo(int callId) throws Exception;

	/*更新呼叫网管记录*/
	public void updateCallInfo(CallInfo callInfo) throws Exception;

	/*删除呼叫网管记录*/
	public void deleteCallInfo(int callId) throws Exception;

	/*获取到最大的callId记录*/
	public int getMaxCallId();

}
