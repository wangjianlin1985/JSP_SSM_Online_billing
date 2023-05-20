var computer_manage_tool = null; 
$(function () { 
	initComputerManageTool(); //建立Computer管理对象
	computer_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#computer_manage").datagrid({
		url : 'Computer/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "computerNo",
		sortOrder : "desc",
		toolbar : "#computer_manage_tool",
		columns : [[
			{
				field : "computerNo",
				title : "计算机编号",
				width : 140,
			},
			{
				field : "computerName",
				title : "计算机名称",
				width : 140,
			},
			{
				field : "area",
				title : "所在区域",
				width : 140,
			},
			{
				field : "computerPhoto",
				title : "计算机照片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "computerState",
				title : "电脑状态",
				width : 140,
			},
			{
				field : "addTime",
				title : "添加时间",
				width : 140,
			},
		]],
	});

	$("#computerEditDiv").dialog({
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
				if ($("#computerEditForm").form("validate")) {
					//验证表单 
					if(!$("#computerEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#computerEditForm").form({
						    url:"Computer/" + $("#computer_computerNo_edit").val() + "/update",
						    onSubmit: function(){
								if($("#computerEditForm").form("validate"))  {
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
			                        $("#computerEditDiv").dialog("close");
			                        computer_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#computerEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#computerEditDiv").dialog("close");
				$("#computerEditForm").form("reset"); 
			},
		}],
	});
});

function initComputerManageTool() {
	computer_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#computer_manage").datagrid("reload");
		},
		redo : function () {
			$("#computer_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#computer_manage").datagrid("options").queryParams;
			queryParams["computerNo"] = $("#computerNo").val();
			queryParams["computerName"] = $("#computerName").val();
			queryParams["area"] = $("#area").val();
			queryParams["computerState"] = $("#computerState").val();
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			$("#computer_manage").datagrid("options").queryParams=queryParams; 
			$("#computer_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#computerQueryForm").form({
			    url:"Computer/OutToExcel",
			});
			//提交表单
			$("#computerQueryForm").submit();
		},
		remove : function () {
			var rows = $("#computer_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var computerNos = [];
						for (var i = 0; i < rows.length; i ++) {
							computerNos.push(rows[i].computerNo);
						}
						$.ajax({
							type : "POST",
							url : "Computer/deletes",
							data : {
								computerNos : computerNos.join(","),
							},
							beforeSend : function () {
								$("#computer_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#computer_manage").datagrid("loaded");
									$("#computer_manage").datagrid("load");
									$("#computer_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#computer_manage").datagrid("loaded");
									$("#computer_manage").datagrid("load");
									$("#computer_manage").datagrid("unselectAll");
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
			var rows = $("#computer_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Computer/" + rows[0].computerNo +  "/update",
					type : "get",
					data : {
						//computerNo : rows[0].computerNo,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (computer, response, status) {
						$.messager.progress("close");
						if (computer) { 
							$("#computerEditDiv").dialog("open");
							$("#computer_computerNo_edit").val(computer.computerNo);
							$("#computer_computerNo_edit").validatebox({
								required : true,
								missingMessage : "请输入计算机编号",
								editable: false
							});
							$("#computer_computerName_edit").val(computer.computerName);
							$("#computer_computerName_edit").validatebox({
								required : true,
								missingMessage : "请输入计算机名称",
							});
							$("#computer_area_edit").val(computer.area);
							$("#computer_area_edit").validatebox({
								required : true,
								missingMessage : "请输入所在区域",
							});
							$("#computer_computerPhoto").val(computer.computerPhoto);
							$("#computer_computerPhotoImg").attr("src", computer.computerPhoto);
							computer_computerDesc_editor.setContent(computer.computerDesc, false);
							$("#computer_computerState_edit").val(computer.computerState);
							$("#computer_computerState_edit").validatebox({
								required : true,
								missingMessage : "请输入电脑状态",
							});
							$("#computer_addTime_edit").datetimebox({
								value: computer.addTime,
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
