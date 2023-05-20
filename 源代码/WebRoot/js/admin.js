var maxCallId = null;
$.ajax({
	type: "get",
	url: "CallInfo/getMaxCallId",
	dataType : "json" ,
	success : function(obj) {
		maxCallId = obj.maxCallId;
		setInterval("queryNewMaxCallId();",2000);
	}
});

function queryNewMaxCallId() { 
	$.ajax({
		type: "get",
		url: "CallInfo/getMaxCallId",
		dataType : "json" ,
		success : function(obj) {
			newMaxCallId = obj.maxCallId;
			if(newMaxCallId > maxCallId ) {
				$.messager.alert("呼叫提示","有用户呼叫网管，请尽快到呼叫中心查询处理！","warning");
				maxCallId = newMaxCallId;
			}
		}
	});
} 



$(function () {
	
	$('#nav').tree({
		//url : 'Nav/Nav_queryNavById.action', 
		lines : true,
		onLoadSuccess : function (node, data) {
			if (data) {
				$(data).each(function (index, value) {
					if (this.state == 'closed') {
						$('#nav').tree('expandAll');
					}
				});
			}
		},
		
		data: 
			[{
		       text: "会员充值管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "添加会员",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "UserInfo/userInfo_add.jsp"
			    	   	},
			    		{
			    	   		text: "会员管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "UserInfo/userInfo_query_result.jsp"
			    	    },
			    	    {
			    		    text: "添加会员充值",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "Recharge/recharge_add.jsp"
			    	   	},
			    		{
			    	   		text: "会员充值管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "Recharge/recharge_query_result.jsp"
			    	    }
			    	   ]
			},{
		       text: "计费方式管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "添加计费方式",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "Charging/charging_add.jsp"
			    	   	},
			    		{
			    	   		text: "计费方式管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "Charging/charging_query_result.jsp"
			    	    }
			    	   ]
			},{
		       text: "计算机上机管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "添加计算机",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "Computer/computer_add.jsp"
			    	   	},
			    		{
			    	   		text: "计算机管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "Computer/computer_query_result.jsp"
			    	    },
			    	    {
			    		    text: "登记上机记录",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "ComputerUse/computerUse_add.jsp"
			    	   	},
			    		{
			    	   		text: "上机记录管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "ComputerUse/computerUse_query_result.jsp"
			    	    }
			    	  
			    	   ]
			},{
		       text: "菜品点餐管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "添加菜品分类",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "DishClass/dishClass_add.jsp"
			    	   	},
			    		{
			    	   		text: "菜品分类管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "DishClass/dishClass_query_result.jsp"
			    	    },
			    	    {
			    		    text: "添加菜品",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "Dish/dish_add.jsp"
			    	   	},
			    		{
			    	   		text: "菜品管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "Dish/dish_query_result.jsp"
			    	    },
			    	    {
			    		    text: "添加点餐",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "DishOrder/dishOrder_add.jsp"
			    	   	},
			    		{
			    	   		text: "点餐管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "DishOrder/dishOrder_query_result.jsp"
			    	    }
			    	   ]
			},{
		       text: "留言管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "添加留言",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "Leaveword/leaveword_add.jsp"
			    	   	},
			    		{
			    	   		text: "留言管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "Leaveword/leaveword_query_result.jsp"
			    	    }
			    	   ]
			},{
		       text: "呼叫网管管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "添加呼叫网管",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "CallInfo/callInfo_add.jsp"
			    	   	},
			    		{
			    	   		text: "呼叫网管管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "CallInfo/callInfo_query_result.jsp"
			    	    }
			    	   ]
			},{
		       text: "新闻公告管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "添加新闻公告",
			    		    state: "open",
			    		    iconCls: "icon-add-new",
			    		    url: "Notice/notice_add.jsp"
			    	   	},
			    		{
			    	   		text: "新闻公告管理",
			    	   		state: "open",
			    	   		iconCls: "icon-manager",
			    	   		url: "Notice/notice_query_result.jsp"
			    	    }
			    	   ]
			},{
			       text: "系统管理",
			       state: "closed",
			       iconCls: "icon-system",
			       children:
			    	   [{
			    		    text: "修改密码",
			    		    state: "open",
			    		    iconCls: "icon-edit",
			    		    url: "password_modify.jsp"
			    	   	}
			    	   ]
			}],
		onClick : function (node) {
			if (node.url) {
				if ($('#tabs').tabs('exists', node.text)) {
					$('#tabs').tabs('select', node.text);
				} else {
					$('#tabs').tabs('add', {
						title : node.text,
						iconCls : node.iconCls,
						closable : true,
						href : node.url,
						//content: '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="' + node.url + '" style="width:100%;height:100%;"></iframe>'
					});
				} 
			}
		}
	});
	
	$('#tabs').tabs({
		fit : true,
		border : false, 
		//width: $("#tabs").parent().width()-100,  
	});


	$(window).resize(function () {
		$('#tabs').tabs({ 
			height: "auto"
		});  
	});
	  
	
	

	//下面是扩展自定义验证
	$.extend($.fn.validatebox.defaults.rules, {
        idcard: {// 验证身份证
            validator: function (value) {
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
            },
            message: '身份证号码格式不正确'
        },
        minLength: {
            validator: function (value, param) {
                return value.length >= param[0];
            },
            message: '请输入至少（2）个字符.'
        },
        length: { validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
            message: "输入内容长度必须介于{0}和{1}之间."
        },
        phone: {// 验证电话号码
            validator: function (value) {
                return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
            },
            message: '格式不正确,请使用下面格式:020-88888888'
        },
        mobile: {// 验证手机号码
            validator: function (value) {
                return /^(13|15|18)\d{9}$/i.test(value);
            },
            message: '手机号码格式不正确'
        },
        intOrFloat: {// 验证整数或小数
            validator: function (value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '请输入数字，并确保格式正确'
        },
        currency: {// 验证货币
            validator: function (value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '货币格式不正确'
        },
        qq: {// 验证QQ,从10000开始
            validator: function (value) {
                return /^[1-9]\d{4,9}$/i.test(value);
            },
            message: 'QQ号码格式不正确'
        },
        integer: {// 验证整数 可正负数
            validator: function (value) { 
        		return /^[+]?[0-9]\d*$/.test(value);
            },
            message: '请输入整数'
        },
        number: {  
            validator: function (value, param) {  
                return /^[0-9]+.?[0-9]*$/.test(value);  
            },  
            message: '请输入数字'  
        },
        age: {// 验证年龄
            validator: function (value) {
                return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
            },
            message: '年龄必须是0到120之间的整数'
        },

        chinese: {// 验证中文
            validator: function (value) {
                return /^[\Α-\￥]+$/i.test(value);
            },
            message: '请输入中文'
        },
        english: {// 验证英语
            validator: function (value) {
                return /^[A-Za-z]+$/i.test(value);
            },
            message: '请输入英文'
        },
        unnormal: {// 验证是否包含空格和非法字符
            validator: function (value) {
                return /.+/i.test(value);
            },
            message: '输入值不能为空和包含其他非法字符'
        },
        username: {// 验证用户名
            validator: function (value) {
                return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
            },
            message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
        },
        faxno: {// 验证传真
            validator: function (value) {
                //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
                return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
            },
            message: '传真号码不正确'
        },
        zip: {// 验证邮政编码
            validator: function (value) {
                return /^[1-9]\d{5}$/i.test(value);
            },
            message: '邮政编码格式不正确'
        },
        ip: {// 验证IP地址
            validator: function (value) {
                return /d+.d+.d+.d+/i.test(value);
            },
            message: 'IP地址格式不正确'
        },
        name: {// 验证姓名，可以是中文或英文
            validator: function (value) {
                return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
            },
            message: '请输入姓名'
        },
        date: {// 验证姓名，可以是中文或英文
            validator: function (value) {
                //格式yyyy-MM-dd或yyyy-M-d
                return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
            },
            message: '清输入合适的日期格式'
        },
        msn: {
            validator: function (value) {
                return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
            },
            message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
        },
        same: {
            validator: function (value, param) {
                if ($("#" + param[0]).val() != "" && value != "") {
                    return $("#" + param[0]).val() == value;
                } else {
                    return true;
                }
            },
            message: '两次输入的密码不一致！'
        }
    });
});
