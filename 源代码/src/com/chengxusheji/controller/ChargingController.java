package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.ChargingService;
import com.chengxusheji.po.Charging;

//Charging管理控制层
@Controller
@RequestMapping("/Charging")
public class ChargingController extends BaseController {

    /*业务层对象*/
    @Resource ChargingService chargingService;

	@InitBinder("charging")
	public void initBinderCharging(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("charging.");
	}
	/*跳转到添加Charging视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Charging());
		return "Charging_add";
	}

	/*客户端ajax方式提交添加计费方式信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Charging charging, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        chargingService.addCharging(charging);
        message = "计费方式添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询计费方式信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String moneyWay,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (moneyWay == null) moneyWay = "";
		if(rows != 0)chargingService.setRows(rows);
		List<Charging> chargingList = chargingService.queryCharging(moneyWay, page);
	    /*计算总的页数和总的记录数*/
	    chargingService.queryTotalPageAndRecordNumber(moneyWay);
	    /*获取到总的页码数目*/
	    int totalPage = chargingService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = chargingService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Charging charging:chargingList) {
			JSONObject jsonCharging = charging.getJsonObject();
			jsonArray.put(jsonCharging);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询计费方式信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Charging> chargingList = chargingService.queryAllCharging();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Charging charging:chargingList) {
			JSONObject jsonCharging = new JSONObject();
			jsonCharging.accumulate("chargingId", charging.getChargingId());
			jsonCharging.accumulate("chargingName", charging.getChargingName());
			jsonArray.put(jsonCharging);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询计费方式信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String moneyWay,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (moneyWay == null) moneyWay = "";
		List<Charging> chargingList = chargingService.queryCharging(moneyWay, currentPage);
	    /*计算总的页数和总的记录数*/
	    chargingService.queryTotalPageAndRecordNumber(moneyWay);
	    /*获取到总的页码数目*/
	    int totalPage = chargingService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = chargingService.getRecordNumber();
	    request.setAttribute("chargingList",  chargingList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("moneyWay", moneyWay);
		return "Charging/charging_frontquery_result"; 
	}

     /*前台查询Charging信息*/
	@RequestMapping(value="/{chargingId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer chargingId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键chargingId获取Charging对象*/
        Charging charging = chargingService.getCharging(chargingId);

        request.setAttribute("charging",  charging);
        return "Charging/charging_frontshow";
	}

	/*ajax方式显示计费方式修改jsp视图页*/
	@RequestMapping(value="/{chargingId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer chargingId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键chargingId获取Charging对象*/
        Charging charging = chargingService.getCharging(chargingId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonCharging = charging.getJsonObject();
		out.println(jsonCharging.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新计费方式信息*/
	@RequestMapping(value = "/{chargingId}/update", method = RequestMethod.POST)
	public void update(@Validated Charging charging, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			chargingService.updateCharging(charging);
			message = "计费方式更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "计费方式更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除计费方式信息*/
	@RequestMapping(value="/{chargingId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer chargingId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  chargingService.deleteCharging(chargingId);
	            request.setAttribute("message", "计费方式删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "计费方式删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条计费方式记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String chargingIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = chargingService.deleteChargings(chargingIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出计费方式信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String moneyWay, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(moneyWay == null) moneyWay = "";
        List<Charging> chargingList = chargingService.queryCharging(moneyWay);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Charging信息记录"; 
        String[] headers = { "计费id","计费名称","计费金额","缴费算法"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<chargingList.size();i++) {
        	Charging charging = chargingList.get(i); 
        	dataset.add(new String[]{charging.getChargingId() + "",charging.getChargingName(),charging.getChargingMoney() + "",charging.getMoneyWay()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Charging.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
