<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>呼叫网管添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>CallInfo/frontlist">呼叫网管列表</a></li>
			    	<li role="presentation" class="active"><a href="#callInfoAdd" aria-controls="callInfoAdd" role="tab" data-toggle="tab">添加呼叫网管</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="callInfoList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="callInfoAdd"> 
				      	<form class="form-horizontal" name="callInfoAddForm" id="callInfoAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="callInfo_userObj_user_name" class="col-md-2 text-right">呼叫会员:</label>
						  	 <div class="col-md-8">
							    <select id="callInfo_userObj_user_name" name="callInfo.userObj.user_name" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="callInfo_callTimeDiv" class="col-md-2 text-right">呼叫时间:</label>
						  	 <div class="col-md-8">
				                <div id="callInfo_callTimeDiv" class="input-group date callInfo_callTime col-md-12" data-link-field="callInfo_callTime">
				                    <input class="form-control" id="callInfo_callTime" name="callInfo.callTime" size="16" type="text" value="" placeholder="请选择呼叫时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="callInfo_handFlag" class="col-md-2 text-right">处理状态:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="callInfo_handFlag" name="callInfo.handFlag" class="form-control" placeholder="请输入处理状态">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="callInfo_handMemo" class="col-md-2 text-right">处理备注:</label>
						  	 <div class="col-md-8">
							    <textarea id="callInfo_handMemo" name="callInfo.handMemo" rows="8" class="form-control" placeholder="请输入处理备注"></textarea>
							 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxCallInfoAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#callInfoAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
		</div>
	</div> 
</div>

<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加呼叫网管信息
	function ajaxCallInfoAdd() { 
		//提交之前先验证表单
		$("#callInfoAddForm").data('bootstrapValidator').validate();
		if(!$("#callInfoAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "CallInfo/add",
			dataType : "json" , 
			data: new FormData($("#callInfoAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#callInfoAddForm").find("input").val("");
					$("#callInfoAddForm").find("textarea").val("");
				} else {
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
	//验证呼叫网管添加表单字段
	$('#callInfoAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"callInfo.callTime": {
				validators: {
					notEmpty: {
						message: "呼叫时间不能为空",
					}
				}
			},
			"callInfo.handFlag": {
				validators: {
					notEmpty: {
						message: "处理状态不能为空",
					}
				}
			},
		}
	}); 
	//初始化呼叫会员下拉框值 
	$.ajax({
		url: basePath + "UserInfo/listAll",
		type: "get",
		success: function(userInfos,response,status) { 
			$("#callInfo_userObj_user_name").empty();
			var html="";
    		$(userInfos).each(function(i,userInfo){
    			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
    		});
    		$("#callInfo_userObj_user_name").html(html);
    	}
	});
	//呼叫时间组件
	$('#callInfo_callTimeDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#callInfoAddForm').data('bootstrapValidator').updateStatus('callInfo.callTime', 'NOT_VALIDATED',null).validateField('callInfo.callTime');
	});
})
</script>
</body>
</html>
