package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Computer;

public interface ComputerMapper {
	/*添加计算机信息*/
	public void addComputer(Computer computer) throws Exception;

	/*按照查询条件分页查询计算机记录*/
	public ArrayList<Computer> queryComputer(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有计算机记录*/
	public ArrayList<Computer> queryComputerList(@Param("where") String where) throws Exception;

	/*按照查询条件的计算机记录数*/
	public int queryComputerCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条计算机记录*/
	public Computer getComputer(String computerNo) throws Exception;

	/*更新计算机记录*/
	public void updateComputer(Computer computer) throws Exception;

	/*删除计算机记录*/
	public void deleteComputer(String computerNo) throws Exception;

}
