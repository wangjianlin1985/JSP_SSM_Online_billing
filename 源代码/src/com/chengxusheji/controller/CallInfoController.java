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
import com.chengxusheji.service.CallInfoService;
import com.chengxusheji.po.CallInfo;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//CallInfo管理控制层
@Controller
@RequestMapping("/CallInfo")
public class CallInfoController extends BaseController {

    /*业务层对象*/
    @Resource CallInfoService callInfoService;

    @Resource UserInfoService userInfoService;
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("callInfo")
	public void initBinderCallInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("callInfo.");
	}
	/*跳转到添加CallInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new CallInfo());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "CallInfo_add";
	}

	/*客户端ajax方式提交添加呼叫网管信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated CallInfo callInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        callInfoService.addCallInfo(callInfo);
        message = "呼叫网管添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*客户端ajax方式提交添加呼叫网管信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(CallInfo callInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		callInfo.setCallTime(sdf.format(new java.util.Date()));
		
		callInfo.setHandFlag("待处理");
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		callInfo.setUserObj(userObj);
		
		 
        callInfoService.addCallInfo(callInfo);
        message = "呼叫网管添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询呼叫网管信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("userObj") UserInfo userObj,String callTime,String handFlag,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (callTime == null) callTime = "";
		if (handFlag == null) handFlag = "";
		if(rows != 0)callInfoService.setRows(rows);
		List<CallInfo> callInfoList = callInfoService.queryCallInfo(userObj, callTime, handFlag, page);
	    /*计算总的页数和总的记录数*/
	    callInfoService.queryTotalPageAndRecordNumber(userObj, callTime, handFlag);
	    /*获取到总的页码数目*/
	    int totalPage = callInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = callInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(CallInfo callInfo:callInfoList) {
			JSONObject jsonCallInfo = callInfo.getJsonObject();
			jsonArray.put(jsonCallInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询呼叫网管信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<CallInfo> callInfoList = callInfoService.queryAllCallInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(CallInfo callInfo:callInfoList) {
			JSONObject jsonCallInfo = new JSONObject();
			jsonCallInfo.accumulate("callId", callInfo.getCallId());
			jsonArray.put(jsonCallInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询呼叫网管信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("userObj") UserInfo userObj,String callTime,String handFlag,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (callTime == null) callTime = "";
		if (handFlag == null) handFlag = "";
		List<CallInfo> callInfoList = callInfoService.queryCallInfo(userObj, callTime, handFlag, currentPage);
	    /*计算总的页数和总的记录数*/
	    callInfoService.queryTotalPageAndRecordNumber(userObj, callTime, handFlag);
	    /*获取到总的页码数目*/
	    int totalPage = callInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = callInfoService.getRecordNumber();
	    request.setAttribute("callInfoList",  callInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("callTime", callTime);
	    request.setAttribute("handFlag", handFlag);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "CallInfo/callInfo_frontquery_result"; 
	}

	/*前台按照查询条件分页查询呼叫网管信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("userObj") UserInfo userObj,String callTime,String handFlag,Integer currentPage, Model model, HttpServletRequest request, HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (callTime == null) callTime = "";
		if (handFlag == null) handFlag = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<CallInfo> callInfoList = callInfoService.queryCallInfo(userObj, callTime, handFlag, currentPage);
	    /*计算总的页数和总的记录数*/
	    callInfoService.queryTotalPageAndRecordNumber(userObj, callTime, handFlag);
	    /*获取到总的页码数目*/
	    int totalPage = callInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = callInfoService.getRecordNumber();
	    request.setAttribute("callInfoList",  callInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("callTime", callTime);
	    request.setAttribute("handFlag", handFlag);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "CallInfo/callInfo_userFrontquery_result"; 
	}
	
     /*前台查询CallInfo信息*/
	@RequestMapping(value="/{callId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer callId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键callId获取CallInfo对象*/
        CallInfo callInfo = callInfoService.getCallInfo(callId);

        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("callInfo",  callInfo);
        return "CallInfo/callInfo_frontshow";
	}

	/*ajax方式显示呼叫网管修改jsp视图页*/
	@RequestMapping(value="/{callId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer callId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键callId获取CallInfo对象*/
        CallInfo callInfo = callInfoService.getCallInfo(callId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonCallInfo = callInfo.getJsonObject();
		out.println(jsonCallInfo.toString());
		out.flush();
		out.close();
	}
	
	
	/*ajax方式查询最大的呼叫网管id*/
	@RequestMapping(value="/getMaxCallId",method=RequestMethod.GET)
	public void getMaxCallId(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键callId获取CallInfo对象*/
        int maxCallId = callInfoService.getMaxCallId();

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonData = new JSONObject();
		jsonData.put("maxCallId", maxCallId);
		out.println(jsonData.toString());
		out.flush();
		out.close();
	}
	
	

	/*ajax方式更新呼叫网管信息*/
	@RequestMapping(value = "/{callId}/update", method = RequestMethod.POST)
	public void update(@Validated CallInfo callInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			callInfoService.updateCallInfo(callInfo);
			message = "呼叫网管更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "呼叫网管更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除呼叫网管信息*/
	@RequestMapping(value="/{callId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer callId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  callInfoService.deleteCallInfo(callId);
	            request.setAttribute("message", "呼叫网管删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "呼叫网管删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条呼叫网管记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String callIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = callInfoService.deleteCallInfos(callIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出呼叫网管信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("userObj") UserInfo userObj,String callTime,String handFlag, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(callTime == null) callTime = "";
        if(handFlag == null) handFlag = "";
        List<CallInfo> callInfoList = callInfoService.queryCallInfo(userObj,callTime,handFlag);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "CallInfo信息记录"; 
        String[] headers = { "记录id","呼叫会员","呼叫时间","处理状态","处理备注"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<callInfoList.size();i++) {
        	CallInfo callInfo = callInfoList.get(i); 
        	dataset.add(new String[]{callInfo.getCallId() + "",callInfo.getUserObj().getName(),callInfo.getCallTime(),callInfo.getHandFlag(),callInfo.getHandMemo()});
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
			response.setHeader("Content-disposition","attachment; filename="+"CallInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
