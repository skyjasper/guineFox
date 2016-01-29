$(function(){

    $("#valic").click(function() {
        $("#valic").attr("src", "/guinea/verify?sky=" + new Date().getTime());
    });

    $(".username").focus();

    $(".login-button").click(function(){
        var username = $(".username").val();
        var password = $(".password").val();
        var txtSN = $(".txtSN").val();
        if(username==null||username==""||username.size<1){
            alert("请输入用户名!");
        }else if(password==null||password==""||password.size<1){
            alert("请输入密码!");
        }else if(txtSN==null||txtSN==""||txtSN.size<1){
            alert("请输入验证码!");
        }else{
            $.ajax({
                type : "post",
                url : "/login",
                data : {
                    loginName : username,
                    password : password,
                    verifycode : txtSN
                },
                dataType : "json",
                success : function(data) {
                    if (data.mta == 1) {
                        window.location.href = "/main_index?mimi="
                            + new Date().getTime();
                    } else {
                        $("#valic").attr("src", "/guinea/verify?mini="+ new Date().getTime());
                         alert( data.msg);
                    }
                }
            });
        }
    });

    document.onkeydown = function(e){
        var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            var username = $(".username").val();
            var password = $(".password").val();
            var txtSN = $(".txtSN").val();
            if(username==null||username==""||username.size<1){
                 alert("请输入用户名!");
            }else if(password==null||password==""||password.size<1){
                 alert("请输入密码!");
            }else if(txtSN==null||txtSN==""||txtSN.size<1){
                 alert("请输入验证码!");
            }else{
                $.ajax({
                    type : "post",
                    url : "/login",
                    data : {
                        loginName : username,
                        password : password,
                        verifycode : txtSN
                    },
                    dataType : "json",
                    success : function(data) {
                        if (data.mta == 1) {
                            window.location.href = "/main_index?mimi="
                                + new Date().getTime();
                        } else {
                            $("#valic").attr(
                                "src", "/guinea/verify?mini="
                                + new Date().getTime());
                             alert( data.msg);
                        }
                    }
                });
            }
            return false;
        }
    }
});

function stop() {
    return false;
}
document.oncontextmenu = stop;




