$(function () {
	$.ajax({
		url : "ComputerUse/" + $("#computerUse_cuId_edit").val() + "/update",
		type : "get",
		data : {
			//cuId : $("#computerUse_cuId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (computerUse, response, status) {
			$.messager.progress("close");
			if (computerUse) { 
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
				$("#computerUse_endTime_edit").val(computerUse.endTime);
				$("#computerUse_jiezhangFlag_edit").val(computerUse.jiezhangFlag);
				$("#computerUse_jiezhangFlag_edit").validatebox({
					required : true,
					missingMessage : "请输入是否结账",
				});
				$("#computerUse_useMoney_edit").val(computerUse.useMoney);
				$("#computerUse_useMoney_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入结账金额",
					invalidMessage : "结账金额输入不对",
				});
				$("#computerUse_memo_edit").val(computerUse.memo);
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#computerUseModifyButton").click(function(){ 
		if ($("#computerUseEditForm").form("validate")) {
			$("#computerUseEditForm").form({
			    url:"ComputerUse/" +  $("#computerUse_cuId_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#computerUseEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
