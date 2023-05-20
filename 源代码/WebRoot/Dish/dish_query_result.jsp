<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dish.css" /> 

<div id="dish_manage"></div>
<div id="dish_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="dish_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="dish_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="dish_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="dish_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="dish_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="dishQueryForm" method="post">
			菜品编号：<input type="text" class="textbox" id="dishNo" name="dishNo" style="width:110px" />
			菜品类别：<input class="textbox" type="text" id="dishClassObj_dishClassId_query" name="dishClassObj.dishClassId" style="width: auto"/>
			菜品名称：<input type="text" class="textbox" id="dishName" name="dishName" style="width:110px" />
			是否推荐：<input type="text" class="textbox" id="tuijianFlag" name="tuijianFlag" style="width:110px" />
			上架状态：<input type="text" class="textbox" id="upState" name="upState" style="width:110px" />
			添加时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="dish_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="dishEditDiv">
	<form id="dishEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">菜品编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_dishNo_edit" name="dish.dishNo" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">菜品类别:</span>
			<span class="inputControl">
				<input class="textbox"  id="dish_dishClassObj_dishClassId_edit" name="dish.dishClassObj.dishClassId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">菜品名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_dishName_edit" name="dish.dishName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">菜品图片:</span>
			<span class="inputControl">
				<img id="dish_dishPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="dish_dishPhoto" name="dish.dishPhoto"/>
				<input id="dishPhotoFile" name="dishPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">菜品单价:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_dishPrice_edit" name="dish.dishPrice" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">是否推荐:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_tuijianFlag_edit" name="dish.tuijianFlag" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">上架状态:</span>
			<span class="inputControl">
				<select id="dish_upState_edit" name="dish.upState">
					<option value="上架中">上架中</option>
					<option value="下架中">下架中</option>
				</select>
			</span>

		</div>
		<div>
			<span class="label">浏览量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_viewNum_edit" name="dish.viewNum" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">菜品描述:</span>
			<span class="inputControl">
				<script name="dish.dishDesc" id="dish_dishDesc_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">添加时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="dish_addTime_edit" name="dish.addTime" />

			</span>

		</div>
	</form>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var dish_dishDesc_editor = UE.getEditor('dish_dishDesc_edit'); //菜品描述编辑器
</script>
<script type="text/javascript" src="Dish/js/dish_manage.js"></script> 
