// <div style="display:none;" id="userToolBar">
// 	<button class="layui-btn bt_add" data="893px, 550px" data-url="${ctx}/user/toUserAdd"><span
// class='iconfont icon-add'></span>&nbsp;新增
// 	</button>
// 	<button class="layui-btn layui-btn-warm bt_update" data="893px, 550px" data-url="${ctx}/user/toUserUpdate"><span
// class='iconfont icon-brush'></span>&nbsp;修改
// 	</button>
// 	<button class="layui-btn layui-btn-danger bt_delete" data-url="${ctx}/user/deleteUser"><span
// class='iconfont icon-delete'></span>&nbsp;删除
// 	</button>
// 	<button class="layui-btn layui-btn-normal bt_setRole" data="893px, 550px" data-url="user/user_setRole.jsp"><span
// class='iconfont icon-group'></span>&nbsp;分配角色
// 	</button>
// 	</div>
$(function() {
	layui.use('table', function() {
		
		//新增
		$('.bt_add').on('click', function() {
			var d = $(this).attr("data");//data="893px, 550px"
			layer.open({
				type : 2,
				title : '新增',
				shadeClose : true,
				area : [ d.split(",")[0], d.split(",")[1]],// [0]893px  [1]550px
				content : $(this).attr("data-url")//data-url="${ctx}/user/toUserAdd"
			});
		});
		
		//修改
		$('.bt_update').on('click', function() {
			var checkStatus = layui.table.checkStatus($('table').attr("id"));
			if (checkStatus.data.length<1) {
				layer.msg("至少选择一行数据", {icon: 2, time: 1500});
				return;
			}
			if (checkStatus.data.length>1) {
				layer.msg("至多只能选择一行数据", {icon: 2, time: 1500});
				return;
			}
			var id = checkStatus.data[0].id;
			
			var d = $(this).attr("data");
			layer.open({
				type : 2,
				title : '修改',
				shadeClose : true,
				area : [ d.split(",")[0], d.split(",")[1]],
				content : $(this).attr("data-url")+"?id="+id//data-url="${ctx}/user/toUserUpdate"
			});
		});
		
		//删除
		$('.bt_delete').on('click', function() {
			console.log("ok");
			var checkStatus = layui.table.checkStatus($('table').attr("id"));
			if (checkStatus.data.length<1) {
				layer.msg("至少选择一行数据", {icon: 2, time: 1500});
				return;
			}
			
			$.ajax({
			    type: "POST",
			    url: $(this).attr("data-url"),
			    contentType : "application/json",
			    dataType: 'json',  
			    data: JSON.stringify(checkStatus.data),  
			    success: function(data){
			    	if (data.result===true) {
			    		layer.msg(data.msg, {icon: 1, time: 1500});
			    		layui.table.reload($('table.layui-hide').attr("id"));
			    	}else {
			    		layer.msg(data.msg, {icon: 2, time: 1500});
			    	}
			    },  
			    error: function(res){
			    	layer.msg("未知异常", {icon: 2});
			    }  
			});
		});
		
		//分配角色
		$('.bt_setRole').on('click', function() {
			console.log("ok");
			var checkStatus = layui.table.checkStatus($('table').attr("id"));
			if (checkStatus.data.length<1) {
				layer.msg("至少选择一行数据", {icon: 2, time: 1500});
				return;
			}
			if (checkStatus.data.length>1) {
				layer.msg("至多只能选择一行数据", {icon: 2, time: 1500});
				return;
			}
			var id = checkStatus.data[0].id;
			
			var d = $(this).attr("data");
			layer.open({
				type : 2,
				title : '设置角色',
				shadeClose : true,
				area : [ d.split(",")[0], d.split(",")[1]],
				content : $(this).attr("data-url")+"?id="+id
			});
		});
		
		//分配权限
		$('.bt_setMenu').on('click', function() {
			console.log("ok");
			var checkStatus = layui.table.checkStatus($('table').attr("id"));
			if (checkStatus.data.length<1) {
				layer.msg("至少选择一行数据", {icon: 2, time: 1500});
				return;
			}
			if (checkStatus.data.length>1) {
				layer.msg("至多只能选择一行数据", {icon: 2, time: 1500});
				return;
			}
			var id = checkStatus.data[0].id;
			
			var d = $(this).attr("data");
			layer.open({
				type : 2,
				title : '设置权限',
				shadeClose : true,
				area : [ d.split(",")[0], d.split(",")[1]],
				content : $(this).attr("data-url")+"?id="+id
			});
		});
		
	})
	
})

//回调方法
function closeLayer(msg) {
	layui.table.reload($('table.layui-hide').attr("id"));
	layer.msg(msg, {icon: 1, time: 1500});
	layer.closeAll('iframe');
}