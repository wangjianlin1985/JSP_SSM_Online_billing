$(function () {
	$("#computerUse_userObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#computerUse_userObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#computerUse_userObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#computerUse_computerObj_computerNo").combobox({
	    url:'Computer/listKxAll',
	    valueField: "computerNo",
	    textField: "computerName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#computerUse_computerObj_computerNo").combobox("getData"); 
            if (data.length > 0) {
                $("#computerUse_computerObj_computerNo").combobox("select", data[0].computerNo);
            }
        }
	});
	$("#computerUse_chargingObj_chargingId").combobox({
	    url:'Charging/listAll',
	    valueField: "chargingId",
	    textField: "chargingName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#computerUse_chargingObj_chargingId").combobox("getData"); 
            if (data.length > 0) {
                $("#computerUse_chargingObj_chargingId").combobox("select", data[0].chargingId);
            }
        }
	});
	$("#computerUse_startTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#computerUse_jiezhangFlag").validatebox({
		required : true, 
		missingMessage : '请输入是否结账',
	});

	$("#computerUse_useMoney").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入结账金额',
		invalidMessage : '结账金额输入不对',
	});

	//单击添加按钮
	$("#computerUseAddButton").click(function () {
		//验证表单 
		if(!$("#computerUseAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#computerUseAddForm").form({
			    url:"ComputerUse/add",
			    onSubmit: function(){
					if($("#computerUseAddForm").form("validate"))  { 
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
                        $("#computerUseAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#computerUseAddForm").submit();
		}
	});

	//单击清空按钮
	$("#computerUseClearButton").click(function () { 
		$("#computerUseAddForm").form("clear"); 
	});
});
