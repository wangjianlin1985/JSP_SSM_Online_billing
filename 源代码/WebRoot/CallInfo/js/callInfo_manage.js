var callInfo_manage_tool = null; 
$(function () { 
	initCallInfoManageTool(); //建立CallInfo管理对象
	callInfo_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#callInfo_manage").datagrid({
		url : 'CallInfo/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "callId",
		sortOrder : "desc",
		toolbar : "#callInfo_manage_tool",
		columns : [[
			{
				field : "callId",
				title : "记录id",
				width : 70,
			},
			{
				field : "userObj",
				title : "呼叫会员",
				width : 140,
			},
			{
				field : "callTime",
				title : "呼叫时间",
				width : 140,
			},
			{
				field : "handFlag",
				title : "处理状态",
				width : 140,
			},
			{
				field : "handMemo",
				title : "处理备注",
				width : 140,
			},
		]],
	});

	$("#callInfoEditDiv").dialog({
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
				if ($("#callInfoEditForm").form("validate")) {
					//验证表单 
					if(!$("#callInfoEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#callInfoEditForm").form({
						    url:"CallInfo/" + $("#callInfo_callId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#callInfoEditForm").form("validate"))  {
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
			                        $("#callInfoEditDiv").dialog("close");
			                        callInfo_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#callInfoEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#callInfoEditDiv").dialog("close");
				$("#callInfoEditForm").form("reset"); 
			},
		}],
	});
});

function initCallInfoManageTool() {
	callInfo_manage_tool = {
		init: function() {
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#userObj_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",name:"不限制"});
					$("#userObj_user_name_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#callInfo_manage").datagrid("reload");
		},
		redo : function () {
			$("#callInfo_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#callInfo_manage").datagrid("options").queryParams;
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["callTime"] = $("#callTime").datebox("getValue"); 
			queryParams["handFlag"] = $("#handFlag").val();
			$("#callInfo_manage").datagrid("options").queryParams=queryParams; 
			$("#callInfo_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#callInfoQueryForm").form({
			    url:"CallInfo/OutToExcel",
			});
			//提交表单
			$("#callInfoQueryForm").submit();
		},
		remove : function () {
			var rows = $("#callInfo_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var callIds = [];
						for (var i = 0; i < rows.length; i ++) {
							callIds.push(rows[i].callId);
						}
						$.ajax({
							type : "POST",
							url : "CallInfo/deletes",
							data : {
								callIds : callIds.join(","),
							},
							beforeSend : function () {
								$("#callInfo_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#callInfo_manage").datagrid("loaded");
									$("#callInfo_manage").datagrid("load");
									$("#callInfo_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#callInfo_manage").datagrid("loaded");
									$("#callInfo_manage").datagrid("load");
									$("#callInfo_manage").datagrid("unselectAll");
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
			var rows = $("#callInfo_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "CallInfo/" + rows[0].callId +  "/update",
					type : "get",
					data : {
						//callId : rows[0].callId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (callInfo, response, status) {
						$.messager.progress("close");
						if (callInfo) { 
							$("#callInfoEditDiv").dialog("open");
							$("#callInfo_callId_edit").val(callInfo.callId);
							$("#callInfo_callId_edit").validatebox({
								required : true,
								missingMessage : "请输入记录id",
								editable: false
							});
							$("#callInfo_userObj_user_name_edit").combobox({
								url:"UserInfo/listAll",
							    valueField:"user_name",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#callInfo_userObj_user_name_edit").combobox("select", callInfo.userObjPri);
									//var data = $("#callInfo_userObj_user_name_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#callInfo_userObj_user_name_edit").combobox("select", data[0].user_name);
						            //}
								}
							});
							$("#callInfo_callTime_edit").datetimebox({
								value: callInfo.callTime,
							    required: true,
							    showSeconds: true,
							});
							$("#callInfo_handFlag_edit").val(callInfo.handFlag);
							$("#callInfo_handFlag_edit").validatebox({
								required : true,
								missingMessage : "请输入处理状态",
							});
							$("#callInfo_handMemo_edit").val(callInfo.handMemo);
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
