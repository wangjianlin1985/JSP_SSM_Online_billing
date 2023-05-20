$(function () {
	$.ajax({
		url : "DishClass/" + $("#dishClass_dishClassId_edit").val() + "/update",
		type : "get",
		data : {
			//dishClassId : $("#dishClass_dishClassId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (dishClass, response, status) {
			$.messager.progress("close");
			if (dishClass) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#dishClassModifyButton").click(function(){ 
		if ($("#dishClassEditForm").form("validate")) {
			$("#dishClassEditForm").form({
			    url:"DishClass/" +  $("#dishClass_dishClassId_edit").val() + "/update",
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
			$("#dishClassEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
