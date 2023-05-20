<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/callInfo.css" /> 

<div id="callInfo_manage"></div>
<div id="callInfo_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="callInfo_manage_tool.edit();">处理呼叫</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="callInfo_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="callInfo_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="callInfo_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="callInfo_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="callInfoQueryForm" method="post">
			呼叫会员：<input class="textbox" type="text" id="userObj_user_name_query" name="userObj.user_name" style="width: auto"/>
			呼叫时间：<input type="text" id="callTime" name="callTime" class="easyui-datebox" editable="false" style="width:100px">
			处理状态：<input type="text" class="textbox" id="handFlag" name="handFlag" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="callInfo_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="callInfoEditDiv">
	<form id="callInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="callInfo_callId_edit" name="callInfo.callId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">呼叫会员:</span>
			<span class="inputControl">
				<input class="textbox"  id="callInfo_userObj_user_name_edit" name="callInfo.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">呼叫时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="callInfo_callTime_edit" name="callInfo.callTime" />

			</span>

		</div>
		<div>
			<span class="label">处理状态:</span>
			<span class="inputControl">
				<select id="callInfo_handFlag_edit" name="callInfo.handFlag">
					<option value="待处理">待处理</option>
					<option value="已处理">已处理</option>
				</select>
			</span>

		</div>
		<div>
			<span class="label">处理备注:</span>
			<span class="inputControl">
				<textarea id="callInfo_handMemo_edit" name="callInfo.handMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="CallInfo/js/callInfo_manage.js"></script> 
