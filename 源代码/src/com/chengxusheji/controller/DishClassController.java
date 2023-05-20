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
import com.chengxusheji.service.DishClassService;
import com.chengxusheji.po.DishClass;

//DishClass管理控制层
@Controller
@RequestMapping("/DishClass")
public class DishClassController extends BaseController {

    /*业务层对象*/
    @Resource DishClassService dishClassService;

	@InitBinder("dishClass")
	public void initBinderDishClass(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishClass.");
	}
	/*跳转到添加DishClass视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new DishClass());
		return "DishClass_add";
	}

	/*客户端ajax方式提交添加菜品分类信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated DishClass dishClass, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        dishClassService.addDishClass(dishClass);
        message = "菜品分类添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询菜品分类信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)dishClassService.setRows(rows);
		List<DishClass> dishClassList = dishClassService.queryDishClass(page);
	    /*计算总的页数和总的记录数*/
	    dishClassService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = dishClassService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishClassService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(DishClass dishClass:dishClassList) {
			JSONObject jsonDishClass = dishClass.getJsonObject();
			jsonArray.put(jsonDishClass);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询菜品分类信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<DishClass> dishClassList = dishClassService.queryAllDishClass();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(DishClass dishClass:dishClassList) {
			JSONObject jsonDishClass = new JSONObject();
			jsonDishClass.accumulate("dishClassId", dishClass.getDishClassId());
			jsonDishClass.accumulate("dishClassName", dishClass.getDishClassName());
			jsonArray.put(jsonDishClass);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询菜品分类信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<DishClass> dishClassList = dishClassService.queryDishClass(currentPage);
	    /*计算总的页数和总的记录数*/
	    dishClassService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = dishClassService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishClassService.getRecordNumber();
	    request.setAttribute("dishClassList",  dishClassList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "DishClass/dishClass_frontquery_result"; 
	}

     /*前台查询DishClass信息*/
	@RequestMapping(value="/{dishClassId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer dishClassId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键dishClassId获取DishClass对象*/
        DishClass dishClass = dishClassService.getDishClass(dishClassId);

        request.setAttribute("dishClass",  dishClass);
        return "DishClass/dishClass_frontshow";
	}

	/*ajax方式显示菜品分类修改jsp视图页*/
	@RequestMapping(value="/{dishClassId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer dishClassId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键dishClassId获取DishClass对象*/
        DishClass dishClass = dishClassService.getDishClass(dishClassId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDishClass = dishClass.getJsonObject();
		out.println(jsonDishClass.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新菜品分类信息*/
	@RequestMapping(value = "/{dishClassId}/update", method = RequestMethod.POST)
	public void update(@Validated DishClass dishClass, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			dishClassService.updateDishClass(dishClass);
			message = "菜品分类更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "菜品分类更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除菜品分类信息*/
	@RequestMapping(value="/{dishClassId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer dishClassId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  dishClassService.deleteDishClass(dishClassId);
	            request.setAttribute("message", "菜品分类删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "菜品分类删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条菜品分类记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String dishClassIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = dishClassService.deleteDishClasss(dishClassIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出菜品分类信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<DishClass> dishClassList = dishClassService.queryDishClass();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "DishClass信息记录"; 
        String[] headers = { "菜品分类id","菜品分类名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dishClassList.size();i++) {
        	DishClass dishClass = dishClassList.get(i); 
        	dataset.add(new String[]{dishClass.getDishClassId() + "",dishClass.getDishClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"DishClass.xls");//filename是下载的xls的名，建议最好用英文 
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
