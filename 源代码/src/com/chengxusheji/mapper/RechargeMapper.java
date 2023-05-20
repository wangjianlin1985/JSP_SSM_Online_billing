package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Recharge;

public interface RechargeMapper {
	/*添加会员充值信息*/
	public void addRecharge(Recharge recharge) throws Exception;

	/*按照查询条件分页查询会员充值记录*/
	public ArrayList<Recharge> queryRecharge(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有会员充值记录*/
	public ArrayList<Recharge> queryRechargeList(@Param("where") String where) throws Exception;

	/*按照查询条件的会员充值记录数*/
	public int queryRechargeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条会员充值记录*/
	public Recharge getRecharge(int rechargeId) throws Exception;

	/*更新会员充值记录*/
	public void updateRecharge(Recharge recharge) throws Exception;

	/*删除会员充值记录*/
	public void deleteRecharge(int rechargeId) throws Exception;

}
