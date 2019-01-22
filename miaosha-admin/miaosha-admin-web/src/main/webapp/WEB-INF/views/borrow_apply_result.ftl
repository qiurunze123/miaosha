<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<#assign currentNav="borrow" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="el-tip-info">
				<h3>借款提示</h3>
				<p>你目前有标正在申请的流程当中，不能再次发标；或者你恶意的进入了发标流程，请按照正常流程完成发标前的相关资料提交，谢谢！</p>
			</div>
		</div>	
		
		<#include "common/footer-tpl.ftl" />
	</body>
</html>