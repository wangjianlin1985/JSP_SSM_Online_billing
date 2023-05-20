<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/callInfo.css" />
<div id="callInfoAddDiv">
	<form id="callInfoAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">呼叫会员:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="callInfo_userObj_user_name" name="callInfo.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">呼叫时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="callInfo_callTime" name="callInfo.callTime" />

			</span>

		</div>
		<div>
			<span class="label">处理状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="callInfo_handFlag" name="callInfo.handFlag" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">处理备注:</span>
			<span class="inputControl">
				<textarea id="callInfo_handMemo" name="callInfo.handMemo" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="callInfoAddButton" class="easyui-linkbutton">添加</a>
			<a id="callInfoClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/CallInfo/js/callInfo_add.js"></script> 
