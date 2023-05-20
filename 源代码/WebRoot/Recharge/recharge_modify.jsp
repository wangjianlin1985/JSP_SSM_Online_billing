<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/recharge.css" />
<div id="recharge_editDiv">
	<form id="rechargeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">充值id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="recharge_rechargeId_edit" name="recharge.rechargeId" value="<%=request.getParameter("rechargeId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">充值用户:</span>
			<span class="inputControl">
				<input class="textbox"  id="recharge_userObj_user_name_edit" name="recharge.userObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">充值金额:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="recharge_chargeMoney_edit" name="recharge.chargeMoney" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">充值时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="recharge_chargeTime_edit" name="recharge.chargeTime" />

			</span>

		</div>
		<div>
			<span class="label">备注信息:</span>
			<span class="inputControl">
				<textarea id="recharge_chargeMemo_edit" name="recharge.chargeMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="rechargeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Recharge/js/recharge_modify.js"></script> 
