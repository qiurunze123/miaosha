<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
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
					<#assign currentMenu = "realAuth" />
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<#if (auditing)??>
						<div class="el-tip-info">
							<h3>实名认证</h3>
							<p class="text-info">实名认证资料已经提交，等待业务员审核，请耐心等待；如果急需审核请联系客服；</p>
						</div>
					<#else>
						<div class="panel panel-default">
							<div class="panel-heading">
								实名认证
							</div>
							<div class="panel-body">
								<h4 class="text-success ">你已经通过实名认证</h4>
								<hr />
								<table style="width: 100%;height: 100px;">
									<tr>
										<td><span>真实姓名： ${realAuth.realname}</span></td>
										<td><span>性别：${realAuth.sexDisplay}</span></td>
									</tr>
									<tr>
										<td><span>证件号码： ${realAuth.idNumber}</span></td>
										<td><span>出生日期：${(realAuth.birthDate)!''}</span></td>
									</tr>
								</table>
							</div>
						</div>
					</#if>
				</div>
			</div>
		</div>		
		
		<#include "common/footer-tpl.ftl" />
	</body>
</html>