<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/callInfo.css" />
<div id="callInfo_editDiv">
	<form id="callInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="callInfo_callId_edit" name="callInfo.callId" value="<%=request.getParameter("callId") %>" style="width:200px" />
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
				<input class="textbox" type="text" id="callInfo_handFlag_edit" name="callInfo.handFlag" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">处理备注:</span>
			<span class="inputControl">
				<textarea id="callInfo_handMemo_edit" name="callInfo.handMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="callInfoModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/CallInfo/js/callInfo_modify.js"></script> 
