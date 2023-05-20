$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('dish_dishDesc_edit');
	var dish_dishDesc_edit = UE.getEditor('dish_dishDesc_edit'); //菜品描述编辑器
	dish_dishDesc_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
  function ajaxModifyQuery() {	
	$.ajax({
		url : "Dish/" + $("#dish_dishNo_edit").val() + "/update",
		type : "get",
		data : {
			//dishNo : $("#dish_dishNo_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (dish, response, status) {
			$.messager.progress("close");
			if (dish) { 
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
				dish_dishDesc_edit.setContent(dish.dishDesc);
				$("#dish_addTime_edit").datetimebox({
					value: dish.addTime,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

  }

	$("#dishModifyButton").click(function(){ 
		if ($("#dishEditForm").form("validate")) {
			$("#dishEditForm").form({
			    url:"Dish/" +  $("#dish_dishNo_edit").val() + "/update",
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
			$("#dishEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
