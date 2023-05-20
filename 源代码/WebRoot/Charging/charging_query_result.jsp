<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/charging.css" /> 

<div id="charging_manage"></div>
<div id="charging_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="charging_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="charging_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="charging_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="charging_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="charging_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="chargingQueryForm" method="post">
			缴费算法：<input type="text" class="textbox" id="moneyWay" name="moneyWay" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="charging_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="chargingEditDiv">
	<form id="chargingEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">计费id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="charging_chargingId_edit" name="charging.chargingId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">计费名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="charging_chargingName_edit" name="charging.chargingName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">计费金额:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="charging_chargingMoney_edit" name="charging.chargingMoney" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">缴费算法:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="charging_moneyWay_edit" name="charging.moneyWay" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">计费说明:</span>
			<span class="inputControl">
				<textarea id="charging_chargingMemo_edit" name="charging.chargingMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Charging/js/charging_manage.js"></script> 
