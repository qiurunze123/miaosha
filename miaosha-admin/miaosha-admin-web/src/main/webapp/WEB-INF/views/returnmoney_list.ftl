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
		<link rel="stylesheet" href="/css/bank.css">
		<script type="text/javascript" src="/js/bank.js"></script>
		<script type="text/javascript">
			$(function(){
				$.extend($.fn.twbsPagination.defaults,{
					first:"首页",
				    prev:"上一页",
				    next:"下一页",
				    last:"最后一页"
				});
				
				$('#pagination').twbsPagination({
					totalPages : ${pageResult.totalPage}||1,
					currentPage : ${pageResult.currentPage},
					visiblePages : 5,
					onPageClick : function(event, page) {
						$("#currentPage").val(page);
						$("#searchForm").submit();
					}
				});
				
				$("#query").click(function(){
					$("#currentPage").val(1);
					$("#searchForm").submit();
				});
				
				$(".beginDate,.endDate").click(function(){
					WdatePicker();
				});
				
				$(".return_money").click(function(){
					var useableAmount=parseFloat($("#useableAmount").val());
					var returnMoney=parseFloat($(this).data("returnmoney"));
					if(useableAmount>=returnMoney){
						$.ajax({
							dataType:"json",
							type:"POST",
							url:"/returnMoney.do",
							data:{id:$(this).data("rid")},
							success:function(data){
								if(data.success){
									$.messager.confirm("提示","还款成功",function(){
										window.location.reload();
									});
								}else{
									$.messager.popup(data.msg);
								}
							}
						});
					}
				});
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
					<#assign currentMenu="recharge" />
					<#include "common/leftmenu-tpl.ftl" />		
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<form action="/recharge_list.do" name="searchForm" id="searchForm" class="form-inline">
						<input type="hidden" id="currentPage" name="currentPage" value="" />
						<div class="form-group">
							<label>时间范围</label>
							<input type="text" class="form-control beginDate" name="beginDate" value="${(qo.beginDate?string('yyyy-MM-dd'))!''}"/>
						</div>
						<div class="form-group">
							<label></label>
							<input type="text" class="form-control endDate" name="endDate" value="${(qo.endDate?string('yyyy-MM-dd'))!''}"/>
						</div>
						<div class="form-group">
						    <label>状态</label>
						    <select class="form-control" name="state">
						    	<option value="-1">全部</option>
						    	<option value="0">待还款</option>
						    	<option value="1">已还款</option>
						    	<option value="2">逾期</option>
						    </select>
						    <script type="text/javascript">
						    	$("[name=state] option[value='${(qo.state)!''}']").attr("selected","selected");
						    </script>
						</div>
						<div class="form-group">
							<button id="query" class="btn btn-success"><i class="icon-search"></i> 查询</button>
						</div>
					</form>
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="pull-left" style="line-height: 35px;">还款明细</span>
							<a class="pull-right btn btn-danger btn-sm" href="/recharge.do">
								账户充值
							</a>
							<div class="clearfix"></div>
						</div>
						<input type="hidden" id="useableAmount" value="${account.usableAmount?string('0.##')}">
						<table class="table">
							<thead>
								<tr>
									<th>借款</th>
									<th>还款金额</th>
									<th>还款本金</th>
									<th>还款利息</th>
									<th>还款期数</th>
									<th>还款期限</th>
									<th>还款状态</th>
								</tr>
							</thead>
							<tbody>
								<#list pageResult.listData as data>
									<tr>
										<td><a href="/borrow_info.do?id=${data.bidRequestId}">${data.bidRequestTitle}</a></td>
										<td>${data.totalAmount}元</td>
										<td>${data.principal}元</td>
										<td>${data.interest}元</td>
										<td>${data.monthIndex}期</td>
										<td>${data.deadLine?string("yyyy-MM-dd")}</td>
								        <td>
								        	<#if data.state=0>
								        		<a class="return_money" data-returnmoney="${data.totalAmount?string('0.##')}" data-rid="${data.id}">立刻还款</a>
								        	<#else>
								        		${data.stateDisplay}
								        	</#if>
								        </td>
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