var mobtain = returnCitySN;
var sex_s = "-1"
$(function() {
	$("#currCity").html(mobtain.cname);
	$("#currIP").html(mobtain.cip);
	$(".currIP_").val(mobtain.cip);
	$(".currCity_").val(mobtain.cname);

	$("#kwy_name_").focus();

	$("#kwy_name_").blur(function() {
		var v_kayname_ = $("#kwy_name_").val();
		$.ajax({
			type : "post",
			url : "/interface/changeToPinYin",
			data : {
				v_kayname_ : v_kayname_
			},
			dataType : "json",
			success : function(data) {
				if (data.mta == 1) {
					var tempLoginName = data.msg;
					if (tempLoginName) {
						$("#login_name_").val(tempLoginName);
					}
				}
			}
		});
	});

	$("#sex_").change(function() {
		sex_s = $("#sex_").val();
	});

	$("#phoneNumber_").keyup(function() {
		var user_phone_val = $("#phoneNumber_").val();
		if (!mobile_v(user_phone_val)) {
			$(".phoneNumber_s").html("<font color='red'>手机格式不正确!</font>");
		} else {
			$(".phoneNumber_s").html("<font color='green'>完成</font>");
		}
	});

	$("#email_").keyup(function() {
		var user_email_val = $("#email_").val();
		if (!email_v(user_email_val)) {
			$(".email_s").html("<font color='red'>邮箱格式不正确!</font>");
		} else {
			$(".email_s").html("<font color='green'>完成</font>");
		}
	});

	$("#pwd1_").keyup(function() {
		var pwd1 = $.trim($("#pwd_").val());
		var pwd2 = $.trim($("#pwd1_").val());
		if (pwd1 != pwd2) {
			$(".pwd_s").html("<font color='red'>两次密码不一致!</font>");
		} else {
			$(".pwd_s").html("<font color='green'>完成</font>");
		}
	});

});

function submitForm() {
	var kwy_name_ = $("#kwy_name_").val();
	var login_name_ = $("#login_name_").val();
	var phoneNumber_ = $("#phoneNumber_").val();
	var pwd_ = $("#pwd_").val();
	var pwd1_ = $("#pwd1_").val();
	var email_ = $("#email_").val();
	var systemOS = $(".systemOS").val();
	var browserName = $(".browserName").val();
	var browserVersion = $(".browserVersion").val();
	var city_ = mobtain.cname;
	var ip_ = mobtain.cip;
	if (kwy_name_ == "") {
		$.messager.alert('提示', '请输入用昵称!', 'info');
	} else if (login_name_ == "") {
		$.messager.alert('提示', '请输入用登录名!', 'info');
	} else if (phoneNumber_ == "" || !mobile_v(phoneNumber_)) {
		$.messager.alert('提示', '请输入正确手机号码!', 'info');
	} else if (pwd_ == "" || (pwd_ != pwd1_)) {
		$.messager.alert('提示', '没有输入密码或者两次密码不一致!', 'info');
	} else if (email_ == "" || !email_v(email_)) {
		$.messager.alert('提示', '请输入正确的邮箱!', 'info');
	} else {
		$.ajax({
			type : "post",
			url : "/sys/register",
			data : {
				kwy_name : kwy_name_,
				login_name : login_name_,
				pwd : pwd_,
				phoneNumber : phoneNumber_,
				email : email_,
				sex : sex_s,
				systemOS : systemOS,
				browserName : browserName,
				browserVersion : browserVersion,
				ip : ip_,
				city : city_
			},
			dataType : "json",
			success : function(data) {
				if (data.mta == 1) {
					tipMessage(kwy_name_);
					setTimeout(refLogin, 2866);
				} else if (data.mta == -1) {
					$.messager.alert('提示', data.msg, 'error');
				}
			}
		});

	}
}

function clearForm() {
	$('#ff').form('clear');
}

function refLogin() {
	window.location.href = "/login?bobo=" + new Date().getTime();
}

/*******************************************************************************
 * tip
 * 
 * @param userName
 */
function tipMessage(userName) {
	$.messager.show({
		title : '提示',
		msg : "恭喜:<font color='red'>" + userName
				+ "！！！</font>,注册成功,稍后跳转到登录页面...",
		showType : 'fade',
		style : {
			right : '',
			bottom : ''
		}
	});
}

function stop() {
	return false;
}

document.oncontextmenu = stop;