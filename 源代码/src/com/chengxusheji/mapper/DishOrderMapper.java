package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.DishOrder;

public interface DishOrderMapper {
	/*添加点餐信息*/
	public void addDishOrder(DishOrder dishOrder) throws Exception;

	/*按照查询条件分页查询点餐记录*/
	public ArrayList<DishOrder> queryDishOrder(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有点餐记录*/
	public ArrayList<DishOrder> queryDishOrderList(@Param("where") String where) throws Exception;

	/*按照查询条件的点餐记录数*/
	public int queryDishOrderCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条点餐记录*/
	public DishOrder getDishOrder(int orderId) throws Exception;

	/*更新点餐记录*/
	public void updateDishOrder(DishOrder dishOrder) throws Exception;

	/*删除点餐记录*/
	public void deleteDishOrder(int orderId) throws Exception;

}
