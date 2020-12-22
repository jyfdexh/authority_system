<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加</title>
<%@ include file="/static/base/common.jspf"%>
<script type="text/javascript" src="${ctx}/static/js/hp_form.js"></script>
</head>
<script>
	layui.use(['form'], function(){
		var form = layui.form
				,layer = layui.layer

		//自定义验证规则
		form.verify({
			password: [
				/^[\S]{6,12}$/
				,'密码必须6到12位，且不能出现空格'
			]
			,userName:[
				/^[\S]{2,}$/
				,'用户名长度必须大于2，且不能出现空格'
			]
		});
	});

</script>
<body>
	<div class="body_main">
		<form class="layui-form layui-form-pane" action="${ctx}/user/addUser">
			<div class="layui-form-item">
				<label class="layui-form-label">昵称</label>
				<div class="layui-input-block">
					<input type="text" name="nickname" autocomplete="off"
						   placeholder="请输入昵称" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input type="text" name="userName" lay-verify="userName"  autocomplete="off"
						   placeholder="请输入用户名" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="text" name="password" lay-verify="password"  autocomplete="off"
						   placeholder="请输入密码" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话</label>
				<div class="layui-input-block">
					<input type="tel" name="tel" lay-verify="required" autocomplete="off"
						   placeholder="请输入电话" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<select name="sex" lay-verify="required">
						<option value=""></option>
						<option value="1">男</option>
						<option value="-1">女</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-block">
					<input type="text" name="email"  autocomplete="off"
						   placeholder="请输入邮箱" class="layui-input">
				</div>
			</div>
			<!--************这里添加的隐藏的输入框，用来传递userImg的参数***************-->
			<input type="hidden" name="userImg" class="userImg">
			<div class="layui-form-item">
				<label class="layui-form-label ">头像</label>
				<div class="layui-upload">
					<button type="button" class="layui-btn" id="test1">上传图片</button>
					<div class="layui-upload-list">
						<img class="layui-upload-img"  style="width: 111px;height: 111px" id="demo1">
						<p id="demoText"></p>
					</div>
				</div>
			</div>
			<!--************上传图片***************-->

			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
					<input type="checkbox" checked="" name="status" lay-skin="switch"
						   lay-filter="switchTest" lay-text="可用|禁用">
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
	<script>
		layui.use(['upload','layer'], function(){
			let $ = layui.jquery
			let layer = layui.layer
			let upload = layui.upload;

			//普通图片上传
			const uploadInst = upload.render({
				elem: '#test1'//绑定button  <button type="button" class="layui-btn" id="test1">上传图片</button>
				, url: '/upload'//上传接口
				, accept: 'images'//允许上传的文件类型
				, size: 50000 //最大允许上传的文件大小, 0（即不限制 KB)
				, before: function (obj) {
					obj.preview(function (index, file, result) {
						$('#demo1').attr('src', result);
					});
				}
				, done: function (res) {//上传后的回调
					//如果上传失败
					if (res.code > 0) {
						return layer.msg('上传失败');
					}
					//上传成功
					const demoText = $('#demoText');
					demoText.html('<span style="color: #4cae4c;">上传成功</span>');

					const fileupload = $(".userImg");
					// fileupload.attr(“value”,res.data.src);
					// res.data.src是获取后端传过来的图片的url,之后给表单的隐藏图片输入框赋值即可。
					fileupload.attr("value", res.data.src);
					console.log(fileupload.attr("value"));
				}
				, error: function () {//请求异常回调
					//演示失败状态，并实现重传
					const demoText = $('#demoText');
					demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
					demoText.find('.demo-reload').on('click', function () {
						uploadInst.upload();
					});
				}
			});
		});
	</script>

</body>

</html>
