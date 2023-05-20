package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Computer;

import com.chengxusheji.mapper.ComputerMapper;
@Service
public class ComputerService {

	@Resource ComputerMapper computerMapper;
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

    /*添加计算机记录*/
    public void addComputer(Computer computer) throws Exception {
    	computerMapper.addComputer(computer);
    }

    /*按照查询条件分页查询计算机记录*/
    public ArrayList<Computer> queryComputer(String computerNo,String computerName,String area,String computerState,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!computerNo.equals("")) where = where + " and t_computer.computerNo like '%" + computerNo + "%'";
    	if(!computerName.equals("")) where = where + " and t_computer.computerName like '%" + computerName + "%'";
    	if(!area.equals("")) where = where + " and t_computer.area like '%" + area + "%'";
    	if(!computerState.equals("")) where = where + " and t_computer.computerState like '%" + computerState + "%'";
    	if(!addTime.equals("")) where = where + " and t_computer.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return computerMapper.queryComputer(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Computer> queryComputer(String computerNo,String computerName,String area,String computerState,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!computerNo.equals("")) where = where + " and t_computer.computerNo like '%" + computerNo + "%'";
    	if(!computerName.equals("")) where = where + " and t_computer.computerName like '%" + computerName + "%'";
    	if(!area.equals("")) where = where + " and t_computer.area like '%" + area + "%'";
    	if(!computerState.equals("")) where = where + " and t_computer.computerState like '%" + computerState + "%'";
    	if(!addTime.equals("")) where = where + " and t_computer.addTime like '%" + addTime + "%'";
    	return computerMapper.queryComputerList(where);
    }

    /*查询所有计算机记录*/
    public ArrayList<Computer> queryAllComputer()  throws Exception {
        return computerMapper.queryComputerList("where 1=1");
    }
    
    /*查询所有计算机记录*/
    public ArrayList<Computer> queryKxAllComputer()  throws Exception {
        return computerMapper.queryComputerList("where 1=1 and t_computer.computerState='空闲中'");
    }
    

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String computerNo,String computerName,String area,String computerState,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(!computerNo.equals("")) where = where + " and t_computer.computerNo like '%" + computerNo + "%'";
    	if(!computerName.equals("")) where = where + " and t_computer.computerName like '%" + computerName + "%'";
    	if(!area.equals("")) where = where + " and t_computer.area like '%" + area + "%'";
    	if(!computerState.equals("")) where = where + " and t_computer.computerState like '%" + computerState + "%'";
    	if(!addTime.equals("")) where = where + " and t_computer.addTime like '%" + addTime + "%'";
        recordNumber = computerMapper.queryComputerCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取计算机记录*/
    public Computer getComputer(String computerNo) throws Exception  {
        Computer computer = computerMapper.getComputer(computerNo);
        return computer;
    }

    /*更新计算机记录*/
    public void updateComputer(Computer computer) throws Exception {
        computerMapper.updateComputer(computer);
    }

    /*删除一条计算机记录*/
    public void deleteComputer (String computerNo) throws Exception {
        computerMapper.deleteComputer(computerNo);
    }

    /*删除多条计算机信息*/
    public int deleteComputers (String computerNos) throws Exception {
    	String _computerNos[] = computerNos.split(",");
    	for(String _computerNo: _computerNos) {
    		computerMapper.deleteComputer(_computerNo);
    	}
    	return _computerNos.length;
    }
}
