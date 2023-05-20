package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DishClass;

import com.chengxusheji.mapper.DishClassMapper;
@Service
public class DishClassService {

	@Resource DishClassMapper dishClassMapper;
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

    /*添加菜品分类记录*/
    public void addDishClass(DishClass dishClass) throws Exception {
    	dishClassMapper.addDishClass(dishClass);
    }

    /*按照查询条件分页查询菜品分类记录*/
    public ArrayList<DishClass> queryDishClass(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return dishClassMapper.queryDishClass(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<DishClass> queryDishClass() throws Exception  { 
     	String where = "where 1=1";
    	return dishClassMapper.queryDishClassList(where);
    }

    /*查询所有菜品分类记录*/
    public ArrayList<DishClass> queryAllDishClass()  throws Exception {
        return dishClassMapper.queryDishClassList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = dishClassMapper.queryDishClassCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取菜品分类记录*/
    public DishClass getDishClass(int dishClassId) throws Exception  {
        DishClass dishClass = dishClassMapper.getDishClass(dishClassId);
        return dishClass;
    }

    /*更新菜品分类记录*/
    public void updateDishClass(DishClass dishClass) throws Exception {
        dishClassMapper.updateDishClass(dishClass);
    }

    /*删除一条菜品分类记录*/
    public void deleteDishClass (int dishClassId) throws Exception {
        dishClassMapper.deleteDishClass(dishClassId);
    }

    /*删除多条菜品分类信息*/
    public int deleteDishClasss (String dishClassIds) throws Exception {
    	String _dishClassIds[] = dishClassIds.split(",");
    	for(String _dishClassId: _dishClassIds) {
    		dishClassMapper.deleteDishClass(Integer.parseInt(_dishClassId));
    	}
    	return _dishClassIds.length;
    }
}
