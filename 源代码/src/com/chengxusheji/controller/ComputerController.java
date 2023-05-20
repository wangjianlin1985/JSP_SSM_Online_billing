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
import com.chengxusheji.service.ComputerService;
import com.chengxusheji.po.Computer;

//Computer管理控制层
@Controller
@RequestMapping("/Computer")
public class ComputerController extends BaseController {

    /*业务层对象*/
    @Resource ComputerService computerService;

	@InitBinder("computer")
	public void initBinderComputer(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("computer.");
	}
	/*跳转到添加Computer视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Computer());
		return "Computer_add";
	}

	/*客户端ajax方式提交添加计算机信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Computer computer, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(computerService.getComputer(computer.getComputerNo()) != null) {
			message = "计算机编号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			computer.setComputerPhoto(this.handlePhotoUpload(request, "computerPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        computerService.addComputer(computer);
        message = "计算机添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询计算机信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String computerNo,String computerName,String area,String computerState,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (computerNo == null) computerNo = "";
		if (computerName == null) computerName = "";
		if (area == null) area = "";
		if (computerState == null) computerState = "";
		if (addTime == null) addTime = "";
		if(rows != 0)computerService.setRows(rows);
		List<Computer> computerList = computerService.queryComputer(computerNo, computerName, area, computerState, addTime, page);
	    /*计算总的页数和总的记录数*/
	    computerService.queryTotalPageAndRecordNumber(computerNo, computerName, area, computerState, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = computerService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = computerService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Computer computer:computerList) {
			JSONObject jsonComputer = computer.getJsonObject();
			jsonArray.put(jsonComputer);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询计算机信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Computer> computerList = computerService.queryAllComputer();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Computer computer:computerList) {
			JSONObject jsonComputer = new JSONObject();
			jsonComputer.accumulate("computerNo", computer.getComputerNo());
			jsonComputer.accumulate("computerName", computer.getComputerName());
			jsonArray.put(jsonComputer);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}
	
	
	
	/*ajax方式按照查询条件分页查询计算机信息*/
	@RequestMapping(value = { "/listKxAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listKxAll(HttpServletResponse response) throws Exception {
		List<Computer> computerList = computerService.queryKxAllComputer();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Computer computer:computerList) {
			JSONObject jsonComputer = new JSONObject();
			jsonComputer.accumulate("computerNo", computer.getComputerNo());
			jsonComputer.accumulate("computerName", computer.getComputerName());
			jsonArray.put(jsonComputer);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}
	

	/*前台按照查询条件分页查询计算机信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String computerNo,String computerName,String area,String computerState,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (computerNo == null) computerNo = "";
		if (computerName == null) computerName = "";
		if (area == null) area = "";
		if (computerState == null) computerState = "";
		if (addTime == null) addTime = "";
		List<Computer> computerList = computerService.queryComputer(computerNo, computerName, area, computerState, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    computerService.queryTotalPageAndRecordNumber(computerNo, computerName, area, computerState, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = computerService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = computerService.getRecordNumber();
	    request.setAttribute("computerList",  computerList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("computerNo", computerNo);
	    request.setAttribute("computerName", computerName);
	    request.setAttribute("area", area);
	    request.setAttribute("computerState", computerState);
	    request.setAttribute("addTime", addTime);
		return "Computer/computer_frontquery_result"; 
	}

     /*前台查询Computer信息*/
	@RequestMapping(value="/{computerNo}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String computerNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键computerNo获取Computer对象*/
        Computer computer = computerService.getComputer(computerNo);

        request.setAttribute("computer",  computer);
        return "Computer/computer_frontshow";
	}

	/*ajax方式显示计算机修改jsp视图页*/
	@RequestMapping(value="/{computerNo}/update",method=RequestMethod.GET)
	public void update(@PathVariable String computerNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键computerNo获取Computer对象*/
        Computer computer = computerService.getComputer(computerNo);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonComputer = computer.getJsonObject();
		out.println(jsonComputer.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新计算机信息*/
	@RequestMapping(value = "/{computerNo}/update", method = RequestMethod.POST)
	public void update(@Validated Computer computer, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String computerPhotoFileName = this.handlePhotoUpload(request, "computerPhotoFile");
		if(!computerPhotoFileName.equals("upload/NoImage.jpg"))computer.setComputerPhoto(computerPhotoFileName); 


		try {
			computerService.updateComputer(computer);
			message = "计算机更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "计算机更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除计算机信息*/
	@RequestMapping(value="/{computerNo}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String computerNo,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  computerService.deleteComputer(computerNo);
	            request.setAttribute("message", "计算机删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "计算机删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条计算机记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String computerNos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = computerService.deleteComputers(computerNos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出计算机信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String computerNo,String computerName,String area,String computerState,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(computerNo == null) computerNo = "";
        if(computerName == null) computerName = "";
        if(area == null) area = "";
        if(computerState == null) computerState = "";
        if(addTime == null) addTime = "";
        List<Computer> computerList = computerService.queryComputer(computerNo,computerName,area,computerState,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Computer信息记录"; 
        String[] headers = { "计算机编号","计算机名称","所在区域","计算机照片","电脑状态","添加时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<computerList.size();i++) {
        	Computer computer = computerList.get(i); 
        	dataset.add(new String[]{computer.getComputerNo(),computer.getComputerName(),computer.getArea(),computer.getComputerPhoto(),computer.getComputerState(),computer.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Computer.xls");//filename是下载的xls的名，建议最好用英文 
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
