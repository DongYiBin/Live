<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String contextPath = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<jsp:include page="../common.jsp" flush="true" />
</head>
<body>
	<div style="width: 100%;margin-left: 2px;">
		<form id="search_form">
			<input type="hidden" id="pageNum" name="pageNum" />
			<input type="hidden" name="pageSize" value="10" />
			<div class="row">
				<!-- <div class="col-lg-2">
					<label>直播间:</label>
					<input type="text" name="title" class="form-control" placeholder="直播间名称(模糊查询)" />
				</div> -->
				<div class="col-lg-2">
					<label>用户昵称:</label>
					<input type="text" name="user.nickName" class="form-control" placeholder="用户昵称" />
				</div>
				<!-- <div class="col-lg-2">
					<label>状态:</label>
					<select class="form-control" name="state">
						<option value="">全部</option>
						<option value="0">已结束</option>
						<option value="1">直播中</option>
						<option value="100">回放</option>
						<option value="-1">初始化</option>
					</select>
				</div> -->
				<div class="col-lg-2">
					<br />
					<button type="submit" id="search_btn" class="btn btn-primary btn-lg" style="font-size: 9px">
						<span class="glyphicon glyphicon-search"></span> 搜索
					</button>
				</div>
			</div>
		</form>
	</div>
	<div style="width: 100%;margin-left: 2px;margin-top: 20px;height:430px;">
		<table class="table table-hover" id="data_table">
			<thead>
				<tr style="background-color: #E0E0E0">
					<th>用户ID</th>
					<th>用户名</th>
					<th>昵称</th>
					<th>账户类型</th>
					<th>手机</th>
					<th>创建时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
	<div id="laypage"></div>
</body>
<script>
$(function(){
	//页面初始化
	list(1,function(data){
		//分页
		groPage('laypage',data.data.pageTotal,5,function(curr){
			list(curr,function(data){});
		});
	});
	
	//搜索
	$('#search_form').on('submit',function(){
		list(1,function(data){
			//分页
			groPage('laypage',data.data.pageTotal,5,function(curr){
				list(curr,function(data){});
			});
		});
		return false;
	});
	
	//表格数据初始化
	function list(pageNum,callback){
		$('#pageNum').val(pageNum);
		var data = $('#search_form').serialize();
		data_ajax(
			{
				"url":'<%=contextPath%>/web/admin/findUserPageList',
				"data":data
			},
			function(rs){
				var _tbody = $('#data_table tbody');
				_tbody.empty();
				var html = '';
				for(var key in rs.data.pageData){
					var data = rs.data.pageData[key];
					html += "<tr>";
					html += "<td>" + NULL(data.userId) + "</td>";
					html += "<td>" + NULL(data.account) + "</td>";
					html += "<td>" + NULL(data.nickName) + "</td>";
					html += "<td>" + NULL(data.accountType) + "</td>";
					html += "<td>" + NULL(data.phone) + "</td>";
					html += "<td>" + NULL(data.createTime) + "</td>";
					if(NULL(data.state) == -1){
 						html += "<td><i>已禁用</i></td>";
						html += "<td>";
						html += '<a href="javascript:void(0)" class="btn btn-link btn-xs open" data_id="'+data.userId+'">启用</a>';
					}else{
						html += "<td>正常</td>";
						html += "<td>";
						html += '<a href="javascript:void(0)" class="btn btn-link btn-xs stop" data_id="'+data.userId+'">禁用</a>';
					}
					html += '<a href="javascript:void(0)" class="btn btn-link btn-xs delete" data_id="'+data.userId+'">删除</a>';
					html += "</td>";
					
					html += "</tr>";
				}
				_tbody.append(html);
				callback(rs);
			}
		);
	}
	
	// 删除
	$('#data_table').on('click','.delete',function(){
		var _self = $(this);
		layer.confirm('确定删除该账户?',
			{
			  btn: ['确定','取消'] //按钮
			}, function(){
				var id = _self.attr('data_id');
			  	$.post("<%= contextPath %>/web/admin/delaccount",{"userId":id},function(rs){
					if(rs.code == 1){
						layer.msg("已删除",{icon:6});
						$('#search_form').submit();
					} else {
						layer.msg("失败",{icon:5});
					}
				});
			});
		window.open("<%= contextPath %>/web/admin/delaccount?userId=" + id);
	});
	
	// 禁用
	$('#data_table').on('click','.stop',function(){
		var _self = $(this);
		layer.confirm('确定禁用该账户?',
			{
			  btn: ['确定','取消'] //按钮
			}, function(){
				var id = _self.attr('data_id');
			  	$.post("<%= contextPath %>/web/admin/disaccount",{"userId":id, "state" : -1},function(rs){
					if(rs.code == 1){
						layer.msg("已禁用",{icon:6});
						$('#search_form').submit();
					} else {
						layer.msg("失败",{icon:5});
					}
				});
		});
	});
	
	// 启用
	$('#data_table').on('click','.open',function(){
		var _self = $(this);
		layer.confirm('确定禁用该账户?',
			{
			  btn: ['确定','取消'] //按钮
			}, function(){
				var id = _self.attr('data_id');
			  	$.post("<%= contextPath %>/web/admin/disaccount",{"userId":id, "state" : 0},function(rs){
					if(rs.code == 1){
						layer.msg("已启用",{icon:6});
						$('#search_form').submit();
					} else {
						layer.msg("失败",{icon:5});
					}
				});
		});
	});
})
</script>
</html>