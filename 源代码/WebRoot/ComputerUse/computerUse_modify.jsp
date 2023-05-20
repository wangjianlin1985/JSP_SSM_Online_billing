<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/computerUse.css" />
<div id="computerUse_editDiv">
	<form id="computerUseEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_cuId_edit" name="computerUse.cuId" value="<%=request.getParameter("cuId") %>" style="width:200px" />
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
		<div>
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
		<div class="operation">
			<a id="computerUseModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/ComputerUse/js/computerUse_modify.js"></script> 
