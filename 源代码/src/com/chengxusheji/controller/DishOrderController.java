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
import com.chengxusheji.service.DishOrderService;
import com.chengxusheji.po.DishOrder;
import com.chengxusheji.service.DishService;
import com.chengxusheji.po.Dish;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;

//DishOrder管理控制层
@Controller
@RequestMapping("/DishOrder")
public class DishOrderController extends BaseController {

    /*业务层对象*/
    @Resource DishOrderService dishOrderService;

    @Resource DishService dishService;
    @Resource UserInfoService userInfoService;
	@InitBinder("dishObj")
	public void initBinderdishObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("dishOrder")
	public void initBinderDishOrder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dishOrder.");
	}
	/*跳转到添加DishOrder视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new DishOrder());
		/*查询所有的Dish信息*/
		List<Dish> dishList = dishService.queryAllDish();
		request.setAttribute("dishList", dishList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "DishOrder_add";
	}

	/*客户端ajax方式提交添加点餐信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated DishOrder dishOrder, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        dishOrderService.addDishOrder(dishOrder);
        message = "点餐添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加点餐信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(DishOrder dishOrder, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		
		String user_name = (String) session.getAttribute("user_name");
		if(user_name == null) {
			message = "请先登录网站！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		String dishNo = dishOrder.getDishObj().getDishNo();
		float dishPrice = dishService.getDish(dishNo).getDishPrice(); //获取菜单单价
		float dishMoney = dishPrice * dishOrder.getDishNum(); //计算订单总价
		
		UserInfo userInfo = userInfoService.getUserInfo(user_name);
		float userMoney = userInfo.getUserMoney();
		if(userMoney < dishMoney) {
			message = "你的余额不足，请先去充值！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		dishOrder.setDishMoney(dishMoney);
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(user_name);
		dishOrder.setUserObj(userObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dishOrder.setOrderTime(sdf.format(new java.util.Date()));
		
		dishOrder.setOrderState("已付款"); 
        dishOrderService.addDishOrder(dishOrder);
        
        userInfo.setUserMoney(userMoney-dishMoney);
        userInfoService.updateUserInfo(userInfo);
        
        
        message = "点餐添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询点餐信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String orderState,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (orderTime == null) orderTime = "";
		if (orderState == null) orderState = "";
		if(rows != 0)dishOrderService.setRows(rows);
		List<DishOrder> dishOrderList = dishOrderService.queryDishOrder(dishObj, userObj, orderTime, orderState, page);
	    /*计算总的页数和总的记录数*/
	    dishOrderService.queryTotalPageAndRecordNumber(dishObj, userObj, orderTime, orderState);
	    /*获取到总的页码数目*/
	    int totalPage = dishOrderService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishOrderService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(DishOrder dishOrder:dishOrderList) {
			JSONObject jsonDishOrder = dishOrder.getJsonObject();
			jsonArray.put(jsonDishOrder);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询点餐信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<DishOrder> dishOrderList = dishOrderService.queryAllDishOrder();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(DishOrder dishOrder:dishOrderList) {
			JSONObject jsonDishOrder = new JSONObject();
			jsonDishOrder.accumulate("orderId", dishOrder.getOrderId());
			jsonArray.put(jsonDishOrder);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询点餐信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String orderState,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (orderTime == null) orderTime = "";
		if (orderState == null) orderState = "";
		List<DishOrder> dishOrderList = dishOrderService.queryDishOrder(dishObj, userObj, orderTime, orderState, currentPage);
	    /*计算总的页数和总的记录数*/
	    dishOrderService.queryTotalPageAndRecordNumber(dishObj, userObj, orderTime, orderState);
	    /*获取到总的页码数目*/
	    int totalPage = dishOrderService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishOrderService.getRecordNumber();
	    request.setAttribute("dishOrderList",  dishOrderList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("dishObj", dishObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("orderTime", orderTime);
	    request.setAttribute("orderState", orderState);
	    List<Dish> dishList = dishService.queryAllDish();
	    request.setAttribute("dishList", dishList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "DishOrder/dishOrder_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询点餐信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String orderState,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (orderTime == null) orderTime = "";
		if (orderState == null) orderState = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<DishOrder> dishOrderList = dishOrderService.queryDishOrder(dishObj, userObj, orderTime, orderState, currentPage);
	    /*计算总的页数和总的记录数*/
	    dishOrderService.queryTotalPageAndRecordNumber(dishObj, userObj, orderTime, orderState);
	    /*获取到总的页码数目*/
	    int totalPage = dishOrderService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dishOrderService.getRecordNumber();
	    request.setAttribute("dishOrderList",  dishOrderList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("dishObj", dishObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("orderTime", orderTime);
	    request.setAttribute("orderState", orderState);
	    List<Dish> dishList = dishService.queryAllDish();
	    request.setAttribute("dishList", dishList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "DishOrder/dishOrder_userFrontquery_result"; 
	}
	

     /*前台查询DishOrder信息*/
	@RequestMapping(value="/{orderId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer orderId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键orderId获取DishOrder对象*/
        DishOrder dishOrder = dishOrderService.getDishOrder(orderId);

        List<Dish> dishList = dishService.queryAllDish();
        request.setAttribute("dishList", dishList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("dishOrder",  dishOrder);
        return "DishOrder/dishOrder_frontshow";
	}

	/*ajax方式显示点餐修改jsp视图页*/
	@RequestMapping(value="/{orderId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer orderId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键orderId获取DishOrder对象*/
        DishOrder dishOrder = dishOrderService.getDishOrder(orderId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDishOrder = dishOrder.getJsonObject();
		out.println(jsonDishOrder.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新点餐信息*/
	@RequestMapping(value = "/{orderId}/update", method = RequestMethod.POST)
	public void update(@Validated DishOrder dishOrder, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			dishOrderService.updateDishOrder(dishOrder);
			message = "点餐更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "点餐更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除点餐信息*/
	@RequestMapping(value="/{orderId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer orderId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  dishOrderService.deleteDishOrder(orderId);
	            request.setAttribute("message", "点餐删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "点餐删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条点餐记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String orderIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = dishOrderService.deleteDishOrders(orderIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出点餐信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("dishObj") Dish dishObj,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String orderState, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(orderTime == null) orderTime = "";
        if(orderState == null) orderState = "";
        List<DishOrder> dishOrderList = dishOrderService.queryDishOrder(dishObj,userObj,orderTime,orderState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "DishOrder信息记录"; 
        String[] headers = { "点餐id","菜品名称","订餐数量","菜品金额","订单备注","点餐用户","点餐时间","订单状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dishOrderList.size();i++) {
        	DishOrder dishOrder = dishOrderList.get(i); 
        	dataset.add(new String[]{dishOrder.getOrderId() + "",dishOrder.getDishObj().getDishName(),dishOrder.getDishNum() + "",dishOrder.getDishMoney() + "",dishOrder.getOrderMemo(),dishOrder.getUserObj().getName(),dishOrder.getOrderTime(),dishOrder.getOrderState()});
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
			response.setHeader("Content-disposition","attachment; filename="+"DishOrder.xls");//filename是下载的xls的名，建议最好用英文 
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
