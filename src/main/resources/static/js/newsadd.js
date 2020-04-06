layui.use(['table', 'upload', 'layer', 'laydate', 'form'], function () {
    //实例化layui模块 table-->数据表格模块  $ 表示我们的jquery模块
    const table = layui.table
        , $ = layui.jquery,
        upload = layui.upload,
        laydate = layui.laydate,
        form = layui.form,
        layer = layui.layer;

    //select联动
    form.on('select(pre)', function (data) {
        $.ajax({
            url: "http://localhost:8080/main/linkSort",
            dataType: 'json',
            data: {
                id: data.value
            },
            success: function (res) {
                $("#depid").empty();
                $.each(res, function (i, item) {
                    $("#depid").append(new Option(item.sortName, item.sortId));
                });
                form.render('select');
            }
        });
    });


    //上传封面
    upload.render({
        elem: '#uploadImg',
        url: 'http://localhost:8080/main/uploadImg',
        acceptMime: 'image/jpg, image/png',
        field: 'file',
        size: 2048,
        multiple: false,
        before: function (obj) {
            obj.preview(function (index, file, result) {
                $('#coverimg').attr('src', result);
            });
        },
        done: function (res) {
            layer.msg('上传成功');
            layui.$('#uploadDemoView').removeClass('layui-hide').find('img').attr('src', res.files);
            //console.log(res)
        }
    });


});