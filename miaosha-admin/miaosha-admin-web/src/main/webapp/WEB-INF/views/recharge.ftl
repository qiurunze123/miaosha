<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		
		<#include "common/links-tpl.ftl" />
		<link rel="stylesheet" href="/css/bank.css">
		<script type="text/javascript" src="/js/bank.js"></script>
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js" ></script>
		
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<style type="text/css">
			.el-recharge-table > tbody > tr > td{
				vertical-align: middle;
			}
			.el-recharge-form input{
				width: 280px;
			}
			.tab-content{
				border:1px solid #ddd;
				border-top:none;
				padding:0px 20px;
			}
		</style>
		
		<script type="text/javascript">
			$(function(){
				$("#rechargeForm").ajaxForm(function(data){
					if(data.success){
						$.messager.popup("线下充值记录已提交，等待平台人员审核");
						$("#rechargeForm").resetForm();
					}
				});
				
				$(".datepicker").click(function(){
					WdatePicker();
				});
			});
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<!-- 网页导航 -->
		<div class="navbar navbar-default el-navbar">
			<div class="container">
				<div class="navbar-header">
					<a href=""><img alt="Brand" src="/images/logo.png"></a>
					<span class="el-page-title">线下充值</span>
				</div>
			</div>
		</div>
		
		<div class="container">
			<div class="el-tip-info">
				<h3>账户充值</h3>
				<p>1. 由于银行系统的限制，充值功能建议使用 IE 内核（IE8及以上）的浏览器。</p>
				<p>2. 请注意：为打击信用卡套现行为，充值资金必须经过投资回款后方能允许提现。</p>
			</div>
			<div role="tabpanel">
			  <!-- Nav tabs -->
			  <ul class="nav nav-tabs" role="tablist">
			  	<li role="presentation" ><a href="#tab1" data-toggle="tab">线上充值（第三方平台）</a></li>
			  	<li role="presentation" class="active"><a href="#tab2" data-toggle="tab">线下充值</a></li>
			  </ul>
			
			  <!-- Tab panes -->
			  <div class="tab-content">
			    <div role="tabpanel" class="tab-pane active" id="tab2">
			    	<table class="table el-recharge-table">
			    		<thead>
			    			<tr>		
			    				<th>银行名称</th>
			    				<th>开户人姓名</th>
			    				<th>银行帐号</th>
			    				<th>开户行支行名称</th>
			    			</tr>
			    		</thead>
			    		<tbody>
			    			<#list banks as bank>
				    			<tr>
				    				<td>
				    					<div class="bank bank_${bank.bankname}"></div>
				    				</td>
				    				<td>${bank.accountname}</td>
				    				<td>${bank.banknumber}</td>
				    				<td>${bank.bankforkname}</td>
				    			</tr>
			    			</#list>
			    		</tbody>
			    	</table>
			    	<div>
			    		<p class="text-primary h4" style="padding: 0px;margin: 0px;;">第一步:</p><p class="text-primary">请选择往以上一个账号汇款或转账您所要充值的金额（若您是跨行转账，推荐选择建行）。注：请保存您的交易记录信息。</p>
			    		<p class="text-primary h4" style="padding: 0px;margin: 0px;;">第二步:</p><p class="text-primary">款或转账成功后，再选择你所汇款或转账的账号，并填写以下完整的信息后，提交您的线下充值申请。我们将会在下个工作日核对您的申请。</p>
			    		<form id="rechargeForm" class="form-horizontal el-recharge-form" action="/recharge_save.do" method="post">
			    			<div class="form-group">
			    				<label class="control-label col-lg-2">银行</label>
			    				<div class="col-sm-10">
			    					<select class="form-control" style="width: 480px;" name="bankInfo.id">
			    						<#list banks as bank>
				    						<option value="${bank.id}">
												<script>
													var str="("+SITE_BANK_TYPE_NAME_MAP["${bank.bankname}"]+")${bank.banknumber}${bank.bankforkname}";
													document.write(str);
												</script>
											</option>
				    					</#list>
			    					</select>
			    				</div>
			    			</div>
			    			<div class="form-group">
			    				<label class="control-label col-sm-2">交易号</label>
			    				<div class="col-sm-10">
			    					<input class="form-control" name="tradeCode" />
			    				</div>
			    			</div>
			    			<div class="form-group">
			    				<label class="control-label col-sm-2">充值时间</label>
			    				<div class="col-sm-10">
			    					<input class="form-control datepicker" name="tradeTime" />
			    				</div>
			    			</div>
			    			<div class="form-group">
			    				<label class="control-label col-sm-2">充值金额</label>
			    				<div class="col-sm-10">
			    					<input class="form-control" name="amount" />
			    				</div>
			    			</div>
			    			<div class="form-group">
			    				<label class="control-label col-sm-2">说明</label>
			    				<div class="col-sm-10">
			    					<textarea style="resize: none;" class="form-control" name="note"></textarea>
			    				</div>
			    			</div>
			    			<div class="form-group">
			    				<button type="submit" class="col-sm-offset-2 btn btn-primary">确认提交</button>
			    			</div>	
			    		</form>
			    	</div>
			    </div>
			     <div role="tabpanel" class="tab-pane " id="tab1">
			    	<p class="h3 text-danger text-center" style="margin-top:0px;padding-top:10px;">系统暂不支持，请采用线下充值</p>
			    </div>
			  </div>
			</div>
		</div>
		
		<#include "common/footer-tpl.ftl" />
	</body>
</html>