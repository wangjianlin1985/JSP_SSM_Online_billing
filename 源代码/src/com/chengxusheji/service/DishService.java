package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DishClass;
import com.chengxusheji.po.Dish;

import com.chengxusheji.mapper.DishMapper;
@Service
public class DishService {

	@Resource DishMapper dishMapper;
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

    /*添加菜品记录*/
    public void addDish(Dish dish) throws Exception {
    	dishMapper.addDish(dish);
    }

    /*按照查询条件分页查询菜品记录*/
    public ArrayList<Dish> queryDish(String dishNo,DishClass dishClassObj,String dishName,String tuijianFlag,String upState,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!dishNo.equals("")) where = where + " and t_dish.dishNo like '%" + dishNo + "%'";
    	if(null != dishClassObj && dishClassObj.getDishClassId()!= null && dishClassObj.getDishClassId()!= 0)  where += " and t_dish.dishClassObj=" + dishClassObj.getDishClassId();
    	if(!dishName.equals("")) where = where + " and t_dish.dishName like '%" + dishName + "%'";
    	if(!tuijianFlag.equals("")) where = where + " and t_dish.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!upState.equals("")) where = where + " and t_dish.upState like '%" + upState + "%'";
    	if(!addTime.equals("")) where = where + " and t_dish.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return dishMapper.queryDish(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Dish> queryDish(String dishNo,DishClass dishClassObj,String dishName,String tuijianFlag,String upState,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!dishNo.equals("")) where = where + " and t_dish.dishNo like '%" + dishNo + "%'";
    	if(null != dishClassObj && dishClassObj.getDishClassId()!= null && dishClassObj.getDishClassId()!= 0)  where += " and t_dish.dishClassObj=" + dishClassObj.getDishClassId();
    	if(!dishName.equals("")) where = where + " and t_dish.dishName like '%" + dishName + "%'";
    	if(!tuijianFlag.equals("")) where = where + " and t_dish.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!upState.equals("")) where = where + " and t_dish.upState like '%" + upState + "%'";
    	if(!addTime.equals("")) where = where + " and t_dish.addTime like '%" + addTime + "%'";
    	return dishMapper.queryDishList(where);
    }

    /*查询所有菜品记录*/
    public ArrayList<Dish> queryAllDish()  throws Exception {
        return dishMapper.queryDishList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String dishNo,DishClass dishClassObj,String dishName,String tuijianFlag,String upState,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(!dishNo.equals("")) where = where + " and t_dish.dishNo like '%" + dishNo + "%'";
    	if(null != dishClassObj && dishClassObj.getDishClassId()!= null && dishClassObj.getDishClassId()!= 0)  where += " and t_dish.dishClassObj=" + dishClassObj.getDishClassId();
    	if(!dishName.equals("")) where = where + " and t_dish.dishName like '%" + dishName + "%'";
    	if(!tuijianFlag.equals("")) where = where + " and t_dish.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!upState.equals("")) where = where + " and t_dish.upState like '%" + upState + "%'";
    	if(!addTime.equals("")) where = where + " and t_dish.addTime like '%" + addTime + "%'";
        recordNumber = dishMapper.queryDishCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取菜品记录*/
    public Dish getDish(String dishNo) throws Exception  {
        Dish dish = dishMapper.getDish(dishNo);
        return dish;
    }

    /*更新菜品记录*/
    public void updateDish(Dish dish) throws Exception {
        dishMapper.updateDish(dish);
    }

    /*删除一条菜品记录*/
    public void deleteDish (String dishNo) throws Exception {
        dishMapper.deleteDish(dishNo);
    }

    /*删除多条菜品信息*/
    public int deleteDishs (String dishNos) throws Exception {
    	String _dishNos[] = dishNos.split(",");
    	for(String _dishNo: _dishNos) {
    		dishMapper.deleteDish(_dishNo);
    	}
    	return _dishNos.length;
    }
}
