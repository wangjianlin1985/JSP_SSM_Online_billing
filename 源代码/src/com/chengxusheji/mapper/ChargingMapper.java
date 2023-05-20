package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Charging;

public interface ChargingMapper {
	/*添加计费方式信息*/
	public void addCharging(Charging charging) throws Exception;

	/*按照查询条件分页查询计费方式记录*/
	public ArrayList<Charging> queryCharging(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有计费方式记录*/
	public ArrayList<Charging> queryChargingList(@Param("where") String where) throws Exception;

	/*按照查询条件的计费方式记录数*/
	public int queryChargingCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条计费方式记录*/
	public Charging getCharging(int chargingId) throws Exception;

	/*更新计费方式记录*/
	public void updateCharging(Charging charging) throws Exception;

	/*删除计费方式记录*/
	public void deleteCharging(int chargingId) throws Exception;

}
