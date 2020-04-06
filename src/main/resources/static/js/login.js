layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery;
    //登录操作
    form.on('submit(login)', function (data) {
        //向后台发送请求 AJAX
        $.ajax({
            type: "post",
            url: "/user/login",
            data: data.field,
            dataType: "json",
            success: function (re) {
                if (re.status === 200) {
                    layer.msg(re.message,
                        {
                            icon: 6, time: 1500, end: function () {
                                window.location.href = "http://localhost:8080/home/index"
                            }
                        },
                    )
                } else {
                    layer.alert(re.message, {icon: 2})
                }
            }
        });
        return false;
    });


    /*$("#getTelCode").on('click', function telCode() {
        var tel = document.getElementById("userPhone").value;
        var telCode = document.getElementById("telCode").value;
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/user/getTelCode?tel=' + tel,
            dataType: 'json',
            success: function (re) {
                console.log("111");
                if (re.status === 500) {
              /!*  else if (re.status===telCode) {
                    }*!/
                    layer.msg(re.message, {icon: 5, time: 1000});
                } else {
                    layer.msg(re.message, {icon: 6, time: 1000});
                }
                return re.status;
            }
        });
    });*/


    $("#getTelCode").on('click', function () {
        var tel = document.getElementById("userPhone").value;
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/user/getTelCode?tel=' + tel,
            dataType: 'json',
            success: function (re) {
                if (re.status === 500) {
                    layer.msg(re.message, {icon: 5, time: 1000});
                } else {
                    layer.msg(re.message, {icon: 6, time: 1000});
                }
            }
        });
    });

    $("#regUser").on('click', function () {
        $.ajax({
            type: "post",
            url: "/user/register",
            dataType: "json",
            data: $("#regform").serialize(),
            success: function (re) {
                if (re.status === 200) {
                    layer.msg(re.message,
                        {
                            icon: 6, time: 1500, end: function () {
                                window.location.href = "http://localhost:8080/sys/login";
                            }
                        },
                    )
                } else {
                    layer.msg(re.message, {icon: 5, time: 1000});
                }
            }
        });
        return false;
    });

});

function changeCode() {
    var img = document.getElementById("codeImg");
    img.src = "/sys/getCode?time=" + new Date().getTime();
}





