<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户列表</title>
    <script type="text/javascript" src="${ctx}/static/js/hp_list.js"></script>
</head>
<body>
<form action="">
    <div class="demoTable">
        用户名：
        <div class="layui-inline">
            <input class="layui-input" name="userName" id="userName"
                   autocomplete="off">
        </div>
        电话：
        <div class="layui-inline">
            <input class="layui-input" name="tel" id="tel"
                   autocomplete="off">
        </div>
        邮箱：
        <div class="layui-inline">
            <input class="layui-input" name="email" id="email"
                   autocomplete="off">
        </div>
        <button class="layui-btn bt_search" data-type="reload123">搜索</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</form>
<div style="height: 10px;"></div>
<%--<div style="display:none;" id="userToolBar">--%>
    <div style="            ;" id="userToolBar">

    <button class="layui-btn bt_add" data="893px, 550px" data-url="${ctx}/user/toUserAdd"><span
            class='iconfont icon-add'></span>&nbsp;新增
    </button>
    <button class="layui-btn layui-btn-warm bt_update" data="893px, 550px" data-url="${ctx}/user/toUserUpdate"><span
            class='iconfont icon-brush'></span>&nbsp;修改
    </button>
    <button class="layui-btn layui-btn-danger bt_delete" data-url="${ctx}/user/deleteUser"><span
            class='iconfont icon-delete'></span>&nbsp;删除
    </button>
    <button class="layui-btn layui-btn-normal bt_setRole" data="893px, 550px"
            data-url="${ctx}/user/toSetRole">
        <span class='iconfont icon-group'></span>&nbsp;分配角色
    </button>
</div>
<%--                                lay-data="            "></table>--%>
<table class="layui-hide" id="user" lay-data="{id: 'user'}"></table>
<script>
    layui.use(['table', 'util'], function () {
        const table = layui.table;
        const util = layui.util;
// 渲染数据表格
        table.render({
            elem: '#user'
            , height: 312
            // ,defaultToolbar:['filter', 'print', 'exports'] 表头工具条右边图标：默认筛选列，打印，导出
            ,limit:2
            , url: '${ctx}/user/list' //数据接口
            , parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": 0, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.total, //解析数据长度
                    "data": res.records //解析数据列表
                };
            }
            , page: true //开启分页
            , method: "post"
            , cols: [[ //表头
                {
                    field: 'id', type: 'checkbox', width: 50, fixed:
                        'center'
                }
                , {type: 'numbers'}
                , {
                    field: 'id', title: 'UUID', width: 222, sort: true,
                    align: 'center',unresize:true
                }
                , {field: 'userName', title: '用户名', width: 80}
                , {field: 'nickname', title: '昵称', width: 80}
                , {field: 'tel', title: '电话', width: 120}
                , {
                    field: 'sex', title: '性别', width: 80
                    , templet: function (res) {
                        if (res.sex === 1) {
                            return '男';
                        } else {
                            return '女';
                        }
                    }
                }
                , {field: 'email', title: '邮箱', width: 150}
                , {
                    field: 'status', title: '状态', width: 65
                    , templet: function (res) {
                        if (res.status === 'on') {
                            return '启用';
                        } else {
                            return '禁用';
                        }
                    }
                }
                , {field: 'userImg', title: '头像', width: 100}
                , {
                    field: 'createTime', title: '创建时间', width: 140
                    , templet: function (res) {
                        if (res.createTime != null) {
                            return util.toDateString(res.createTime,
                                "yyyy-MM-dd HH:mm:ss");
                        } else {
                            return "";
                        }
                    }
                }
                , {
                    field: 'updateTime', title: '更新时间', width: 140
                    , templet: function (res) {
                        if (res.updateTime != null) {
                            return util.toDateString(res.updateTime,
                                "yyyy-MM-dd HH:mm:ss");
                        } else {
                            return "";
                        }
                    }
                }
            ]]
        });

        //搜索条件
        let active123 = {
            reload123: function () {
                table.reload('user', {
                    where: {
                        userName: $('#userName').val(),
                        tel: $('#tel').val(),
                        email: $('#email').val()
                    }
                });
            }
        };

        //触发搜索条件事件
        $('.bt_search').on('click', function () {
            const type = $(this).attr('data-type');
            active123[type] ? active123[type].call(this) : '';
            return false;
        });

    });

</script>
</body>
</html>
