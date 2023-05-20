package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.ComputerUse;

public interface ComputerUseMapper {
	/*添加上机记录信息*/
	public void addComputerUse(ComputerUse computerUse) throws Exception;

	/*按照查询条件分页查询上机记录记录*/
	public ArrayList<ComputerUse> queryComputerUse(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有上机记录记录*/
	public ArrayList<ComputerUse> queryComputerUseList(@Param("where") String where) throws Exception;

	/*按照查询条件的上机记录记录数*/
	public int queryComputerUseCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条上机记录记录*/
	public ComputerUse getComputerUse(int cuId) throws Exception;

	/*更新上机记录记录*/
	public void updateComputerUse(ComputerUse computerUse) throws Exception;

	/*删除上机记录记录*/
	public void deleteComputerUse(int cuId) throws Exception;

}
