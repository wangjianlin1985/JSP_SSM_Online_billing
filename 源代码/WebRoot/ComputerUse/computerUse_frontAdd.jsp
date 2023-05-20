<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Charging" %>
<%@ page import="com.chengxusheji.po.Computer" %>
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
<title>上机记录添加</title>
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
			    	<li role="presentation" ><a href="<%=basePath %>ComputerUse/frontlist">上机记录列表</a></li>
			    	<li role="presentation" class="active"><a href="#computerUseAdd" aria-controls="computerUseAdd" role="tab" data-toggle="tab">添加上机记录</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="computerUseList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="computerUseAdd"> 
				      	<form class="form-horizontal" name="computerUseAddForm" id="computerUseAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="computerUse_userObj_user_name" class="col-md-2 text-right">上机用户:</label>
						  	 <div class="col-md-8">
							    <select id="computerUse_userObj_user_name" name="computerUse.userObj.user_name" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="computerUse_computerObj_computerNo" class="col-md-2 text-right">使用电脑:</label>
						  	 <div class="col-md-8">
							    <select id="computerUse_computerObj_computerNo" name="computerUse.computerObj.computerNo" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="computerUse_chargingObj_chargingId" class="col-md-2 text-right">计费方式:</label>
						  	 <div class="col-md-8">
							    <select id="computerUse_chargingObj_chargingId" name="computerUse.chargingObj.chargingId" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="computerUse_startTimeDiv" class="col-md-2 text-right">上机时间:</label>
						  	 <div class="col-md-8">
				                <div id="computerUse_startTimeDiv" class="input-group date computerUse_startTime col-md-12" data-link-field="computerUse_startTime">
				                    <input class="form-control" id="computerUse_startTime" name="computerUse.startTime" size="16" type="text" value="" placeholder="请选择上机时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="computerUse_endTime" class="col-md-2 text-right">下机时间:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="computerUse_endTime" name="computerUse.endTime" class="form-control" placeholder="请输入下机时间">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="computerUse_jiezhangFlag" class="col-md-2 text-right">是否结账:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="computerUse_jiezhangFlag" name="computerUse.jiezhangFlag" class="form-control" placeholder="请输入是否结账">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="computerUse_useMoney" class="col-md-2 text-right">结账金额:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="computerUse_useMoney" name="computerUse.useMoney" class="form-control" placeholder="请输入结账金额">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="computerUse_memo" class="col-md-2 text-right">备注信息:</label>
						  	 <div class="col-md-8">
							    <textarea id="computerUse_memo" name="computerUse.memo" rows="8" class="form-control" placeholder="请输入备注信息"></textarea>
							 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxComputerUseAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#computerUseAddForm .form-group {margin:10px;}  </style>
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
	//提交添加上机记录信息
	function ajaxComputerUseAdd() { 
		//提交之前先验证表单
		$("#computerUseAddForm").data('bootstrapValidator').validate();
		if(!$("#computerUseAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "ComputerUse/add",
			dataType : "json" , 
			data: new FormData($("#computerUseAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#computerUseAddForm").find("input").val("");
					$("#computerUseAddForm").find("textarea").val("");
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
	//验证上机记录添加表单字段
	$('#computerUseAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"computerUse.startTime": {
				validators: {
					notEmpty: {
						message: "上机时间不能为空",
					}
				}
			},
			"computerUse.jiezhangFlag": {
				validators: {
					notEmpty: {
						message: "是否结账不能为空",
					}
				}
			},
			"computerUse.useMoney": {
				validators: {
					notEmpty: {
						message: "结账金额不能为空",
					},
					numeric: {
						message: "结账金额不正确"
					}
				}
			},
		}
	}); 
	//初始化上机用户下拉框值 
	$.ajax({
		url: basePath + "UserInfo/listAll",
		type: "get",
		success: function(userInfos,response,status) { 
			$("#computerUse_userObj_user_name").empty();
			var html="";
    		$(userInfos).each(function(i,userInfo){
    			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
    		});
    		$("#computerUse_userObj_user_name").html(html);
    	}
	});
	//初始化使用电脑下拉框值 
	$.ajax({
		url: basePath + "Computer/listAll",
		type: "get",
		success: function(computers,response,status) { 
			$("#computerUse_computerObj_computerNo").empty();
			var html="";
    		$(computers).each(function(i,computer){
    			html += "<option value='" + computer.computerNo + "'>" + computer.computerName + "</option>";
    		});
    		$("#computerUse_computerObj_computerNo").html(html);
    	}
	});
	//初始化计费方式下拉框值 
	$.ajax({
		url: basePath + "Charging/listAll",
		type: "get",
		success: function(chargings,response,status) { 
			$("#computerUse_chargingObj_chargingId").empty();
			var html="";
    		$(chargings).each(function(i,charging){
    			html += "<option value='" + charging.chargingId + "'>" + charging.chargingName + "</option>";
    		});
    		$("#computerUse_chargingObj_chargingId").html(html);
    	}
	});
	//上机时间组件
	$('#computerUse_startTimeDiv').datetimepicker({
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
		$('#computerUseAddForm').data('bootstrapValidator').updateStatus('computerUse.startTime', 'NOT_VALIDATED',null).validateField('computerUse.startTime');
	});
})
</script>
</body>
</html>
