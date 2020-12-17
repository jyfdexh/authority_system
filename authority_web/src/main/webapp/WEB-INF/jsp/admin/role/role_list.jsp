<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>角色列表</title>
    <script type="text/javascript" src="${ctx}/static/js/hp_list.js"></script>
</head>
<body>
<form action="">
    <div class="demoTable">
        角色名：
        <div class="layui-inline">
            <input class="layui-input" name="role" id="roleName"
                   autocomplete="off">
        </div>
        <button class="layui-btn bt_search" data-type="reload123">搜索</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</form>
<div style="height: 10px;"></div>
<div style="" id="userToolBar">
    <button class="layui-btn bt_add" data="893px, 550px" data-url="${ctx}/role/toAddRole" data-event="add">新增</button>
    <button class="layui-btn layui-btn-warm bt_update" data="893px, 550px"
            data-url="${ctx}/role/toUpdateRole" data-event="update">修改
    </button>
    <button class="layui-btn layui-btn-danger bt_delete" data-url="${ctx}/role/deleteRole">删除</button>
    <button class="layui-btn layui-btn-normal bt_setMenu" data="893px, 550px" data-url="${ctx}/role/toSetMenu">分配权限
    </button>
</div>
<%--监听行工具事件 先准备2个按钮绑上lay-event事件 id="roleBar"--%>
<div id="roleBar" style="display:none;">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</div>

<table class="layui-hide" id="role" lay-data="{id: 'role'}" lay-filter="roleTable"></table>
<script>
    layui.use(['table', 'util'], function () {
        const table = layui.table;
        const util = layui.util;

        table.render({
            elem: '#role',
            url: '${ctx}/role/list',
            cellMinWidth: 80,
            toolbar: "#userToolBar",//表头工具条
            page: true,
            title: 'role表',
            limit: 2,
            limits: [1, 2, 3, 4, 5],
            skin: 'nob', //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
            even: true,    //隔行换色
            totalRow: true,//开启合并行
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": 0, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.total, //解析数据长度
                    "data": res.records //解析数据列表
                };
            },
            cols: [[
                {field: 'id', type: 'checkbox', width: 50, fixed: 'center'},
                // 监听行工具事件
                {fixed:'left',title: '操作',toolbar:'#roleBar', width: 150},
                {type: 'numbers'},
                {
                    field: 'id',
                    width: 200,
                    title: 'ID',
                    sort: true
                }, {
                    field: 'role',
                    title: '角色名'
                }, {
                    field: 'remark',
                    title: '备注'
                }, {
                    field: 'createTime',
                    title: '创建时间',
                    templet: function (res) {
                        if (res.createTime != null) {
                            return util.toDateString(res.createTime,
                                "yyyy-MM-dd HH:mm:ss");
                        } else {
                            return "";
                        }
                    }
                }, {
                    field: 'updateTime',
                    title: '更新时间',
                    templet: function (res) {
                        if (res.updateTime != null) {
                            return util.toDateString(res.updateTime,
                                "yyyy-MM-dd HH:mm:ss");
                        } else {
                            return "";
                        }
                    }
                }]]
        });

        //搜索条件
        let active123 = {
            reload123: function () {
                table.reload('role', {
                    where: {
                        role: $("#roleName").val()
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
        //监听

        //监听复选框
        table.on('checkbox(roleTable)', function(aa){
            console.log(aa.checked); //当前是否选中状态
            console.log(aa.data); //选中行的相关数据
            console.log(aa.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
        });
        //监听行单击事件 row即表示单击
        // table.on('row(roleTable)', function(obj){
        //     console.log(obj.tr) //得到当前行元素对象
        //     console.log(obj.data) //得到当前行数据
        //     // obj.del(); //删除当前行(仅暂时删除样式，数据库不变)
        //     // obj.update(fields) //修改当前行数据
        // });
        //监听行双击事件 rowDouble
        table.on('rowDouble(roleTable)', function(obj){
            //obj 同上
        });
        //监听行的工具条
        table.on('tool(roleTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            const data     = obj.data;      //获得当前行数据
            const layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            if(layEvent === 'del'){ //删除
                layer.msg("删除");
                console.log(data);
                layer.confirm('真的删除行么', function(index){
                    obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    console.log(index);
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        type: "POST",
                        url: "${ctx}/role/deleteRole",
                        contentType : "application/json",
                        dataType: 'json',
                        data: JSON.stringify(data),
                        // success: function(data){
                        //     if (data.result===true) {
                        //         layer.msg(data.msg, {icon: 1, time: 1500});
                        //         layui.table.reload($('table.layui-hide').attr("id"));
                        //     }else {
                        //         layer.msg(data.msg, {icon: 2, time: 1500});
                        //     }
                        // },
                        // error: function(res){
                        //     layer.msg("未知异常", {icon: 2});
                        // }
                    });
                });
            } else if(layEvent === 'edit'){ //编辑
                layer.msg("编辑");
                console.log(obj.data);
            }
        });
        // var checkStatus = table.checkStatus('roleTable'); //roleTable 即为基础参数 id 对应的值
        // console.log(checkStatus.data) //获取选中行的数据
        // console.log(checkStatus.data.length) //获取选中行数量，可作为是否有选中行的条件
        // console.log(checkStatus.isAll ) //表格是否全选
        // table.on('toolbar(roleTable)', function (obj) {
        //     switch (obj.event) {
        //         case 'add':
        //             layer.msg('新建添加add');
        //             console.log(obj.event);
        //             break;
        //         case 'update':
        //             layer.msg('更新修改update');
        //             console.log(obj.event);
        //
        //             break;
        //     }
        // });
    });
</script>
</body>
</html>
