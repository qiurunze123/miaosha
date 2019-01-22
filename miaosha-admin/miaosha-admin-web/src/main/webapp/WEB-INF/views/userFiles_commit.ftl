<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<#include "common/loadSystemDictionary-macro.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/uploadify/jquery.uploadify.min.js"></script>
		<style type="text/css">
			.uploadify{
				height: 20px;
				font-size:13px;
				line-height:20px;
				text-align:center;
				position: relative;
				margin-left:auto;
				margin-right:auto;
				cursor:pointer;
				background-color: #337ab7;
			    border-color: #2e6da4;
			    color: #fff;
			}
		</style>
		<script type="text/javascript">
		$(function(){
			$("#submitFileType").click(function(){
				$("#editForm").submit();
			})
		})
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl"/>
		<#assign currentNav="account" />
		<#include "common/navbar-tpl.ftl" />

		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="userFile"/>
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
					<div class="panel panel-default">
						<div class="panel-heading">
							用户认证文件信息
						</div>
					</div>
					<div class="row">
					  <form method="POST" action="/userFile_selectType.do" id="editForm">
					  <#list userFiles as file>
					  <div class="col-sm-6 col-md-4">
					    <div class="thumbnail">
					      <img src="${file.file}" />
					      <div class="caption">
					        <p>
					        	<input type="hidden" name="id" value="${file.id}" />
					        	<select class="form-control" name="fileType" style="width: 180px" autocomplate="off">
									<@loadDictionary sn="userFileType" />
								</select>
					        </p>
					      </div>
					    </div>
					  </div>
					  </#list>
					  </form>
					</div>
					<div class="row">
						<a href="javascript:;" id="submitFileType" class="btn btn-success">确定提交</a>
					</div>
				</div>
			</div>
		</div>		
		<#include "common/footer-tpl.ftl" />		
	</body>
</html>