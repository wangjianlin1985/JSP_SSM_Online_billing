package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Dish;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.DishOrder;

import com.chengxusheji.mapper.DishOrderMapper;
@Service
public class DishOrderService {

	@Resource DishOrderMapper dishOrderMapper;
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

    /*添加点餐记录*/
    public void addDishOrder(DishOrder dishOrder) throws Exception {
    	dishOrderMapper.addDishOrder(dishOrder);
    }

    /*按照查询条件分页查询点餐记录*/
    public ArrayList<DishOrder> queryDishOrder(Dish dishObj,UserInfo userObj,String orderTime,String orderState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != dishObj &&  dishObj.getDishNo() != null  && !dishObj.getDishNo().equals(""))  where += " and t_dishOrder.dishObj='" + dishObj.getDishNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_dishOrder.userObj='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) where = where + " and t_dishOrder.orderTime like '%" + orderTime + "%'";
    	if(!orderState.equals("")) where = where + " and t_dishOrder.orderState like '%" + orderState + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return dishOrderMapper.queryDishOrder(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<DishOrder> queryDishOrder(Dish dishObj,UserInfo userObj,String orderTime,String orderState) throws Exception  { 
     	String where = "where 1=1";
    	if(null != dishObj &&  dishObj.getDishNo() != null && !dishObj.getDishNo().equals(""))  where += " and t_dishOrder.dishObj='" + dishObj.getDishNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_dishOrder.userObj='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) where = where + " and t_dishOrder.orderTime like '%" + orderTime + "%'";
    	if(!orderState.equals("")) where = where + " and t_dishOrder.orderState like '%" + orderState + "%'";
    	return dishOrderMapper.queryDishOrderList(where);
    }

    /*查询所有点餐记录*/
    public ArrayList<DishOrder> queryAllDishOrder()  throws Exception {
        return dishOrderMapper.queryDishOrderList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Dish dishObj,UserInfo userObj,String orderTime,String orderState) throws Exception {
     	String where = "where 1=1";
    	if(null != dishObj &&  dishObj.getDishNo() != null && !dishObj.getDishNo().equals(""))  where += " and t_dishOrder.dishObj='" + dishObj.getDishNo() + "'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_dishOrder.userObj='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) where = where + " and t_dishOrder.orderTime like '%" + orderTime + "%'";
    	if(!orderState.equals("")) where = where + " and t_dishOrder.orderState like '%" + orderState + "%'";
        recordNumber = dishOrderMapper.queryDishOrderCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取点餐记录*/
    public DishOrder getDishOrder(int orderId) throws Exception  {
        DishOrder dishOrder = dishOrderMapper.getDishOrder(orderId);
        return dishOrder;
    }

    /*更新点餐记录*/
    public void updateDishOrder(DishOrder dishOrder) throws Exception {
        dishOrderMapper.updateDishOrder(dishOrder);
    }

    /*删除一条点餐记录*/
    public void deleteDishOrder (int orderId) throws Exception {
        dishOrderMapper.deleteDishOrder(orderId);
    }

    /*删除多条点餐信息*/
    public int deleteDishOrders (String orderIds) throws Exception {
    	String _orderIds[] = orderIds.split(",");
    	for(String _orderId: _orderIds) {
    		dishOrderMapper.deleteDishOrder(Integer.parseInt(_orderId));
    	}
    	return _orderIds.length;
    }
}
