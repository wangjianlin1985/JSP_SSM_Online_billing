<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.CallInfo" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    CallInfo callInfo = (CallInfo)request.getAttribute("callInfo");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改呼叫网管信息</TITLE>
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
  		<li class="active">呼叫网管信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="callInfoEditForm" id="callInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="callInfo_callId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="callInfo_callId_edit" name="callInfo.callId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="callInfo_userObj_user_name_edit" class="col-md-3 text-right">呼叫会员:</label>
		  	 <div class="col-md-9">
			    <select id="callInfo_userObj_user_name_edit" name="callInfo.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="callInfo_callTime_edit" class="col-md-3 text-right">呼叫时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date callInfo_callTime_edit col-md-12" data-link-field="callInfo_callTime_edit">
                    <input class="form-control" id="callInfo_callTime_edit" name="callInfo.callTime" size="16" type="text" value="" placeholder="请选择呼叫时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="callInfo_handFlag_edit" class="col-md-3 text-right">处理状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="callInfo_handFlag_edit" name="callInfo.handFlag" class="form-control" placeholder="请输入处理状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="callInfo_handMemo_edit" class="col-md-3 text-right">处理备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="callInfo_handMemo_edit" name="callInfo.handMemo" rows="8" class="form-control" placeholder="请输入处理备注"></textarea>
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxCallInfoModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#callInfoEditForm .form-group {margin-bottom:5px;}  </style>
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
/*弹出修改呼叫网管界面并初始化数据*/
function callInfoEdit(callId) {
	$.ajax({
		url :  basePath + "CallInfo/" + callId + "/update",
		type : "get",
		dataType: "json",
		success : function (callInfo, response, status) {
			if (callInfo) {
				$("#callInfo_callId_edit").val(callInfo.callId);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#callInfo_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#callInfo_userObj_user_name_edit").html(html);
		        		$("#callInfo_userObj_user_name_edit").val(callInfo.userObjPri);
					}
				});
				$("#callInfo_callTime_edit").val(callInfo.callTime);
				$("#callInfo_handFlag_edit").val(callInfo.handFlag);
				$("#callInfo_handMemo_edit").val(callInfo.handMemo);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交呼叫网管信息表单给服务器端修改*/
function ajaxCallInfoModify() {
	$.ajax({
		url :  basePath + "CallInfo/" + $("#callInfo_callId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#callInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#callInfoQueryForm").submit();
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
    /*呼叫时间组件*/
    $('.callInfo_callTime_edit').datetimepicker({
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
    callInfoEdit("<%=request.getParameter("callId")%>");
 })
 </script> 
</body>
</html>

