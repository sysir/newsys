layui.use(['table', 'upload', 'layer', 'laydate', 'form'], function () {
    //实例化layui模块 table-->数据表格模块  $ 表示我们的jquery模块
    const table = layui.table
        , $ = layui.jquery,
        upload = layui.upload,
        laydate = layui.laydate,
        form = layui.form;


    laydate.render({
        elem: '#timerange'
        , range: '~'
    });

    table.render({
        elem: '#tablelist',
        id: 'tablelist',
        //table表格的高度
        height: 'full-100',
        url: 'http://localhost:8080/main/getNews',
        title: '新闻列表',
        cellMinWidth: 45,
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
                    fixed: 'left'
                }, {
                field: 'newId',
                title: '新闻ID',
                width: 90,
                align: "center",
                sort: true
            }, {
                field: 'auther',
                title: '创建成员',
                width: 90,
                align: "center",
            }, {
                field: 'sortId',
                title: '分类',
                width: 80,
                align: "center",
            }, {
                field: 'newTitle',
                title: '标题',
            }, {
                field: 'content',
                title: '新闻内容',
                align: 'center'
            }, {
                field: 'visited',
                title: '点击次数',
                width: 105,
                align: "center",
                sort: true,
            }, {
                field: 'comment',
                title: '评论次数',
                width: 105,
                align: "center",
                sort: true,
            }, {
                field: 'pubdate',
                title: '发布日期',
                width: 105,
                align: "center",
                templet: function (d) {
                    return showTimeMin(d.pubdate);
                }
            }, {
                field: 'newStatus',
                title: '新闻状态',
                width: 120,
                templet: function (d) {
                    var stu = d.newStatus;
                    if (stu === 1) {
                        return "<span class='layui-btn layui-btn-xs layui-btn-radius  layui-btn-normal'>已审核</span>"
                    } else {
                        return "<span class='layui-btn layui-btn-xs layui-btn-radius layui-btn-warm '>待审核</span>"
                    }
                }
            }, {
                fixed: 'right',
                title: '操作',
                width: 160,
                align: 'center',
                toolbar: '#barDemo'

            }
            ]
        ]
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
                content: '/main/editNew?nid=' + data.newId
            })
        } else if (layEvent === 'delete') {
            layer.confirm('请确定删除当前数据', function (index) {
                $.ajax({
                    type: 'post',
                    url: "/main/delByNid",
                    dataType: "json",
                    data: {
                        "nid": data.newId
                    },
                    success: function (re) {
                        if (re.status === 200) {
                            obj.del(); //删除对应行（tr）的DOM结构
                            layer.msg(re.message, {icon: 1, time: 1000})
                        } else {
                            layer.msg(re.message, {icon: 5, time: 1000})
                        }
                    }
                })
            });
        }
    });

    table.on('toolbar(table)', function (obj) {
        const checkStatus = table.checkStatus(obj.config.id),
            data = checkStatus.data;
        const nids = new Array();
        for (const i in data) {
            nids[i] = data[i].newId;
        }
        if (obj.event === 'deleteids') {
            if (data.length === 0) {
                layer.msg('请选择要处理的数据');
            } else {
                layer.confirm("请慎重删除数据", function (index) {
                    $.ajax({
                        type: "post",
                        url: "http://localhost:8080/main/delByNids",
                        dataType: 'json',
                        data: {
                            "nids": nids
                        },
                        traditional: true,
                        success: function (re) {
                            if (re.status === 200) {
                                layer.msg(re.message, {icon: 1, time: 1000});
                                location.reload()
                            } else {
                                layer.msg(re.message, {icon: 5, time: 1000});
                                location.reload()
                            }

                        }
                    })

                })

            }
        }
    });


    //包含下拉框的多条件查询
    $("#search").on("click", function () {
        table.reload('tablelist', {
            page: {
                curr: 1
            },
            url: "http://localhost:8080/main/searchnews",
            where: {
                "title": $("#newTitle").val(),
                "auther": $("#auther").val(),
                "content": $("#content").val(),
                "stu": $("#newStatus").val()
            }
        })
    });
    //日期范围查询
    $("#serdate").on("click", function () {
        table.reload('tablelist', {
            page: {
                curr: 1
            },
            url: "http://localhost:8080/main/getNews",
            where: {//设置异步请求的参数
                "timerange": $("#timerange").val()
            }
        })
    });

});