<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户修改</title>
<%@ include file="/static/base/common.jspf"%>
<script type="text/javascript" src="${ctx}/static/js/hp_form.js"></script>
</head>
<body>
	<div class="body_main">
		<form class="layui-form layui-form-pane" action="${ctx}/user/updateUser">
			<input type="hidden" value="${userById.id}" name="id">
			<div class="layui-form-item">
				<label class="layui-form-label">昵称</label>
				<div class="layui-input-block">
					<input type="text" name="nickname" autocomplete="off" value="${userById.nickname}"
						placeholder="请输入昵称" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input type="text" readonly="readonly" name="userName" autocomplete="off" value="${userById.userName}"
						placeholder="请输入用户名" class="layui-input">
				</div>
			</div>
<%--            明文密码--%>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-inline">
					<input type="text" name="password" autocomplete="off" value="${userById.password}"
						placeholder="请输入密码" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux" style="color: #1E9FFF !important"></div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话</label>
				<div class="layui-input-block">
					<input type="tel" name="tel" lay-verify="required|phone" autocomplete="off"  value="${userById.tel}"
						placeholder="请输入电话" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<label>
						<select name="sex" lay-verify="required">
							<c:if test="${userById.sex == 1}">
								<option value="1" selected>男</option>
								<option value="-1">女</option>
							</c:if>
							<c:if test="${userById.sex == -1}">
								<option value="1">男</option>
								<option value="-1" selected>女</option>
							</c:if>
						</select>
					</label>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-block">
					<input type="text" name="email" autocomplete="off" lay-verify="email"  value="${userById.email}"
						placeholder="请输入邮箱" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">头像</label>
					<div class="layui-input-inline">
						<img  id="image2" style="width: 111px;height: 111px" src="http://192.168.179.128/${userById.userImg}">
					                                	<%--		 in Linux:    http://192.168.179.128:4545/${userById.userImg}--%>
					</div>

				</div>
			</div>
			<!--************这里是上传图片的代码***************-->
			<!--************这里添加的隐藏的输入框，用来传递images的参数***************-->
            <input type="hidden" name="userImg" class="userImg" value="${userById.userImg}">
            <div class="layui-form-item">
                <label class="layui-form-label ">改头</label>
                <div class="layui-upload">
                    <button type="button" class="layui-btn" id="test1">上传图片</button>
                    <div class="layui-upload-list">
                        <img class="layui-upload-img"  style="width: 111px;height: 111px" id="demo1">
                        <p id="demoText"></p>
                    </div>
                </div>
            </div>
			<!--************上面里是上传图片的代码***************-->
			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
					<label>
						<c:if test="${userById.status}=='on'">
							<input type="checkbox" name="status" lay-skin="switch" value="${userById.status}"
								   lay-filter="switchTest" lay-text="可用|禁用" checked>
						</c:if>
						<c:if test="${userById.status}!='on'">
							<input type="checkbox" name="status" lay-skin="switch" value="${userById.status}"
								   lay-filter="switchTest" lay-text="可用|禁用">
						</c:if>
					</label>
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
</html>
