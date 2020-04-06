layui.use(['table', 'upload', 'layer', 'form'], function () {
    const table = layui.table
        , $ = layui.jquery,
        upload = layui.upload,
        form = layui.form;


    table.render({
        elem: '#tablelist',
        id: 'tablelist',
        //table表格的高度
        height: 'full-100',
        url: 'http://localhost:8080/main/allSort',
        title: '新闻列表',
        cellMinWidth: 80,
        even: true,
        //开启分页
        page: true,
        limits: [15, 30, 45, 60, 75, 90],
        limit: 15,
        toolbar: '#bar',
        parseData: function (res) { //res 即为原始返回的数据
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
                    fixed: 'left',
                }, {
                field: 'sortId',
                title: '分类ID',
                align: "center",
                width:100,
                sort: true
            }, {
                field: 'sortName',
                title: '分类名',
                edit:true,
                align: "center",
            }, {
                field: 'sortFid',
                title: '从属分类',
                width:100,
                align: "center",
            }, {
                field: 'sortStatus',
                title: '当前状态',
                width: 150,
                align: "center",
                templet: function (d) {
                    var stu = d.sortStatus;
                    if (stu === 1) {
                        return "<span class='layui-btn layui-btn-xs layui-btn-radius  layui-btn-normal'>启用</span>"
                    } else {
                        return "<span class='layui-btn layui-btn-xs layui-btn-radius layui-btn-warm '>禁用</span>"
                    }
                }
            }, {
                fixed: 'right',
                title: '操作',
                width: 100,
                align: 'center',
                toolbar: '#barDemo'

            }
            ]
        ]
    });
    table.on('tool(table)', function (obj) {
        var data = obj.data
            , layEvent = obj.event;
        if (layEvent === 'delete') {
            layer.confirm('请确定删除当前数据', function (index) {
                $.ajax({
                    type: 'post',
                    url: "/main/delBySid",
                    dataType: "json",
                    data: {
                        "sid": data.sortId
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

    //包含下拉框的多条件查询
    $("#search").on("click", function () {
        console.log($("#searchform").serialize());
        table.reload('tablelist', {
            page: {
                curr: 1
            },
            url: "http://localhost:8080/main/searchSort",
            where: {
                "sid": $("#sortId").val(),
                "sname": $("#sortName").val(),
                "stu": $("#sortStatus").val(),
            }
        })
    });

    table.on('toolbar(table)', function (obj) {
        var data = obj.data
            , layEvent = obj.event;
        if (layEvent === 'add') {
            layer.open({
                type: 2,
                title: '新增分类',
                area: ['30%', '35%'],
                resize:false,
                content: '/main/toAddSort',
            })

        }
    });
    //提交编译以后的表单
    $("#addSort").on("click", function () {
        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/main/addSort',
            dataType: 'json',
            data: {
                "sid": $("#sid").val(),
                "sname": $("#sname").val(),
            },
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

});