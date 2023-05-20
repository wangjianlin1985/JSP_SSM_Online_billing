var dishClass_manage_tool = null; 
$(function () { 
	initDishClassManageTool(); //建立DishClass管理对象
	dishClass_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#dishClass_manage").datagrid({
		url : 'DishClass/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "dishClassId",
		sortOrder : "desc",
		toolbar : "#dishClass_manage_tool",
		columns : [[
			{
				field : "dishClassId",
				title : "菜品分类id",
				width : 70,
			},
			{
				field : "dishClassName",
				title : "菜品分类名称",
				width : 140,
			},
		]],
	});

	$("#dishClassEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#dishClassEditForm").form("validate")) {
					//验证表单 
					if(!$("#dishClassEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#dishClassEditForm").form({
						    url:"DishClass/" + $("#dishClass_dishClassId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#dishClassEditForm").form("validate"))  {
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
			                        $("#dishClassEditDiv").dialog("close");
			                        dishClass_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#dishClassEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#dishClassEditDiv").dialog("close");
				$("#dishClassEditForm").form("reset"); 
			},
		}],
	});
});

function initDishClassManageTool() {
	dishClass_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#dishClass_manage").datagrid("reload");
		},
		redo : function () {
			$("#dishClass_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#dishClass_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#dishClassQueryForm").form({
			    url:"DishClass/OutToExcel",
			});
			//提交表单
			$("#dishClassQueryForm").submit();
		},
		remove : function () {
			var rows = $("#dishClass_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var dishClassIds = [];
						for (var i = 0; i < rows.length; i ++) {
							dishClassIds.push(rows[i].dishClassId);
						}
						$.ajax({
							type : "POST",
							url : "DishClass/deletes",
							data : {
								dishClassIds : dishClassIds.join(","),
							},
							beforeSend : function () {
								$("#dishClass_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#dishClass_manage").datagrid("loaded");
									$("#dishClass_manage").datagrid("load");
									$("#dishClass_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#dishClass_manage").datagrid("loaded");
									$("#dishClass_manage").datagrid("load");
									$("#dishClass_manage").datagrid("unselectAll");
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
			var rows = $("#dishClass_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "DishClass/" + rows[0].dishClassId +  "/update",
					type : "get",
					data : {
						//dishClassId : rows[0].dishClassId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (dishClass, response, status) {
						$.messager.progress("close");
						if (dishClass) { 
							$("#dishClassEditDiv").dialog("open");
							$("#dishClass_dishClassId_edit").val(dishClass.dishClassId);
							$("#dishClass_dishClassId_edit").validatebox({
								required : true,
								missingMessage : "请输入菜品分类id",
								editable: false
							});
							$("#dishClass_dishClassName_edit").val(dishClass.dishClassName);
							$("#dishClass_dishClassName_edit").validatebox({
								required : true,
								missingMessage : "请输入菜品分类名称",
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
