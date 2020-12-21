<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单添加</title>
<%@ include file="/static/base/common.jspf"%>
<style type="text/css">
	.iconfont {
	    font-size : 20px !important;
	}
</style>
<script type="text/javascript" src="${ctx}/static/js/hp_form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/menu.js"></script>
	<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
</head>
<body>
	<div class="body_main">
		<form class="layui-form layui-form-pane" action="${ctx}/menu/addMenu">
			<div class="layui-form-item">
				<label class="layui-form-label">父级菜单</label>
				<div class="layui-input-block">
					<label>
						<select name="pid" id="pmenuId" lay-verify="required" onclick="getType()">
							<option value="0">顶级目录</option>
							<c:forEach items="${menuList}" var="menu">
								<option value="${menu.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${menu.menuName}</option>
								<c:forEach items="${menu.nodes}" var="menu2">
									<option value="${menu2.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${menu2.menuName }</option>
								</c:forEach>
							</c:forEach>
						</select>
					</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单名称</label>
				<div class="layui-input-block">
					<input type="text" name="menuName" autocomplete="off"
						placeholder="请输入菜单名称" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单类型</label>
				<div class="layui-input-inline">
					<input type="text" name="menuType" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单图标</label>
				<div class="layui-input-inline">
					<input type="text" name="menuImg" autocomplete="off"
						placeholder="请输入菜单图标" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux"></div>
			</div>
			<div class="layui-form-item icon-div" style="display: none;">
	<%--			font-class 引用
				font-class 是 Unicode 使用方式的一种变种，主要是解决 Unicode 书写不直观，语意不明确的问题。

				与 Unicode 使用方式相比，具有如下特点：

				兼容性良好，支持 IE8+，及所有现代浏览器。
				相比于 Unicode 语意明确，书写更直观。可以很容易分辨这个 icon 是什么。
				因为使用 class 来定义图标，所以当要替换图标时，只需要修改 class 里面的 Unicode 引用。
				不过因为本质上还是使用的字体，所以多色图标还是不支持的。
				使用步骤如下：

				第一步：引入项目下面生成的 fontclass 代码：
				<link rel="stylesheet" href="./iconfont.css">
				第二步：挑选相应图标并获取类名，应用于页面：
				<span class="iconfont icon--xxx"></span>--%>
				<div class="layui-input-block">
					<c:forEach var="icon" items="${allIconFont}">
						<input type="radio" name="icon" lay-filter="icon" value="${icon}" title="<span class='iconfont ${icon}'></span>">
					</c:forEach>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">跳转地址</label>
				<div class="layui-input-block">
					<input type="text" name="url" autocomplete="off"
						placeholder="请输入跳转地址" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">回调方法</label>
				<div class="layui-input-block">
					<input type="text" name="function" autocomplete="off"
						placeholder="请输入回调方法" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">顺序</label>
				<div class="layui-input-block">
					<input type="text" name="seq" autocomplete="off"
						placeholder="请输入顺序" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">权限标识符</label>
				<div class="layui-input-block">
					<input type="text" name="permiss" autocomplete="off"
						placeholder="请输入权限标识符" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
</body>
<script>
	function getType() {
		alert("dianle");
		//定位到下拉列表,获取下拉框的值
		var index = this.selectedIndex;
		//获取下拉框值
		var pmenuId = this.options[index].innerHTML;

			//由于每次都会自动添加，因此每次在调用的时候清楚
			var type =$("#type");
			$.ajax({
				type:"POST",
				dataType:"json",
				url:"${ctx}/menu/getType",
				data:{"pmenuId":pmenuId},
				success:function (data) {

					var data1 = JSON.stringify(data);//将返回的json数据转换成String
						//动态创建option控件
						var option = document.createElement("option");
						//给<option> value 赋值
						option.innerHTML = data1;
						//appendChild()方法向节点添加一个子节点,就出现后面的下拉选项
						// 比如广东的后面一次添加广州、深圳、中山
						type.appendChild(option);
					}

			});

	}
</script>

</html>