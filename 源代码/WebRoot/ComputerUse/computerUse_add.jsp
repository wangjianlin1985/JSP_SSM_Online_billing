<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/computerUse.css" />
<div id="computerUseAddDiv">
	<form id="computerUseAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">上机用户:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_userObj_user_name" name="computerUse.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">使用电脑:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_computerObj_computerNo" name="computerUse.computerObj.computerNo" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">计费方式:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_chargingObj_chargingId" name="computerUse.chargingObj.chargingId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">上机时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_startTime" name="computerUse.startTime" />

			</span>

		</div>
		<div style="display:none;">
			<span class="label">下机时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_endTime" name="computerUse.endTime" value="--" style="width:200px" />

			</span>

		</div>
		<div style="display:none;">
			<span class="label">是否结账:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_jiezhangFlag" name="computerUse.jiezhangFlag" value="否" style="width:200px" />

			</span>

		</div>
		<div style="display:none;">
			<span class="label">结账金额:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="computerUse_useMoney" name="computerUse.useMoney" value="0.0" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">备注信息:</span>
			<span class="inputControl">
				<textarea id="computerUse_memo" name="computerUse.memo" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="computerUseAddButton" class="easyui-linkbutton">添加</a>
			<a id="computerUseClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/ComputerUse/js/computerUse_add.js"></script> 
