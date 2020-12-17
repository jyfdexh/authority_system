<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>菜单列表</title>
	<style type="text/css">
		td .iconfont {
			font-size : 20px !important;
		}
	</style>
	<script type="text/javascript" src="${ctx}/static/js/hp_list.js"></script>
</head>
<body>
<div id="menuToolBar">
	<button class="layui-btn bt_add" data="893px, 550px" data-url="${ctx}/menu/toMenuAdd">新增</button>
	<button class="layui-btn layui-btn-warm bt_update" data="893px, 550px" data-url="${ctx}/menu/toMenuUpdate">修改</button>
	<button class="layui-btn layui-btn-danger bt_delete" data-url="${ctx}/menu/deleteRole">删除</button>
</div>

<table class="layui-hide" id="menu" lay-data="{id: 'menu'}"></table>
<script>
	layui.use(['table','util'], function() {
		var table = layui.table;
		const util=layui.util;

		table.render({
			elem : '#menu',
			url : '${ctx}/menu/list',
			cellMinWidth : 80,
			toolbar: "#menuToolBar",//表头工具条
			skin: 'nob', //表格风格 line （行边框风格）row （列边框风格）nob （无边框风格）
			even: true,    //隔行换色
			totalRow: true,//开启合并行
			cols : [ [ {
				type : 'checkbox'
			}, {
				field : 'menuName',
				title : '目录',
				width : 120,
				templet : function (data){
					if (data.menuType===1) {
						return data.menuName;
					}else if(data.menuType===2){
						return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|____";
					}else {
						return "";
					}
				}
			}, {
				field : 'menuName',
				title : '菜单',
				width : 120,
				templet : function (data){
					if (data.menuType===2) {
						return data.menuName;
					}else if (data.menuType===3){
						return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|____";
					}else {
						return "";
					}
				}
			}, {
				field : 'menuName',
				title : '按钮',
				width : 120,
				templet : function (data){
					if (data.menuType===3) {
						return data.menuName;
					}else {
						return "";
					}
				}
			}, {
				field : 'menuType',
				title : '菜单类型',
				width : 100,
				templet : function (data){
					if (data.menuType===1) {
						return "目录";
					}else if(data.menuType===2){
						return "菜单";
					}else if(data.menuType===3){
						return "按钮";
					}
				}
			}, {
				field : 'menuImg',
				title : '图标',
				width : 80,
				templet : function (data){
					return "<span class='iconfont "+data.menuImg+"'></span>";
				}
			}, {
				field : 'permiss',
				title : '权限标识'
			}, {
				field : 'url',
				title : '菜单地址'
			}, {
				field : 'functionImg',
				title : 'functionImg'
			}, {
				field : 'seq',
				title : '顺序',
				width : 60
			}, {
				field : 'createTime',
				title : '创建时间',templet:function (data) {
					if (data.createTime != null) {
						return util.toDateString(data.createTime, "yyyy-MM-dd HH:mm");
					} else {
						return "";
					}
				}
			}, {
				field : 'updateTime',
				title : '更新时间',templet:function (data) {
					if (data.updateTime != null) {
						return util.toDateString(data.updateTime, "yyyy-MM-dd HH:mm");
					} else {
						return "";
					}
				}
			}] ]
		});

		//搜索条件
		var $ = layui.$, active = {
			reload : function() {
				table.reload($('table').attr("id"), {
					where : {
						menuName : $('#menuName').val()
					}
				});
			}
		};
		//触发搜索条件事件
		$('.bt_search').on('click', function (e){
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
			return false;
		})

	});
</script>
</body>
</html>
