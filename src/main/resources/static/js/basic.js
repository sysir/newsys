layui.use('laydate', function () {
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#start' //指定元素
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#end' //指定元素
    });

});
layui.use('table', function () {
    var table = layui.table;
    //监听单元格编辑
    table.on('edit(test)', function (obj) {
        var value = obj.value //得到修改后的值
            ,
            data = obj.data //得到所在行所有键值
            ,
            field = obj.field; //得到字段
        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
    });
});

layui.use(['table', 'upload', 'layer', 'laydate', 'form'], function () {
    //实例化layui模块 table-->数据表格模块  $ 表示我们的jquery模块
    const table = layui.table
        , $ = layui.jquery,
        upload = layui.upload,
        laydate = layui.laydate,
        form = layui.form;

    table.render({
        elem: '#tablelist',
        id: 'tablelist',
        height: 'full-100',
        url: 'http://localhost:8080/main/getusers',
        title: '成员列表',
        even: true,
        limit: 20,
        page: true,
        cellMinWidth: 45,
        //开启工具栏
        toolbar: '#bar',
        parseData: function (res) {
            return {
                "code": res.status,
                "msg": res.message,
                "count": res.total,
                "data": res.item
            };
        },
        cols: [
            [
                {
                    type: 'checkbox',
                    fixed: 'left'
                }, {
                field: 'userId',
                title: '成员ID',
                sort: true
            }, {
                field: 'userName',
                title: '姓名<i class="layui-icon">&#xe642;</i>',
                edit: 'text',
            }, {
                field: 'userSex',
                title: '性别',
                align: 'center'
            }, {
                field: 'userBirthday',
                title: '用户生日',
                templet: function (d) {
                    return showTimeMin(d.userBirthday);
                }
            }, {
                field: 'userPhone',
                title: '联系方式<i class="layui-icon">&#xe642;</i>',
                edit: 'text',
            }, {
                field: 'userEmail',
                title: '邮箱<i class="layui-icon">&#xe642;</i>',
                edit: 'text',
            }, {
                field: 'userRegtime',
                title: '注册时间',
                sort: true,
                templet: function (d) {
                    return showTime(d.userRegtime);
                }
            }, {
                field: 'userStatus',
                title: '用户状态',
                templet: function (d) {
                    var stu = d.userStatus;
                    if (stu === 1) {
                        return "<span class='layui-btn layui-btn-xs layui-btn-radius  layui-btn-normal'>启用</span>"
                    } else {
                        return "<span class='layui-btn layui-btn-xs layui-btn-radius layui-btn-warm'>禁用</span>"
                    }
                }
            }, {
                fixed: 'right',
                title: '操作',
                align: 'center',
                toolbar: '#barDemo'

            }
            ]
        ]
    });
    table.on('toolbar(table)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data; //获取选中的数据
        var ids =new Array();
        for (var i in data) {
            ids[i] = data[i].userId;
        }
        if (obj.event === 'deleteids') {
            if (data.length === 0) {
                layer.msg('请选择要处理的数据');
            } else {
                layer.confirm("请慎重删除数据", function (index) {
                    $.ajax({
                        type: "post",
                        url: "http://localhost:8080/main/delByIds",
                        dataType: 'json',
                        data: {
                            "ids": ids
                        },
                        traditional: true, //向后台传送数组的时候,必须申明为true
                        success: function (re) {
                            if (re.status === 200) {
                                layer.msg(re.message, {icon: 1,time: 1000});
                                location.reload()
                            } else {
                                layer.msg(re.message, {icon: 5,time:1000});
                                location.reload()
                            }

                        }
                    })

                })

            }
        }
    });
    //监听单元格编辑
    table.on('edit(table)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        $.ajax({
            type: "post",
            url: "http://localhost:8080/main/update",
            dataType: "json",
            data: {
                "uid": data.userId,
                "field": field,
                "value": value
            },
            success: function (re) {
                layer.msg(re.message)
            }
        })

    });

    //监听行工具事件
    table.on('tool(table)', function (obj) {
        var data = obj.data
            , layEvent = obj.event;
        if (layEvent === 'edit') {
            layer.open({
                type: 2,
                title: '编辑成员信息',
                area: ['30%', '50%'],
                content: '/main/editUser?uid=' + data.userId
            })
        } else if (layEvent === 'delete') {
            layer.confirm('请确定删除当前数据', function (index) {
                $.ajax({
                    type: 'post',
                    url: "/main/delByUid",
                    dataType: "json",
                    data: {
                        "uid": data.userId
                    },
                    success: function (re) {
                        if (re.status === 200) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.msg(re.message, {icon: 1})
                        } else {
                            layer.msg(re.message, {icon: 5})
                        }
                    }
                })
            });
        }
    });

    //提交编译以后的表单
    $("#subedit").on("click", function () {
        var index;
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/main/edit',
            dataType: 'json',
            data: $("#form").serialize(),
            success: function (re) {
                if (re.status === 200) {
                    layer.msg(re.message, {icon: 6});
                } else {
                    layer.msg(re.message, {icon: 5});
                }
            }
        });
    });
    $("#exit").on("click", function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    });

    //包含下拉框的多条件查询
    $("#search").on("click", function () {
        table.reload('tablelist', {
            page: {
                curr: 1
            },
            url: "http://localhost:8080/main/search",
            where: {
                "uid": $("#userId").val(),
                "un": $("#userName").val(),
                "up": $("#userPhone").val(),
                "stu": $("#userStatus").val()
            }
        })

    });


    //头工具栏事件
    table.on('toolbar(tablelist)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选');
                break;
        }
    });
});


/*用户-删除*/
function member_del(obj, id) {
    layer.confirm('确认要删除吗？', function (index) {
        //发异步删除数据
        $(obj).parents("tr").remove();
        layer.msg('已删除!', {icon: 1, time: 1000});
    });
}


function delAll(argument) {
    var ids = [];

    // 获取选中的id
    $('tbody input').each(function (index, el) {
        if ($(this).prop('checked')) {
            ids.push($(this).val())
        }
    });

    layer.confirm('确认要删除吗？' + ids.toString(), function (index) {
        layer.msg('删除成功', {icon: 1});
        $(".layui-form-checked").not('.header').parents('tr').remove();
    });
}