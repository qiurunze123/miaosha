<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
		<script type="text/javascript" src="/js/plugins-override.js"></script>
		<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#pagination").twbsPagination({
					totalPages:${pageResult.totalPage},
					visiblePages:5,
					startPage:${pageResult.currentPage},
					onPageClick:function(e,page){
						$("#currentPage").val(page);
						$("#searchForm").submit();
					}
				});
				
				$(".beginDate,.endDate").click(function(){
					WdatePicker({
						dateFmt:"yyyy-MM-dd"
					});
				});
				
				$("#query").click(function(){
					$("#currentPage").val(1);
					$("#searchForm").submit();
				})
			});
		</script>
	</head>
	<body>
	
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<#assign currentNav="account" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="iplog" />
					<#include "common/leftmenu-tpl.ftl" />		
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<form action="/iplog.do" name="searchForm" id="searchForm" class="form-inline" method="post">
						<input type="hidden" id="currentPage" name="currentPage" value="" />
						<div class="form-group">
							<label>时间范围</label>
							<input type="text" class="form-control beginDate" name="beginDate" value='${(qo.beginDate?string("yyyy-MM-dd"))!""}'/>
						</div>
						<div class="form-group">
							<label></label>
							<input type="text" class="form-control endDate" name="endDate" value='${(qo.endDate?string("yyyy-MM-dd"))!""}'/>
						</div>
						<div class="form-group">
						    <label>状态</label>
						    <select class="form-control" name="state">
						    	<option value="-1">全部</option>
						    	<option value="0">登录失败</option>
						    	<option value="1">登录成功</option>
						    </select>
						    <script type="text/javascript">
						    	$('[name=state] option[value=${(qo.state)!"-1"}]').attr("selected","selected");
						    </script>
						</div>
						<div class="form-group">
							<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
						</div>
					</form>
					
					<div class="panel panel-default" style="margin-top: 20px;">
						<div class="panel-heading">
							登录日志
						</div>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>用户</th>
									<th>登录时间</th>
									<th>登录ip</th>
									<th>登录状态</th>
								</tr>
							</thead>
							<tbody>
								<#list pageResult.result as data>
									<tr>
										<td>${data.username}</a></td>
										<td>${data.loginTime?string("yyyy-MM-dd HH:mm:SS")}</td>
								        <td>${data.ip}</td>
								        <td>${data.displayState}</td>
									</tr>
								</#list>
							</tbody>
						</table>
						<div style="text-align: center;">
							<ul id="pagination" class="pagination"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>		
						
		<#include "common/footer-tpl.ftl" />
	</body>
</html>