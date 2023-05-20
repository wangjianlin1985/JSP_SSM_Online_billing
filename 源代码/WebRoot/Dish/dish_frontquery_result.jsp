<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Dish" %>
<%@ page import="com.chengxusheji.po.DishClass" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Dish> dishList = (List<Dish>)request.getAttribute("dishList");
    //获取所有的dishClassObj信息
    List<DishClass> dishClassList = (List<DishClass>)request.getAttribute("dishClassList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String dishNo = (String)request.getAttribute("dishNo"); //菜品编号查询关键字
    DishClass dishClassObj = (DishClass)request.getAttribute("dishClassObj");
    String dishName = (String)request.getAttribute("dishName"); //菜品名称查询关键字
    String tuijianFlag = (String)request.getAttribute("tuijianFlag"); //是否推荐查询关键字
    String upState = (String)request.getAttribute("upState"); //上架状态查询关键字
    String addTime = (String)request.getAttribute("addTime"); //添加时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>菜品查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Dish/frontlist">菜品信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Dish/dish_frontAdd.jsp" style="display:none;">添加菜品</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<dishList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Dish dish = dishList.get(i); //获取到菜品对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Dish/<%=dish.getDishNo() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=dish.getDishPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		菜品编号:<%=dish.getDishNo() %>
			     	</div>
			     	<div class="field">
	            		菜品类别:<%=dish.getDishClassObj().getDishClassName() %>
			     	</div>
			     	<div class="field">
	            		菜品名称:<%=dish.getDishName() %>
			     	</div>
			     	<div class="field">
	            		菜品单价:<%=dish.getDishPrice() %>
			     	</div>
			     	<div class="field">
	            		是否推荐:<%=dish.getTuijianFlag() %>
			     	</div>
			     	<div class="field">
	            		上架状态:<%=dish.getUpState() %>
			     	</div>
			     	<div class="field">
	            		浏览量:<%=dish.getViewNum() %>
			     	</div>
			     	<div class="field">
	            		添加时间:<%=dish.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Dish/<%=dish.getDishNo() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="dishEdit('<%=dish.getDishNo() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="dishDelete('<%=dish.getDishNo() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>菜品查询</h1>
		</div>
		<form name="dishQueryForm" id="dishQueryForm" action="<%=basePath %>Dish/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="dishNo">菜品编号:</label>
				<input type="text" id="dishNo" name="dishNo" value="<%=dishNo %>" class="form-control" placeholder="请输入菜品编号">
			</div>
            <div class="form-group">
            	<label for="dishClassObj_dishClassId">菜品类别：</label>
                <select id="dishClassObj_dishClassId" name="dishClassObj.dishClassId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(DishClass dishClassTemp:dishClassList) {
	 					String selected = "";
 					if(dishClassObj!=null && dishClassObj.getDishClassId()!=null && dishClassObj.getDishClassId().intValue()==dishClassTemp.getDishClassId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=dishClassTemp.getDishClassId() %>" <%=selected %>><%=dishClassTemp.getDishClassName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="dishName">菜品名称:</label>
				<input type="text" id="dishName" name="dishName" value="<%=dishName %>" class="form-control" placeholder="请输入菜品名称">
			</div>
			<div class="form-group">
				<label for="tuijianFlag">是否推荐:</label>
				<input type="text" id="tuijianFlag" name="tuijianFlag" value="<%=tuijianFlag %>" class="form-control" placeholder="请输入是否推荐">
			</div>
			<div class="form-group">
				<label for="upState">上架状态:</label>
				<input type="text" id="upState" name="upState" value="<%=upState %>" class="form-control" placeholder="请输入上架状态">
			</div>
			<div class="form-group">
				<label for="addTime">添加时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择添加时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="dishEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;菜品信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="dishEditForm" id="dishEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="dish_dishNo_edit" class="col-md-3 text-right">菜品编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="dish_dishNo_edit" name="dish.dishNo" class="form-control" placeholder="请输入菜品编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="dish_dishClassObj_dishClassId_edit" class="col-md-3 text-right">菜品类别:</label>
		  	 <div class="col-md-9">
			    <select id="dish_dishClassObj_dishClassId_edit" name="dish.dishClassObj.dishClassId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_dishName_edit" class="col-md-3 text-right">菜品名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dish_dishName_edit" name="dish.dishName" class="form-control" placeholder="请输入菜品名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_dishPhoto_edit" class="col-md-3 text-right">菜品图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="dish_dishPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="dish_dishPhoto" name="dish.dishPhoto"/>
			    <input id="dishPhotoFile" name="dishPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_dishPrice_edit" class="col-md-3 text-right">菜品单价:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dish_dishPrice_edit" name="dish.dishPrice" class="form-control" placeholder="请输入菜品单价">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_tuijianFlag_edit" class="col-md-3 text-right">是否推荐:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dish_tuijianFlag_edit" name="dish.tuijianFlag" class="form-control" placeholder="请输入是否推荐">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_upState_edit" class="col-md-3 text-right">上架状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dish_upState_edit" name="dish.upState" class="form-control" placeholder="请输入上架状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_viewNum_edit" class="col-md-3 text-right">浏览量:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dish_viewNum_edit" name="dish.viewNum" class="form-control" placeholder="请输入浏览量">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_dishDesc_edit" class="col-md-3 text-right">菜品描述:</label>
		  	 <div class="col-md-9">
			 	<textarea name="dish.dishDesc" id="dish_dishDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dish_addTime_edit" class="col-md-3 text-right">添加时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date dish_addTime_edit col-md-12" data-link-field="dish_addTime_edit">
                    <input class="form-control" id="dish_addTime_edit" name="dish.addTime" size="16" type="text" value="" placeholder="请选择添加时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#dishEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxDishModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var dish_dishDesc_edit = UE.getEditor('dish_dishDesc_edit'); //菜品描述编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.dishQueryForm.currentPage.value = currentPage;
    document.dishQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.dishQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.dishQueryForm.currentPage.value = pageValue;
    documentdishQueryForm.submit();
}

/*弹出修改菜品界面并初始化数据*/
function dishEdit(dishNo) {
	$.ajax({
		url :  basePath + "Dish/" + dishNo + "/update",
		type : "get",
		dataType: "json",
		success : function (dish, response, status) {
			if (dish) {
				$("#dish_dishNo_edit").val(dish.dishNo);
				$.ajax({
					url: basePath + "DishClass/listAll",
					type: "get",
					success: function(dishClasss,response,status) { 
						$("#dish_dishClassObj_dishClassId_edit").empty();
						var html="";
		        		$(dishClasss).each(function(i,dishClass){
		        			html += "<option value='" + dishClass.dishClassId + "'>" + dishClass.dishClassName + "</option>";
		        		});
		        		$("#dish_dishClassObj_dishClassId_edit").html(html);
		        		$("#dish_dishClassObj_dishClassId_edit").val(dish.dishClassObjPri);
					}
				});
				$("#dish_dishName_edit").val(dish.dishName);
				$("#dish_dishPhoto").val(dish.dishPhoto);
				$("#dish_dishPhotoImg").attr("src", basePath +　dish.dishPhoto);
				$("#dish_dishPrice_edit").val(dish.dishPrice);
				$("#dish_tuijianFlag_edit").val(dish.tuijianFlag);
				$("#dish_upState_edit").val(dish.upState);
				$("#dish_viewNum_edit").val(dish.viewNum);
				dish_dishDesc_edit.setContent(dish.dishDesc, false);
				$("#dish_addTime_edit").val(dish.addTime);
				$('#dishEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除菜品信息*/
function dishDelete(dishNo) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Dish/deletes",
			data : {
				dishNos : dishNo,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#dishQueryForm").submit();
					//location.href= basePath + "Dish/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交菜品信息表单给服务器端修改*/
function ajaxDishModify() {
	$.ajax({
		url :  basePath + "Dish/" + $("#dish_dishNo_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#dishEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#dishQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*添加时间组件*/
    $('.dish_addTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

