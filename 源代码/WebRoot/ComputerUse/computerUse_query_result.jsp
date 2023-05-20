<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/computerUse.css" /> 

<div id="computerUse_manage"></div>
<div id="computerUse_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="computerUse_manage_tool.edit();">结账下机</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="computerUse_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="computerUse_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="computerUse_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="computerUse_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="computerUseQueryForm" method="post">
			上机用户：<input class="textbox" type="text" id="userObj_user_name_query" name="userObj.user_name" style="width: auto"/>
			使用电脑：<input class="textbox" type="text" id="computerObj_computerNo_query" name="computerObj.computerNo" style="width: auto"/>
			计费方式：<input class="textbox" type="text" id="chargingObj_chargingId_query" name="chargingObj.chargingId" style="width: auto"/>
			上机时间：<input type="text" id="startTime" name="startTime" class="easyui-datebox" editable="false" style="width:100px">
			是否结账：<input type="text" class="textbox" id="jiezhangFlag" name="jiezhangFlag" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="computerUse_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="computerUseEditDiv">
	<form id="computerUseEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_cuId_edit" name="computerUse.cuId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">上机用户:</span>
			<span class="inputControl">
				<input class="textbox"  id="computerUse_userObj_user_name_edit" name="computerUse.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">使用电脑:</span>
			<span class="inputControl">
				<input class="textbox"  id="computerUse_computerObj_computerNo_edit" name="computerUse.computerObj.computerNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">计费方式:</span>
			<span class="inputControl">
				<input class="textbox"  id="computerUse_chargingObj_chargingId_edit" name="computerUse.chargingObj.chargingId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">上机时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_startTime_edit" name="computerUse.startTime" />

			</span>

		</div>
		<div>
			<span class="label">下机时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_endTime_edit" name="computerUse.endTime" style="width:200px" />

			</span>

		</div>
		<div style="display:none;">
			<span class="label">是否结账:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_jiezhangFlag_edit" name="computerUse.jiezhangFlag" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">结账金额:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_useMoney_edit" name="computerUse.useMoney" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">备注信息:</span>
			<span class="inputControl">
				<textarea id="computerUse_memo_edit" name="computerUse.memo" rows="8" cols="60"></textarea>

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="ComputerUse/js/computerUse_manage.js"></script> 
