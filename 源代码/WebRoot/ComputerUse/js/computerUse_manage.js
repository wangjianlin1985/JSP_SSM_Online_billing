var computerUse_manage_tool = null; 

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    
    if (hours >= 0 && hours <= 9) {
    	hours = "0" + hours;
    }
    if (minutes >= 0 && minutes <= 9) {
    	minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
    	seconds = "0" + seconds;
    }
    
    
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + hours + seperator2 + minutes
            + seperator2 + seconds;
    return currentdate;
}

$(function () { 
	initComputerUseManageTool(); //建立ComputerUse管理对象
	computerUse_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#computerUse_manage").datagrid({
		url : 'ComputerUse/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "cuId",
		sortOrder : "desc",
		toolbar : "#computerUse_manage_tool",
		columns : [[
			{
				field : "cuId",
				title : "记录id",
				width : 70,
			},
			{
				field : "userObj",
				title : "上机用户",
				width : 140,
			},
			{
				field : "computerObj",
				title : "使用电脑",
				width : 140,
			},
			{
				field : "chargingObj",
				title : "计费方式",
				width : 140,
			},
			{
				field : "startTime",
				title : "上机时间",
				width : 140,
			},
			{
				field : "endTime",
				title : "下机时间",
				width : 140,
			},
			{
				field : "jiezhangFlag",
				title : "是否结账",
				width : 140,
			},
			{
				field : "useMoney",
				title : "结账金额",
				width : 70,
			},
		]],
	});

	$("#computerUseEditDiv").dialog({
		title : "结账管理",
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
				if ($("#computerUseEditForm").form("validate")) {
					//验证表单 
					if(!$("#computerUseEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#computerUseEditForm").form({
						    url:"ComputerUse/" + $("#computerUse_cuId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#computerUseEditForm").form("validate"))  {
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
			                        $("#computerUseEditDiv").dialog("close");
			                        computerUse_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#computerUseEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#computerUseEditDiv").dialog("close");
				$("#computerUseEditForm").form("reset"); 
			},
		}],
	});
});

function initComputerUseManageTool() {
	computerUse_manage_tool = {
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
			$.ajax({
				url : "Computer/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#computerObj_computerNo_query").combobox({ 
					    valueField:"computerNo",
					    textField:"computerName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{computerNo:"",computerName:"不限制"});
					$("#computerObj_computerNo_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "Charging/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#chargingObj_chargingId_query").combobox({ 
					    valueField:"chargingId",
					    textField:"chargingName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{chargingId:0,chargingName:"不限制"});
					$("#chargingObj_chargingId_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#computerUse_manage").datagrid("reload");
		},
		redo : function () {
			$("#computerUse_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#computerUse_manage").datagrid("options").queryParams;
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["computerObj.computerNo"] = $("#computerObj_computerNo_query").combobox("getValue");
			queryParams["chargingObj.chargingId"] = $("#chargingObj_chargingId_query").combobox("getValue");
			queryParams["startTime"] = $("#startTime").datebox("getValue"); 
			queryParams["jiezhangFlag"] = $("#jiezhangFlag").val();
			$("#computerUse_manage").datagrid("options").queryParams=queryParams; 
			$("#computerUse_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#computerUseQueryForm").form({
			    url:"ComputerUse/OutToExcel",
			});
			//提交表单
			$("#computerUseQueryForm").submit();
		},
		remove : function () {
			var rows = $("#computerUse_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var cuIds = [];
						for (var i = 0; i < rows.length; i ++) {
							cuIds.push(rows[i].cuId);
						}
						$.ajax({
							type : "POST",
							url : "ComputerUse/deletes",
							data : {
								cuIds : cuIds.join(","),
							},
							beforeSend : function () {
								$("#computerUse_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#computerUse_manage").datagrid("loaded");
									$("#computerUse_manage").datagrid("load");
									$("#computerUse_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#computerUse_manage").datagrid("loaded");
									$("#computerUse_manage").datagrid("load");
									$("#computerUse_manage").datagrid("unselectAll");
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
			var rows = $("#computerUse_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				var jiezhangFlag = rows[0].jiezhangFlag;
				if(jiezhangFlag == "是") {
					$.messager.alert("警告操作！", "该条记录已经结过账了！", "warning");
					return;
				}
				
				$.ajax({
					url : "ComputerUse/" + rows[0].cuId +  "/update",
					type : "get",
					data : {
						//cuId : rows[0].cuId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (computerUse, response, status) {
						$.messager.progress("close");
						if (computerUse) { 
							$("#computerUseEditDiv").dialog("open");
							$("#computerUse_cuId_edit").val(computerUse.cuId);
							$("#computerUse_cuId_edit").validatebox({
								required : true,
								missingMessage : "请输入记录id",
								editable: false
							});
							$("#computerUse_userObj_user_name_edit").combobox({
								url:"UserInfo/listAll",
							    valueField:"user_name",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#computerUse_userObj_user_name_edit").combobox("select", computerUse.userObjPri);
									//var data = $("#computerUse_userObj_user_name_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#computerUse_userObj_user_name_edit").combobox("select", data[0].user_name);
						            //}
								}
							});
							$("#computerUse_computerObj_computerNo_edit").combobox({
								url:"Computer/listAll",
							    valueField:"computerNo",
							    textField:"computerName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#computerUse_computerObj_computerNo_edit").combobox("select", computerUse.computerObjPri);
									//var data = $("#computerUse_computerObj_computerNo_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#computerUse_computerObj_computerNo_edit").combobox("select", data[0].computerNo);
						            //}
								}
							});
							$("#computerUse_chargingObj_chargingId_edit").combobox({
								url:"Charging/listAll",
							    valueField:"chargingId",
							    textField:"chargingName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#computerUse_chargingObj_chargingId_edit").combobox("select", computerUse.chargingObjPri);
									//var data = $("#computerUse_chargingObj_chargingId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#computerUse_chargingObj_chargingId_edit").combobox("select", data[0].chargingId);
						            //} 
								}
							});
							$("#computerUse_startTime_edit").datetimebox({
								value: computerUse.startTime,
							    required: true,
							    showSeconds: true,
							});
							
							 
							var nowTime = getNowFormatDate();
							var startTime = new Date(computerUse.startTime);
							var endTime = new Date(nowTime);
							var ms = endTime.getTime() - startTime.getTime();
							 
							var hours = ms/1000/60/60;
							if(hours < (Math.floor(hours)+0.5))
								hours = Math.floor(hours)+0.5;  //不足0.5小时算0.5小时
							else 
								hours = Math.ceil(hours); //超过0.5小时算1个小时 
							
							$("#computerUse_endTime_edit").val(nowTime);
							
							var chargingMoney = null;
							
							$.ajax({
								url :  "Charging/" + computerUse.chargingObjPri + "/update",
								type : "get",
								dataType: "json",
								success : function (charging, response, status) {
									if (charging) {
										chargingMoney = charging.chargingMoney;
										if(computerUse.chargingObjPri != 2) {
											//不是包夜就是按照小时计费
											$("#computerUse_useMoney_edit").val(hours * chargingMoney);
										} else {
											$("#computerUse_useMoney_edit").val(chargingMoney);
										}
										 
									} else {
										alert("获取信息失败！");
									}
								}
							});
							
							 
							
							$("#computerUse_jiezhangFlag_edit").val("是");
							$("#computerUse_jiezhangFlag_edit").validatebox({
								required : true,
								missingMessage : "请输入是否结账",
							});
							//$("#computerUse_useMoney_edit").val(computerUse.useMoney);
							$("#computerUse_useMoney_edit").validatebox({
								required : true,
								validType : "number",
								missingMessage : "请输入结账金额",
								invalidMessage : "结账金额输入不对",
							});
							$("#computerUse_memo_edit").val(computerUse.memo);
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
