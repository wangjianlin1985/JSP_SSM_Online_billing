<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Computer" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Computer> computerList = (List<Computer>)request.getAttribute("computerList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String computerNo = (String)request.getAttribute("computerNo"); //计算机编号查询关键字
    String computerName = (String)request.getAttribute("computerName"); //计算机名称查询关键字
    String area = (String)request.getAttribute("area"); //所在区域查询关键字
    String computerState = (String)request.getAttribute("computerState"); //电脑状态查询关键字
    String addTime = (String)request.getAttribute("addTime"); //添加时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>计算机查询</title>
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
  			<li><a href="<%=basePath %>Computer/frontlist">计算机信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Computer/computer_frontAdd.jsp" style="display:none;">添加计算机</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<computerList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Computer computer = computerList.get(i); //获取到计算机对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Computer/<%=computer.getComputerNo() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=computer.getComputerPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		计算机编号:<%=computer.getComputerNo() %>
			     	</div>
			     	<div class="field">
	            		计算机名称:<%=computer.getComputerName() %>
			     	</div>
			     	<div class="field">
	            		所在区域:<%=computer.getArea() %>
			     	</div>
			     	<div class="field">
	            		电脑状态:<%=computer.getComputerState() %>
			     	</div>
			     	<div class="field">
	            		添加时间:<%=computer.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Computer/<%=computer.getComputerNo() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="computerEdit('<%=computer.getComputerNo() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="computerDelete('<%=computer.getComputerNo() %>');" style="display:none;">删除</a>
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
    		<h1>计算机查询</h1>
		</div>
		<form name="computerQueryForm" id="computerQueryForm" action="<%=basePath %>Computer/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="computerNo">计算机编号:</label>
				<input type="text" id="computerNo" name="computerNo" value="<%=computerNo %>" class="form-control" placeholder="请输入计算机编号">
			</div>
			<div class="form-group">
				<label for="computerName">计算机名称:</label>
				<input type="text" id="computerName" name="computerName" value="<%=computerName %>" class="form-control" placeholder="请输入计算机名称">
			</div>
			<div class="form-group">
				<label for="area">所在区域:</label>
				<input type="text" id="area" name="area" value="<%=area %>" class="form-control" placeholder="请输入所在区域">
			</div>
			<div class="form-group">
				<label for="computerState">电脑状态:</label>
				<input type="text" id="computerState" name="computerState" value="<%=computerState %>" class="form-control" placeholder="请输入电脑状态">
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
<div id="computerEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;计算机信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="computerEditForm" id="computerEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="computer_computerNo_edit" class="col-md-3 text-right">计算机编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="computer_computerNo_edit" name="computer.computerNo" class="form-control" placeholder="请输入计算机编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="computer_computerName_edit" class="col-md-3 text-right">计算机名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="computer_computerName_edit" name="computer.computerName" class="form-control" placeholder="请输入计算机名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computer_area_edit" class="col-md-3 text-right">所在区域:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="computer_area_edit" name="computer.area" class="form-control" placeholder="请输入所在区域">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computer_computerPhoto_edit" class="col-md-3 text-right">计算机照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="computer_computerPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="computer_computerPhoto" name="computer.computerPhoto"/>
			    <input id="computerPhotoFile" name="computerPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computer_computerDesc_edit" class="col-md-3 text-right">计算机描述:</label>
		  	 <div class="col-md-9">
			 	<textarea name="computer.computerDesc" id="computer_computerDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computer_computerState_edit" class="col-md-3 text-right">电脑状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="computer_computerState_edit" name="computer.computerState" class="form-control" placeholder="请输入电脑状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="computer_addTime_edit" class="col-md-3 text-right">添加时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date computer_addTime_edit col-md-12" data-link-field="computer_addTime_edit">
                    <input class="form-control" id="computer_addTime_edit" name="computer.addTime" size="16" type="text" value="" placeholder="请选择添加时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#computerEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxComputerModify();">提交</button>
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
var computer_computerDesc_edit = UE.getEditor('computer_computerDesc_edit'); //计算机描述编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.computerQueryForm.currentPage.value = currentPage;
    document.computerQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.computerQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.computerQueryForm.currentPage.value = pageValue;
    documentcomputerQueryForm.submit();
}

/*弹出修改计算机界面并初始化数据*/
function computerEdit(computerNo) {
	$.ajax({
		url :  basePath + "Computer/" + computerNo + "/update",
		type : "get",
		dataType: "json",
		success : function (computer, response, status) {
			if (computer) {
				$("#computer_computerNo_edit").val(computer.computerNo);
				$("#computer_computerName_edit").val(computer.computerName);
				$("#computer_area_edit").val(computer.area);
				$("#computer_computerPhoto").val(computer.computerPhoto);
				$("#computer_computerPhotoImg").attr("src", basePath +　computer.computerPhoto);
				computer_computerDesc_edit.setContent(computer.computerDesc, false);
				$("#computer_computerState_edit").val(computer.computerState);
				$("#computer_addTime_edit").val(computer.addTime);
				$('#computerEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除计算机信息*/
function computerDelete(computerNo) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Computer/deletes",
			data : {
				computerNos : computerNo,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#computerQueryForm").submit();
					//location.href= basePath + "Computer/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交计算机信息表单给服务器端修改*/
function ajaxComputerModify() {
	$.ajax({
		url :  basePath + "Computer/" + $("#computer_computerNo_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#computerEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#computerQueryForm").submit();
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
    $('.computer_addTime_edit').datetimepicker({
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

