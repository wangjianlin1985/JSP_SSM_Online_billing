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
import com.chengxusheji.service.ComputerUseService;
import com.chengxusheji.po.ComputerUse;
import com.chengxusheji.service.ChargingService;
import com.chengxusheji.po.Charging;
import com.chengxusheji.service.ComputerService;
import com.chengxusheji.po.Computer;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//ComputerUse管理控制层
@Controller
@RequestMapping("/ComputerUse")
public class ComputerUseController extends BaseController {

    /*业务层对象*/
    @Resource ComputerUseService computerUseService;

    @Resource ChargingService chargingService;
    @Resource ComputerService computerService;
    @Resource UserInfoService userInfoService;
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("computerObj")
	public void initBindercomputerObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("computerObj.");
	}
	@InitBinder("chargingObj")
	public void initBinderchargingObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("chargingObj.");
	}
	@InitBinder("computerUse")
	public void initBinderComputerUse(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("computerUse.");
	}
	/*跳转到添加ComputerUse视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new ComputerUse());
		/*查询所有的Charging信息*/
		List<Charging> chargingList = chargingService.queryAllCharging();
		request.setAttribute("chargingList", chargingList);
		/*查询所有的Computer信息*/
		List<Computer> computerList = computerService.queryAllComputer();
		request.setAttribute("computerList", computerList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "ComputerUse_add";
	}

	/*客户端ajax方式提交添加上机记录信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated ComputerUse computerUse, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        computerUseService.addComputerUse(computerUse);
        
        String computerNo = computerUse.getComputerObj().getComputerNo();
        Computer computer= computerService.getComputer(computerNo);
        computer.setComputerState("使用中");
        computerService.updateComputer(computer);
        
        
        message = "上机记录添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询上机记录信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("userObj") UserInfo userObj,@ModelAttribute("computerObj") Computer computerObj,@ModelAttribute("chargingObj") Charging chargingObj,String startTime,String jiezhangFlag,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (startTime == null) startTime = "";
		if (jiezhangFlag == null) jiezhangFlag = "";
		if(rows != 0)computerUseService.setRows(rows);
		List<ComputerUse> computerUseList = computerUseService.queryComputerUse(userObj, computerObj, chargingObj, startTime, jiezhangFlag, page);
	    /*计算总的页数和总的记录数*/
	    computerUseService.queryTotalPageAndRecordNumber(userObj, computerObj, chargingObj, startTime, jiezhangFlag);
	    /*获取到总的页码数目*/
	    int totalPage = computerUseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = computerUseService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(ComputerUse computerUse:computerUseList) {
			JSONObject jsonComputerUse = computerUse.getJsonObject();
			jsonArray.put(jsonComputerUse);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询上机记录信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<ComputerUse> computerUseList = computerUseService.queryAllComputerUse();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(ComputerUse computerUse:computerUseList) {
			JSONObject jsonComputerUse = new JSONObject();
			jsonComputerUse.accumulate("cuId", computerUse.getCuId());
			jsonArray.put(jsonComputerUse);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询上机记录信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("userObj") UserInfo userObj,@ModelAttribute("computerObj") Computer computerObj,@ModelAttribute("chargingObj") Charging chargingObj,String startTime,String jiezhangFlag,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (startTime == null) startTime = "";
		if (jiezhangFlag == null) jiezhangFlag = "";
		List<ComputerUse> computerUseList = computerUseService.queryComputerUse(userObj, computerObj, chargingObj, startTime, jiezhangFlag, currentPage);
	    /*计算总的页数和总的记录数*/
	    computerUseService.queryTotalPageAndRecordNumber(userObj, computerObj, chargingObj, startTime, jiezhangFlag);
	    /*获取到总的页码数目*/
	    int totalPage = computerUseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = computerUseService.getRecordNumber();
	    request.setAttribute("computerUseList",  computerUseList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("computerObj", computerObj);
	    request.setAttribute("chargingObj", chargingObj);
	    request.setAttribute("startTime", startTime);
	    request.setAttribute("jiezhangFlag", jiezhangFlag);
	    List<Charging> chargingList = chargingService.queryAllCharging();
	    request.setAttribute("chargingList", chargingList);
	    List<Computer> computerList = computerService.queryAllComputer();
	    request.setAttribute("computerList", computerList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "ComputerUse/computerUse_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询上机记录信息*/
	@RequestMapping(value = { "userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("userObj") UserInfo userObj,@ModelAttribute("computerObj") Computer computerObj,@ModelAttribute("chargingObj") Charging chargingObj,String startTime,String jiezhangFlag,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (startTime == null) startTime = "";
		if (jiezhangFlag == null) jiezhangFlag = "";
		String user_name = (String) session.getAttribute("user_name");
		userObj = new UserInfo();
		userObj.setUser_name(user_name);
		
		List<ComputerUse> computerUseList = computerUseService.queryComputerUse(userObj, computerObj, chargingObj, startTime, jiezhangFlag, currentPage);
	    /*计算总的页数和总的记录数*/
	    computerUseService.queryTotalPageAndRecordNumber(userObj, computerObj, chargingObj, startTime, jiezhangFlag);
	    /*获取到总的页码数目*/
	    int totalPage = computerUseService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = computerUseService.getRecordNumber();
	    request.setAttribute("computerUseList",  computerUseList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("computerObj", computerObj);
	    request.setAttribute("chargingObj", chargingObj);
	    request.setAttribute("startTime", startTime);
	    request.setAttribute("jiezhangFlag", jiezhangFlag);
	    List<Charging> chargingList = chargingService.queryAllCharging();
	    request.setAttribute("chargingList", chargingList);
	    List<Computer> computerList = computerService.queryAllComputer();
	    request.setAttribute("computerList", computerList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "ComputerUse/computerUse_userFrontquery_result"; 
	}
	

     /*前台查询ComputerUse信息*/
	@RequestMapping(value="/{cuId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer cuId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键cuId获取ComputerUse对象*/
        ComputerUse computerUse = computerUseService.getComputerUse(cuId);

        List<Charging> chargingList = chargingService.queryAllCharging();
        request.setAttribute("chargingList", chargingList);
        List<Computer> computerList = computerService.queryAllComputer();
        request.setAttribute("computerList", computerList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("computerUse",  computerUse);
        return "ComputerUse/computerUse_frontshow";
	}

	/*ajax方式显示上机记录修改jsp视图页*/
	@RequestMapping(value="/{cuId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer cuId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键cuId获取ComputerUse对象*/
        ComputerUse computerUse = computerUseService.getComputerUse(cuId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonComputerUse = computerUse.getJsonObject();
		out.println(jsonComputerUse.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新上机记录信息*/
	@RequestMapping(value = "/{cuId}/update", method = RequestMethod.POST)
	public void update(@Validated ComputerUse computerUse, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			String computerNo = computerUse.getComputerObj().getComputerNo();
			Computer computer = computerService.getComputer(computerNo);
			computer.setComputerState("空闲中");
			computerService.updateComputer(computer);
			
			computerUseService.updateComputerUse(computerUse);
			
			message = "上机记录更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "上机记录更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除上机记录信息*/
	@RequestMapping(value="/{cuId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer cuId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  computerUseService.deleteComputerUse(cuId);
	            request.setAttribute("message", "上机记录删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "上机记录删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条上机记录记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String cuIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = computerUseService.deleteComputerUses(cuIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出上机记录信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("userObj") UserInfo userObj,@ModelAttribute("computerObj") Computer computerObj,@ModelAttribute("chargingObj") Charging chargingObj,String startTime,String jiezhangFlag, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(startTime == null) startTime = "";
        if(jiezhangFlag == null) jiezhangFlag = "";
        List<ComputerUse> computerUseList = computerUseService.queryComputerUse(userObj,computerObj,chargingObj,startTime,jiezhangFlag);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "ComputerUse信息记录"; 
        String[] headers = { "记录id","上机用户","使用电脑","计费方式","上机时间","下机时间","是否结账","结账金额"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<computerUseList.size();i++) {
        	ComputerUse computerUse = computerUseList.get(i); 
        	dataset.add(new String[]{computerUse.getCuId() + "",computerUse.getUserObj().getName(),computerUse.getComputerObj().getComputerName(),computerUse.getChargingObj().getChargingName(),computerUse.getStartTime(),computerUse.getEndTime(),computerUse.getJiezhangFlag(),computerUse.getUseMoney() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"ComputerUse.xls");//filename是下载的xls的名，建议最好用英文 
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
