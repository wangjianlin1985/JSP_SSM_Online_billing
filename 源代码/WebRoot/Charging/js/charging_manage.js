var charging_manage_tool = null; 
$(function () { 
	initChargingManageTool(); //建立Charging管理对象
	charging_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#charging_manage").datagrid({
		url : 'Charging/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "chargingId",
		sortOrder : "desc",
		toolbar : "#charging_manage_tool",
		columns : [[
			{
				field : "chargingId",
				title : "计费id",
				width : 70,
			},
			{
				field : "chargingName",
				title : "计费名称",
				width : 140,
			},
			{
				field : "chargingMoney",
				title : "计费金额",
				width : 70,
			},
			{
				field : "moneyWay",
				title : "缴费算法",
				width : 140,
			},
		]],
	});

	$("#chargingEditDiv").dialog({
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
				if ($("#chargingEditForm").form("validate")) {
					//验证表单 
					if(!$("#chargingEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#chargingEditForm").form({
						    url:"Charging/" + $("#charging_chargingId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#chargingEditForm").form("validate"))  {
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
			                        $("#chargingEditDiv").dialog("close");
			                        charging_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#chargingEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#chargingEditDiv").dialog("close");
				$("#chargingEditForm").form("reset"); 
			},
		}],
	});
});

function initChargingManageTool() {
	charging_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#charging_manage").datagrid("reload");
		},
		redo : function () {
			$("#charging_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#charging_manage").datagrid("options").queryParams;
			queryParams["moneyWay"] = $("#moneyWay").val();
			$("#charging_manage").datagrid("options").queryParams=queryParams; 
			$("#charging_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#chargingQueryForm").form({
			    url:"Charging/OutToExcel",
			});
			//提交表单
			$("#chargingQueryForm").submit();
		},
		remove : function () {
			var rows = $("#charging_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var chargingIds = [];
						for (var i = 0; i < rows.length; i ++) {
							chargingIds.push(rows[i].chargingId);
						}
						$.ajax({
							type : "POST",
							url : "Charging/deletes",
							data : {
								chargingIds : chargingIds.join(","),
							},
							beforeSend : function () {
								$("#charging_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#charging_manage").datagrid("loaded");
									$("#charging_manage").datagrid("load");
									$("#charging_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#charging_manage").datagrid("loaded");
									$("#charging_manage").datagrid("load");
									$("#charging_manage").datagrid("unselectAll");
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
			var rows = $("#charging_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Charging/" + rows[0].chargingId +  "/update",
					type : "get",
					data : {
						//chargingId : rows[0].chargingId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (charging, response, status) {
						$.messager.progress("close");
						if (charging) { 
							$("#chargingEditDiv").dialog("open");
							$("#charging_chargingId_edit").val(charging.chargingId);
							$("#charging_chargingId_edit").validatebox({
								required : true,
								missingMessage : "请输入计费id",
								editable: false
							});
							$("#charging_chargingName_edit").val(charging.chargingName);
							$("#charging_chargingName_edit").validatebox({
								required : true,
								missingMessage : "请输入计费名称",
							});
							$("#charging_chargingMoney_edit").val(charging.chargingMoney);
							$("#charging_chargingMoney_edit").validatebox({
								required : true,
								validType : "number",
								missingMessage : "请输入计费金额",
								invalidMessage : "计费金额输入不对",
							});
							$("#charging_moneyWay_edit").val(charging.moneyWay);
							$("#charging_moneyWay_edit").validatebox({
								required : true,
								missingMessage : "请输入缴费算法",
							});
							$("#charging_chargingMemo_edit").val(charging.chargingMemo);
							$("#charging_chargingMemo_edit").validatebox({
								required : true,
								missingMessage : "请输入计费说明",
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
