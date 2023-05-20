<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/computer.css" /> 

<div id="computer_manage"></div>
<div id="computer_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="computer_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="computer_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="computer_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="computer_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="computer_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="computerQueryForm" method="post">
			计算机编号：<input type="text" class="textbox" id="computerNo" name="computerNo" style="width:110px" />
			计算机名称：<input type="text" class="textbox" id="computerName" name="computerName" style="width:110px" />
			所在区域：<input type="text" class="textbox" id="area" name="area" style="width:110px" />
			电脑状态：<input type="text" class="textbox" id="computerState" name="computerState" style="width:110px" />
			添加时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="computer_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="computerEditDiv">
	<form id="computerEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">计算机编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computer_computerNo_edit" name="computer.computerNo" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">计算机名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computer_computerName_edit" name="computer.computerName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">所在区域:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computer_area_edit" name="computer.area" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">计算机照片:</span>
			<span class="inputControl">
				<img id="computer_computerPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="computer_computerPhoto" name="computer.computerPhoto"/>
				<input id="computerPhotoFile" name="computerPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">计算机描述:</span>
			<span class="inputControl">
				<script name="computer.computerDesc" id="computer_computerDesc_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">电脑状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computer_computerState_edit" name="computer.computerState" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">添加时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computer_addTime_edit" name="computer.addTime" />

			</span>

		</div>
	</form>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var computer_computerDesc_editor = UE.getEditor('computer_computerDesc_edit'); //计算机描述编辑器
</script>
<script type="text/javascript" src="Computer/js/computer_manage.js"></script> 
