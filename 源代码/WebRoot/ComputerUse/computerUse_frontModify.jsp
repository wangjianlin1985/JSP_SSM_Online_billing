<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.ComputerUse" %>
<%@ page import="com.chengxusheji.po.Charging" %>
<%@ page import="com.chengxusheji.po.Computer" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的chargingObj信息
    List<Charging> chargingList = (List<Charging>)request.getAttribute("chargingList");
    //获取所有的computerObj信息
    List<Computer> computerList = (List<Computer>)request.getAttribute("computerList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    ComputerUse computerUse = (ComputerUse)request.getAttribute("computerUse");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改上机记录信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">上机记录信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="computerUseEditForm" id="computerUseEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="computerUse_cuId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="computerUse_cuId_edit" name="computerUse.cuId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="computerUse_userObj_user_name_edit" class="col-md-3 text-right">上机用户:</label>
		  	 <div class="col-md-9">
			    <select id="computerUse_userObj_user_name_edit" name="computerUse.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computerUse_computerObj_computerNo_edit" class="col-md-3 text-right">使用电脑:</label>
		  	 <div class="col-md-9">
			    <select id="computerUse_computerObj_computerNo_edit" name="computerUse.computerObj.computerNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computerUse_chargingObj_chargingId_edit" class="col-md-3 text-right">计费方式:</label>
		  	 <div class="col-md-9">
			    <select id="computerUse_chargingObj_chargingId_edit" name="computerUse.chargingObj.chargingId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computerUse_startTime_edit" class="col-md-3 text-right">上机时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date computerUse_startTime_edit col-md-12" data-link-field="computerUse_startTime_edit">
                    <input class="form-control" id="computerUse_startTime_edit" name="computerUse.startTime" size="16" type="text" value="" placeholder="请选择上机时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computerUse_endTime_edit" class="col-md-3 text-right">下机时间:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="computerUse_endTime_edit" name="computerUse.endTime" class="form-control" placeholder="请输入下机时间">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computerUse_jiezhangFlag_edit" class="col-md-3 text-right">是否结账:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="computerUse_jiezhangFlag_edit" name="computerUse.jiezhangFlag" class="form-control" placeholder="请输入是否结账">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computerUse_useMoney_edit" class="col-md-3 text-right">结账金额:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="computerUse_useMoney_edit" name="computerUse.useMoney" class="form-control" placeholder="请输入结账金额">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computerUse_memo_edit" class="col-md-3 text-right">备注信息:</label>
		  	 <div class="col-md-9">
			    <textarea id="computerUse_memo_edit" name="computerUse.memo" rows="8" class="form-control" placeholder="请输入备注信息"></textarea>
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxComputerUseModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#computerUseEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改上机记录界面并初始化数据*/
function computerUseEdit(cuId) {
	$.ajax({
		url :  basePath + "ComputerUse/" + cuId + "/update",
		type : "get",
		dataType: "json",
		success : function (computerUse, response, status) {
			if (computerUse) {
				$("#computerUse_cuId_edit").val(computerUse.cuId);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#computerUse_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#computerUse_userObj_user_name_edit").html(html);
		        		$("#computerUse_userObj_user_name_edit").val(computerUse.userObjPri);
					}
				});
				$.ajax({
					url: basePath + "Computer/listAll",
					type: "get",
					success: function(computers,response,status) { 
						$("#computerUse_computerObj_computerNo_edit").empty();
						var html="";
		        		$(computers).each(function(i,computer){
		        			html += "<option value='" + computer.computerNo + "'>" + computer.computerName + "</option>";
		        		});
		        		$("#computerUse_computerObj_computerNo_edit").html(html);
		        		$("#computerUse_computerObj_computerNo_edit").val(computerUse.computerObjPri);
					}
				});
				$.ajax({
					url: basePath + "Charging/listAll",
					type: "get",
					success: function(chargings,response,status) { 
						$("#computerUse_chargingObj_chargingId_edit").empty();
						var html="";
		        		$(chargings).each(function(i,charging){
		        			html += "<option value='" + charging.chargingId + "'>" + charging.chargingName + "</option>";
		        		});
		        		$("#computerUse_chargingObj_chargingId_edit").html(html);
		        		$("#computerUse_chargingObj_chargingId_edit").val(computerUse.chargingObjPri);
					}
				});
				$("#computerUse_startTime_edit").val(computerUse.startTime);
				$("#computerUse_endTime_edit").val(computerUse.endTime);
				$("#computerUse_jiezhangFlag_edit").val(computerUse.jiezhangFlag);
				$("#computerUse_useMoney_edit").val(computerUse.useMoney);
				$("#computerUse_memo_edit").val(computerUse.memo);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交上机记录信息表单给服务器端修改*/
function ajaxComputerUseModify() {
	$.ajax({
		url :  basePath + "ComputerUse/" + $("#computerUse_cuId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#computerUseEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#computerUseQueryForm").submit();
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
    /*上机时间组件*/
    $('.computerUse_startTime_edit').datetimepicker({
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
    computerUseEdit("<%=request.getParameter("cuId")%>");
 })
 </script> 
</body>
</html>

