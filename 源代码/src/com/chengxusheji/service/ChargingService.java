package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Charging;

import com.chengxusheji.mapper.ChargingMapper;
@Service
public class ChargingService {

	@Resource ChargingMapper chargingMapper;
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

    /*添加计费方式记录*/
    public void addCharging(Charging charging) throws Exception {
    	chargingMapper.addCharging(charging);
    }

    /*按照查询条件分页查询计费方式记录*/
    public ArrayList<Charging> queryCharging(String moneyWay,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!moneyWay.equals("")) where = where + " and t_charging.moneyWay like '%" + moneyWay + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return chargingMapper.queryCharging(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Charging> queryCharging(String moneyWay) throws Exception  { 
     	String where = "where 1=1";
    	if(!moneyWay.equals("")) where = where + " and t_charging.moneyWay like '%" + moneyWay + "%'";
    	return chargingMapper.queryChargingList(where);
    }

    /*查询所有计费方式记录*/
    public ArrayList<Charging> queryAllCharging()  throws Exception {
        return chargingMapper.queryChargingList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String moneyWay) throws Exception {
     	String where = "where 1=1";
    	if(!moneyWay.equals("")) where = where + " and t_charging.moneyWay like '%" + moneyWay + "%'";
        recordNumber = chargingMapper.queryChargingCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取计费方式记录*/
    public Charging getCharging(int chargingId) throws Exception  {
        Charging charging = chargingMapper.getCharging(chargingId);
        return charging;
    }

    /*更新计费方式记录*/
    public void updateCharging(Charging charging) throws Exception {
        chargingMapper.updateCharging(charging);
    }

    /*删除一条计费方式记录*/
    public void deleteCharging (int chargingId) throws Exception {
        chargingMapper.deleteCharging(chargingId);
    }

    /*删除多条计费方式信息*/
    public int deleteChargings (String chargingIds) throws Exception {
    	String _chargingIds[] = chargingIds.split(",");
    	for(String _chargingId: _chargingIds) {
    		chargingMapper.deleteCharging(Integer.parseInt(_chargingId));
    	}
    	return _chargingIds.length;
    }
}
