var dish_manage_tool = null; 
$(function () { 
	initDishManageTool(); //建立Dish管理对象
	dish_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#dish_manage").datagrid({
		url : 'Dish/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "dishNo",
		sortOrder : "desc",
		toolbar : "#dish_manage_tool",
		columns : [[
			{
				field : "dishNo",
				title : "菜品编号",
				width : 140,
			},
			{
				field : "dishClassObj",
				title : "菜品类别",
				width : 140,
			},
			{
				field : "dishName",
				title : "菜品名称",
				width : 140,
			},
			{
				field : "dishPhoto",
				title : "菜品图片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "dishPrice",
				title : "菜品单价",
				width : 70,
			},
			{
				field : "tuijianFlag",
				title : "是否推荐",
				width : 140,
			},
			{
				field : "upState",
				title : "上架状态",
				width : 140,
			},
			{
				field : "viewNum",
				title : "浏览量",
				width : 70,
			},
			{
				field : "addTime",
				title : "添加时间",
				width : 140,
			},
		]],
	});

	$("#dishEditDiv").dialog({
		title : "修改管理",
		top: "10px",
		width : 1000,
		height : 600,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#dishEditForm").form("validate")) {
					//验证表单 
					if(!$("#dishEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#dishEditForm").form({
						    url:"Dish/" + $("#dish_dishNo_edit").val() + "/update",
						    onSubmit: function(){
								if($("#dishEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#dishEditDiv").dialog("close");
			                        dish_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#dishEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#dishEditDiv").dialog("close");
				$("#dishEditForm").form("reset"); 
			},
		}],
	});
});

function initDishManageTool() {
	dish_manage_tool = {
		init: function() {
			$.ajax({
				url : "DishClass/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#dishClassObj_dishClassId_query").combobox({ 
					    valueField:"dishClassId",
					    textField:"dishClassName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{dishClassId:0,dishClassName:"不限制"});
					$("#dishClassObj_dishClassId_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#dish_manage").datagrid("reload");
		},
		redo : function () {
			$("#dish_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#dish_manage").datagrid("options").queryParams;
			queryParams["dishNo"] = $("#dishNo").val();
			queryParams["dishClassObj.dishClassId"] = $("#dishClassObj_dishClassId_query").combobox("getValue");
			queryParams["dishName"] = $("#dishName").val();
			queryParams["tuijianFlag"] = $("#tuijianFlag").val();
			queryParams["upState"] = $("#upState").val();
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			$("#dish_manage").datagrid("options").queryParams=queryParams; 
			$("#dish_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#dishQueryForm").form({
			    url:"Dish/OutToExcel",
			});
			//提交表单
			$("#dishQueryForm").submit();
		},
		remove : function () {
			var rows = $("#dish_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var dishNos = [];
						for (var i = 0; i < rows.length; i ++) {
							dishNos.push(rows[i].dishNo);
						}
						$.ajax({
							type : "POST",
							url : "Dish/deletes",
							data : {
								dishNos : dishNos.join(","),
							},
							beforeSend : function () {
								$("#dish_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#dish_manage").datagrid("loaded");
									$("#dish_manage").datagrid("load");
									$("#dish_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#dish_manage").datagrid("loaded");
									$("#dish_manage").datagrid("load");
									$("#dish_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#dish_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Dish/" + rows[0].dishNo +  "/update",
					type : "get",
					data : {
						//dishNo : rows[0].dishNo,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (dish, response, status) {
						$.messager.progress("close");
						if (dish) { 
							$("#dishEditDiv").dialog("open");
							$("#dish_dishNo_edit").val(dish.dishNo);
							$("#dish_dishNo_edit").validatebox({
								required : true,
								missingMessage : "请输入菜品编号",
								editable: false
							});
							$("#dish_dishClassObj_dishClassId_edit").combobox({
								url:"DishClass/listAll",
							    valueField:"dishClassId",
							    textField:"dishClassName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#dish_dishClassObj_dishClassId_edit").combobox("select", dish.dishClassObjPri);
									//var data = $("#dish_dishClassObj_dishClassId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#dish_dishClassObj_dishClassId_edit").combobox("select", data[0].dishClassId);
						            //}
								}
							});
							$("#dish_dishName_edit").val(dish.dishName);
							$("#dish_dishName_edit").validatebox({
								required : true,
								missingMessage : "请输入菜品名称",
							});
							$("#dish_dishPhoto").val(dish.dishPhoto);
							$("#dish_dishPhotoImg").attr("src", dish.dishPhoto);
							$("#dish_dishPrice_edit").val(dish.dishPrice);
							$("#dish_dishPrice_edit").validatebox({
								required : true,
								validType : "number",
								missingMessage : "请输入菜品单价",
								invalidMessage : "菜品单价输入不对",
							});
							$("#dish_tuijianFlag_edit").val(dish.tuijianFlag);
							$("#dish_tuijianFlag_edit").validatebox({
								required : true,
								missingMessage : "请输入是否推荐",
							});
							$("#dish_upState_edit").val(dish.upState);
							$("#dish_upState_edit").validatebox({
								required : true,
								missingMessage : "请输入上架状态",
							});
							$("#dish_viewNum_edit").val(dish.viewNum);
							$("#dish_viewNum_edit").validatebox({
								required : true,
								validType : "integer",
								missingMessage : "请输入浏览量",
								invalidMessage : "浏览量输入不对",
							});
							dish_dishDesc_editor.setContent(dish.dishDesc, false);
							$("#dish_addTime_edit").datetimebox({
								value: dish.addTime,
							    required: true,
							    showSeconds: true,
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
