$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('dish_dishDesc');
	var dish_dishDesc_editor = UE.getEditor('dish_dishDesc'); //菜品描述编辑框
	$("#dish_dishNo").validatebox({
		required : true, 
		missingMessage : '请输入菜品编号',
	});

	$("#dish_dishClassObj_dishClassId").combobox({
	    url:'DishClass/listAll',
	    valueField: "dishClassId",
	    textField: "dishClassName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#dish_dishClassObj_dishClassId").combobox("getData"); 
            if (data.length > 0) {
                $("#dish_dishClassObj_dishClassId").combobox("select", data[0].dishClassId);
            }
        }
	});
	$("#dish_dishName").validatebox({
		required : true, 
		missingMessage : '请输入菜品名称',
	});

	$("#dish_dishPrice").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入菜品单价',
		invalidMessage : '菜品单价输入不对',
	});

	$("#dish_tuijianFlag").validatebox({
		required : true, 
		missingMessage : '请输入是否推荐',
	});

	$("#dish_upState").validatebox({
		required : true, 
		missingMessage : '请输入上架状态',
	});

	$("#dish_viewNum").validatebox({
		required : true,
		validType : "integer",
		missingMessage : '请输入浏览量',
		invalidMessage : '浏览量输入不对',
	});

	$("#dish_addTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#dishAddButton").click(function () {
		if(dish_dishDesc_editor.getContent() == "") {
			alert("请输入菜品描述");
			return;
		}
		//验证表单 
		if(!$("#dishAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#dishAddForm").form({
			    url:"Dish/add",
			    onSubmit: function(){
					if($("#dishAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#dishAddForm").form("clear");
                        dish_dishDesc_editor.setContent("");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#dishAddForm").submit();
		}
	});

	//单击清空按钮
	$("#dishClearButton").click(function () { 
		$("#dishAddForm").form("clear"); 
	});
});
