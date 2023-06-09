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
import com.chengxusheji.service.DishService;
import com.chengxusheji.po.Dish;
import com.chengxusheji.service.DishClassService;
import com.chengxusheji.po.DishClass;

//Dish管理控制层
@Controller
@RequestMapping("/Dish")
public class DishController extends BaseController {

    /*业务层对象*/
    @Resource DishService dishService;

    @Resource DishClassService dishClassService;
	@InitBinder("dishClassObj")
	public void initBinderdishClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishClassObj.");
	}
	@InitBinder("dish")
	public void initBinderDish(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dish.");
	}
	/*跳转到添加Dish视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Dish());
		/*查询所有的DishClass信息*/
		List<DishClass> dishClassList = dishClassService.queryAllDishClass();
		request.setAttribute("dishClassList", dishClassList);
		return "Dish_add";
	}

	/*客户端ajax方式提交添加菜品信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Dish dish, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(dishService.getDish(dish.getDishNo()) != null) {
			message = "菜品编号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			dish.setDishPhoto(this.handlePhotoUpload(request, "dishPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        dishService.addDish(dish);
        message = "菜品添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询菜品信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String dishNo,@ModelAttribute("dishClassObj") DishClass dishClassObj,String dishName,String tuijianFlag,String upState,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (dishNo == null) dishNo = "";
		if (dishName == null) dishName = "";
		if (tuijianFlag == null) tuijianFlag = "";
		if (upState == null) upState = "";
		if (addTime == null) addTime = "";
		if(rows != 0)dishService.setRows(rows);
		List<Dish> dishList = dishService.queryDish(dishNo, dishClassObj, dishName, tuijianFlag, upState, addTime, page);
	    /*计算总的页数和总的记录数*/
	    dishService.queryTotalPageAndRecordNumber(dishNo, dishClassObj, dishName, tuijianFlag, upState, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = dishService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Dish dish:dishList) {
			JSONObject jsonDish = dish.getJsonObject();
			jsonArray.put(jsonDish);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询菜品信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Dish> dishList = dishService.queryAllDish();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Dish dish:dishList) {
			JSONObject jsonDish = new JSONObject();
			jsonDish.accumulate("dishNo", dish.getDishNo());
			jsonDish.accumulate("dishName", dish.getDishName());
			jsonArray.put(jsonDish);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询菜品信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String dishNo,@ModelAttribute("dishClassObj") DishClass dishClassObj,String dishName,String tuijianFlag,String upState,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (dishNo == null) dishNo = "";
		if (dishName == null) dishName = "";
		if (tuijianFlag == null) tuijianFlag = "";
		if (upState == null) upState = "";
		if (addTime == null) addTime = "";
		upState = "上架中";
		List<Dish> dishList = dishService.queryDish(dishNo, dishClassObj, dishName, tuijianFlag, upState, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    dishService.queryTotalPageAndRecordNumber(dishNo, dishClassObj, dishName, tuijianFlag, upState, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = dishService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishService.getRecordNumber();
	    request.setAttribute("dishList",  dishList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("dishNo", dishNo);
	    request.setAttribute("dishClassObj", dishClassObj);
	    request.setAttribute("dishName", dishName);
	    request.setAttribute("tuijianFlag", tuijianFlag);
	    request.setAttribute("upState", upState);
	    request.setAttribute("addTime", addTime);
	    List<DishClass> dishClassList = dishClassService.queryAllDishClass();
	    request.setAttribute("dishClassList", dishClassList);
		return "Dish/dish_frontquery_result"; 
	}

     /*前台查询Dish信息*/
	@RequestMapping(value="/{dishNo}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String dishNo,Model model,HttpServletRequest request) throws Exception {
		/*根据主键dishNo获取Dish对象*/
        Dish dish = dishService.getDish(dishNo);

        List<DishClass> dishClassList = dishClassService.queryAllDishClass();
        request.setAttribute("dishClassList", dishClassList);
        request.setAttribute("dish",  dish);
        return "Dish/dish_frontshow";
	}

	/*ajax方式显示菜品修改jsp视图页*/
	@RequestMapping(value="/{dishNo}/update",method=RequestMethod.GET)
	public void update(@PathVariable String dishNo,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键dishNo获取Dish对象*/
        Dish dish = dishService.getDish(dishNo);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDish = dish.getJsonObject();
		out.println(jsonDish.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新菜品信息*/
	@RequestMapping(value = "/{dishNo}/update", method = RequestMethod.POST)
	public void update(@Validated Dish dish, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String dishPhotoFileName = this.handlePhotoUpload(request, "dishPhotoFile");
		if(!dishPhotoFileName.equals("upload/NoImage.jpg"))dish.setDishPhoto(dishPhotoFileName); 


		try {
			dishService.updateDish(dish);
			message = "菜品更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "菜品更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除菜品信息*/
	@RequestMapping(value="/{dishNo}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String dishNo,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  dishService.deleteDish(dishNo);
	            request.setAttribute("message", "菜品删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "菜品删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条菜品记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String dishNos,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = dishService.deleteDishs(dishNos);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出菜品信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String dishNo,@ModelAttribute("dishClassObj") DishClass dishClassObj,String dishName,String tuijianFlag,String upState,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(dishNo == null) dishNo = "";
        if(dishName == null) dishName = "";
        if(tuijianFlag == null) tuijianFlag = "";
        if(upState == null) upState = "";
        if(addTime == null) addTime = "";
        List<Dish> dishList = dishService.queryDish(dishNo,dishClassObj,dishName,tuijianFlag,upState,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Dish信息记录"; 
        String[] headers = { "菜品编号","菜品类别","菜品名称","菜品图片","菜品单价","是否推荐","上架状态","浏览量","添加时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dishList.size();i++) {
        	Dish dish = dishList.get(i); 
        	dataset.add(new String[]{dish.getDishNo(),dish.getDishClassObj().getDishClassName(),dish.getDishName(),dish.getDishPhoto(),dish.getDishPrice() + "",dish.getTuijianFlag(),dish.getUpState(),dish.getViewNum() + "",dish.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Dish.xls");//filename是下载的xls的名，建议最好用英文 
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
