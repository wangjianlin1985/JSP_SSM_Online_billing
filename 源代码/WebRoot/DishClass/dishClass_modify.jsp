<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dishClass.css" />
<div id="dishClass_editDiv">
	<form id="dishClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">菜品分类id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dishClass_dishClassId_edit" name="dishClass.dishClassId" value="<%=request.getParameter("dishClassId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">菜品分类名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dishClass_dishClassName_edit" name="dishClass.dishClassName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="dishClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/DishClass/js/dishClass_modify.js"></script> 
