<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台->我要借款</title>
<#include "common/links-tpl.ftl" />
</head>
<body>
	<!-- 网页头信息 -->
	<#include "common/head-tpl.ftl" />
	
	<#assign currentNav="borrow" />
	<!-- 网页导航 -->
	<#include "common/navbar-tpl.ftl" />
	
	<!-- 网页内容 -->
	<div class="container el-borrow">
		<div class="row">
			<div class="el-borrow-item col-sm-4">
				<div class="el-borrow-item-title" style="background-color: #40d47e;">
					信用贷</div>
				<div class="el-borrow-item-content">
					<p>
						认证后可借金额 <i>¥ ${account.remainBorrowLimit}
					</p>
					<a href="#" class="text-primary">申请条件</a>
					<p class="help-block">仅限广州地区</p>
					<ul>
						<li>
							<#if !(userinfo.baseInfo)>
								<a href="/basicInfo.do">填写基本资料</a>
								<span class="glyphicon glyphicon-remove" style="color:red;"></span>
							<#else>填写基本资料<span class="glyphicon glyphicon-ok" style="color:green;"></span></#if>
						</li>
						<li>
							<#if !(userinfo.realAuth)>
								<a href="/realAuth.do">身份认证</a>
								<span class="glyphicon glyphicon-remove" style="color:red;"></span>
							<#else>身份认证<span class="glyphicon glyphicon-ok" style="color:green;"></span></#if>
						</li>
						<li>
							<#if (userinfo.authScore<creditBorrowScore) >
								<a href="/userFile.do">材料认证分数达到${creditBorrowScore}分</a>
								<span class="glyphicon glyphicon-remove" style="color:red;"></span>
							<#else>材料认证分数达到30分<span class="glyphicon glyphicon-ok" style="color:green;"></span></#if>
						</li>
						<li>视频认证
							<#if !(userinfo.vedioAuth)><span class="glyphicon glyphicon-remove" style="color:red;"></span>
							<#else><span class="glyphicon glyphicon-ok" style="color:green;"></span></#if>
						</li>
					</ul>
					<#if userinfo.baseInfo && userinfo.realAuth && (userinfo.authScore >= creditBorrowScore) && userinfo.vedioAuth>
						<a href="/borrowInfo.do" class="el-borrow-apply">
							申请贷款
						</a>
					<#else>
						<a href="#" class="el-borrow-apply">
							申请贷款
						</a>
					</#if>
				</div>
			</div>
			<div class="el-borrow-item col-sm-4">
				<div class="el-borrow-item-title">车易贷</div>
				<div class="el-borrow-item-content">
					<p>
						认证后可借金额 <i>¥ 10,000.00</i>
					</p>
					<a href="#" class="text-primary">申请条件</a>
					<p class="help-block">仅限广州地区</p>
					<ul>
						<li>填写基本资料</li>
						<li>身份认证</li>
						<li>材料认证分数达到30分</li>
						<li>提交车辆抵押相关资料</li>
						<li>视频认证</li>
					</ul>
					<a href="#" class="el-borrow-apply">
						申请贷款
					</a>
				</div>
			</div>
			<div class="el-borrow-item col-sm-4">
				<div class="el-borrow-item-title" style="background-color: #2ca2ee;">
					房易贷</div>
				<div class="el-borrow-item-content">
					<p>
						可借金额 <i>¥ 10,0000.00</i>
					</p>
					<a href="#" class="text-primary">申请条件</a>
					<p class="help-block">仅限广州地区</p>
					<ul>
						<li>填写基本资料</li>
						<li>身份认证</li>
						<li>材料认证分数达到50分</li>
						<li>提交房屋抵押相关资料</li>
						<li>视频认证</li>
					</ul>
					<a href="#" class="el-borrow-apply">
						申请贷款
					</a>
				</div>
			</div>
		</div>
	</div>

	<!-- 网页版权 -->
	<#include "common/footer-tpl.ftl" />
</body>
</html>