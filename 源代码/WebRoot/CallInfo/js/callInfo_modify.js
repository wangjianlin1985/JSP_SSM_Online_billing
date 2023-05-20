$(function () {
	$.ajax({
		url : "CallInfo/" + $("#callInfo_callId_edit").val() + "/update",
		type : "get",
		data : {
			//callId : $("#callInfo_callId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (callInfo, response, status) {
			$.messager.progress("close");
			if (callInfo) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#callInfoModifyButton").click(function(){ 
		if ($("#callInfoEditForm").form("validate")) {
			$("#callInfoEditForm").form({
			    url:"CallInfo/" +  $("#callInfo_callId_edit").val() + "/update",
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
			$("#callInfoEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
