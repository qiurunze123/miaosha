<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台</title>
<#include "common/links-tpl.ftl" />
<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
<link type="text/css" rel="stylesheet" href="/css/account.css" />
<style type="text/css">
.el-form input,.el-form select{
	width: 280px;
}
</style>
<#if !haveProcessing && bankInfo??>
<script type="text/javascript">
	$(function() {
		$("#editForm").validate({
			rules:{
				moneyAmount:{
					required:true,
					number:true,
					max:${account.usableAmount},
					min:500
				}
			},
			messages:{
				moneyAmount:{
					required:"输入提现金额",
					number:"输入正确的提现金额",
					max:"提现金额不能超过{0}",
					min:"提现金额必须大于{0}"
				}
			},
			//自定义错误样式
			errorClass:"text-danger",
			//未通过验证,进行高亮处理或其他处理；
			highlight:function(input){
				$(input).closest(".form-group").addClass("has-error");
			},
			//通过验证,清除高亮效果或其他处理；
			unhighlight:function(input){
				$(input).closest(".form-group").removeClass("has-error");
			},
			//验证成功后，提交操作；
			submitHandler:function(form){
				$("#editForm").ajaxSubmit(function(data){
					if (data.success) {
						$.messager.popup("提现申请已提交，等待平台人员审核");
						$("#editForm")[0].reset();
					}else{
						$.messager.popup(data.msg);
					}
				});
			}
		});
	});
</script>
</#if>
</head>
<body>
	<!-- 网页顶部导航 -->
	<#include "common/head-tpl.ftl"/>
	<#assign currentNav="withdraw" />
	<#include "common/navbar-tpl.ftl" />
	
	<div class="container">
		<div class="row">
			<!--导航菜单-->
			<div class="col-sm-3">
				<#assign currentMenu="moneyWithdraw"/>
				<#include "common/leftmenu-tpl.ftl" />
			</div>
			<!-- 功能页面 -->
			<div class="col-sm-9">
				<div class="el-tip-info">
					<h3>账户提现</h3>
					<p>1. 本平台工作日会处理当天 17:00 之前的提款申请。</p>
					<p>2. 为了确保银行转账成功，请您确认银行账号信息的 正确性。</p>
					<p>3. 单笔提现范围-普通用户500.00 元 ~ 500000.00 元。</p>
					<p>4. 单笔提现范围-VIP用户500.00 元 ~ 500000.00 元。</p>
					<p>5. 目前提现服务费：1万元(含) 之内：2.00 元/笔；1万元（以上） ：5.00 元/笔。</p>
					<p>6. 为避免信用卡套现，故充值15日内未投资提现按提现金额的0.4%收取提现费用。</p>
					<p>7. 本次可提现 = 可用余额 - 净值保证金 - 不可提现金额 + 投标冻结 - 最高服务费 - 返还体验金</p>
				</div>
				<#if !haveProcessing>
				<div style="margin: 10px 2px;" class="row h4">
					<div class="col-sm-4">
						可提现金额：<span class="text-primary">${account.usableAmount} 元</span>	
					</div>
					<div class="col-sm-4">
						冻结金额：<span class="text-primary">${account.freezedAmount} 元</span>
					</div>
				</div>
				</#if>
				<div>
					<#if haveProcessing>
						<div class="el-tip-info">
							<p class="text-info">您有提现申请正在处理中，请完这个提现申请完成后再次申请；</p>
						</div>
					<#elseif bankInfo??>
					<form id="editForm" class="form-horizontal el-form" action="/moneyWithdraw_apply.do" method="post">
						<div class="form-group">
							<label class="control-label col-sm-2">开户行</label>
							<div class="col-sm-10">
								<label class="form-control">${bankInfo.bankType.bankName}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">开户行</label>
							<div class="col-sm-10">
								<label class="form-control">${bankInfo.bankName}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">银行卡号</label>
							<div class="col-sm-10">
								<label class="form-control">${bankInfo.anonymousAccountNumber}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">户主姓名</label>
							<div class="col-sm-10">
								<label class="form-control">${userinfo.anonymousRealName}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">提现金额</label>
							<div class="col-sm-10">
								<input class="form-control" name="moneyAmount" />
							</div>
						</div>
						<div class="form-group">
							<button type="submit" class="col-sm-offset-2 btn btn-primary">确认提交</button>
						</div>
					</form>
					<#else>
						<div class="el-tip-info">
							<p class="text-info">请先绑定银行卡，再申请提现；</p>
						</div>
					</#if>
				</div>
			</div>
		</div>
	</div>		
	<#include "common/footer-tpl.ftl" />		
</body>
</html>