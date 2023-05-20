<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.ComputerUse" %>
<%@ page import="com.chengxusheji.po.Charging" %>
<%@ page import="com.chengxusheji.po.Computer" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<ComputerUse> computerUseList = (List<ComputerUse>)request.getAttribute("computerUseList");
    //获取所有的chargingObj信息
    List<Charging> chargingList = (List<Charging>)request.getAttribute("chargingList");
    //获取所有的computerObj信息
    List<Computer> computerList = (List<Computer>)request.getAttribute("computerList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    UserInfo userObj = (UserInfo)request.getAttribute("userObj");
    Computer computerObj = (Computer)request.getAttribute("computerObj");
    Charging chargingObj = (Charging)request.getAttribute("chargingObj");
    String startTime = (String)request.getAttribute("startTime"); //上机时间查询关键字
    String jiezhangFlag = (String)request.getAttribute("jiezhangFlag"); //是否结账查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>上机记录查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#computerUseListPanel" aria-controls="computerUseListPanel" role="tab" data-toggle="tab">上机记录列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>ComputerUse/computerUse_frontAdd.jsp" style="display:none;">添加上机记录</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="computerUseListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>记录id</td><td>使用电脑</td><td>计费方式</td><td>上机时间</td><td>下机时间</td><td>是否结账</td><td>结账金额</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<computerUseList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		ComputerUse computerUse = computerUseList.get(i); //获取到上机记录对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=computerUse.getCuId() %></td>
 											<td><%=computerUse.getComputerObj().getComputerName() %></td>
 											<td><%=computerUse.getChargingObj().getChargingName() %></td>
 											<td><%=computerUse.getStartTime() %></td>
 											<td><%=computerUse.getEndTime() %></td>
 											<td><%=computerUse.getJiezhangFlag() %></td>
 											<td><%=computerUse.getUseMoney() %></td>
 											<td>
 												<a href="<%=basePath  %>ComputerUse/<%=computerUse.getCuId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="computerUseEdit('<%=computerUse.getCuId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="computerUseDelete('<%=computerUse.getCuId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

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
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>上机记录查询</h1>
		</div>
		<form name="computerUseQueryForm" id="computerUseQueryForm" action="<%=basePath %>ComputerUse/userFrontlist" class="mar_t15" method="post">
            <div class="form-group" style="display:none;">
            	<label for="userObj_user_name">上机用户：</label>
                <select id="userObj_user_name" name="userObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(userObj!=null && userObj.getUser_name()!=null && userObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="computerObj_computerNo">使用电脑：</label>
                <select id="computerObj_computerNo" name="computerObj.computerNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Computer computerTemp:computerList) {
	 					String selected = "";
 					if(computerObj!=null && computerObj.getComputerNo()!=null && computerObj.getComputerNo().equals(computerTemp.getComputerNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=computerTemp.getComputerNo() %>" <%=selected %>><%=computerTemp.getComputerName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="chargingObj_chargingId">计费方式：</label>
                <select id="chargingObj_chargingId" name="chargingObj.chargingId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Charging chargingTemp:chargingList) {
	 					String selected = "";
 					if(chargingObj!=null && chargingObj.getChargingId()!=null && chargingObj.getChargingId().intValue()==chargingTemp.getChargingId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=chargingTemp.getChargingId() %>" <%=selected %>><%=chargingTemp.getChargingName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="startTime">上机时间:</label>
				<input type="text" id="startTime" name="startTime" class="form-control"  placeholder="请选择上机时间" value="<%=startTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="jiezhangFlag">是否结账:</label>
				<input type="text" id="jiezhangFlag" name="jiezhangFlag" value="<%=jiezhangFlag %>" class="form-control" placeholder="请输入是否结账">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="computerUseEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;上机记录信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#computerUseEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxComputerUseModify();">提交</button>
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
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.computerUseQueryForm.currentPage.value = currentPage;
    document.computerUseQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.computerUseQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.computerUseQueryForm.currentPage.value = pageValue;
    documentcomputerUseQueryForm.submit();
}

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
				$('#computerUseEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除上机记录信息*/
function computerUseDelete(cuId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "ComputerUse/deletes",
			data : {
				cuIds : cuId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#computerUseQueryForm").submit();
					//location.href= basePath + "ComputerUse/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>

