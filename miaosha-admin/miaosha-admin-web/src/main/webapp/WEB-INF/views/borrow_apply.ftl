<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.js"></script>
		<style type="text/css">
			body {
				font: 14px/1.5 "Verdana", "微软雅黑", YaHei, tahoma, arial, Hiragino Sans GB, "宋体";
			}
			.el-borrow-form{
				position: relative;	
			}
			.el-borrow-form-tip{
				width: 300px;
				border-radius: 3px;
				border:1px solid #ddd;
				box-shadow: 1px 1px 20px #ddd;
				padding:20px 30px;
				position: absolute;
				top:10px;
				right: 100px;
			}
			.text-danger{text-align: center;}
		</style>
		<script type="text/javascript">
			jQuery.validator.addMethod("notGreatThan", function(value, element, param){
				var target=$(param);
				var me=$(element);
				if ( this.settings.onfocusout ) {
					target.unbind(".validate-notGreatThan").bind("blur.validate-notGreatThan", function() {
						me.valid();
					});
				}
				return parseFloat(me.val())<=parseFloat(target.val());
			});
			$(function(){
				$("#editForm").validate({
					rules : {
						bidRequestAmount:{
							required:true,
							number:true,
							min:${minBidRequestAmount},
							max:${account.remainBorrowLimit}
						},
						currentRate:{
							required:true,
							number:true,
							min:5,
							max:20
						},
						minBidAmount:{
							required:true,
							number:true,
							min:${minBidAmount},
							notGreatThan:"#bidRequestAmount"
						},
						title:"required"
					},
					messages: {
						bidRequestAmount:{
							required:"请填写借款金额",
							number:"借款金额为数字",
							min:"借款金额最小为{0}",
							max:"借款金额最大为{0}"
						},
						currentRate:{
							required:"请填写借款利息",
							number:"借款利息为数字",
							min:"最低借款利息为{0}%",
							max:"最大借款利息不能超过{0}%"
						},
						minBidAmount:{
							required:"请填写最小投标金额",
							number:"最小投标金额为数字",
							min:"最小投标金额必须大于{0}",
							notGreatThan:"最小投标金额不能超过借款金额"
						},
						title:"必须填写借款原因"
					},
					//自定义错误样式
					errorClass:"text-danger col-sm-6",
					//未通过验证,进行高亮处理或其他处理；
					highlight:function(input){
						$(input).closest(".form-group").addClass("has-error");
					},
					//通过验证,清除高亮效果或其他处理；
					unhighlight:function(input){
						$(input).closest(".form-group").removeClass("has-error");
					},
					//错误提示信息加载的位置
					errorPlacement:function(label, element){
						label.appendTo(element.closest(".form-group"));
					}
				});
			});
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		<#assign currentNav="borrow" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="el-tip-info">
				<h3>信用借款</h3>
				<p>
					<span class="text-info">可借金额：</span><span class="text-danger">${account.remainBorrowLimit}</span>
				</p>
			</div>

			<div class="page-header lead">
				借款信息
				<label class="label label-primary">信用标</label>
			</div>
			<form class="form-horizontal el-borrow-form" id="editForm" novalidate="novalidate" method="post" action="/borrow_apply.do">
				<div class="form-group">
					<label class="col-sm-3 control-label">
						借款金额
					</label>
					<div class="col-sm-3  input-group">
						<input class="form-control" name="bidRequestAmount" id="bidRequestAmount"/>
						<span class="input-group-addon">元</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">
						借款利息
					</label>
					<div class="col-sm-3  input-group">
						<input class="form-control" name="currentRate" />
						<span class="input-group-addon">%</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">
						借款期限
					</label>
					<div class="col-sm-3 input-group">
						<select class="form-control" name="monthes2Return">
							<option value="1">1</option>
							<option value="3">3</option>
							<option value="6">6</option>
							<option value="9">9</option>
							<option value="12">12</option>
						</select>
						<span class="input-group-addon">月</span>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-3 control-label">
						还款方式
					</label>
					<label class="radio-inline">
						<input type="radio" value="0" checked="checked" name="returnType" />
						按月分期
					</label>
					<label class="radio-inline">
						<input type="radio" value="1" name="returnType" />
						按月到期
					</label>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">
						最小投标
					</label>
					<div class="col-sm-3  input-group">
						<input class="form-control" name="minBidAmount" />
						<span class="input-group-addon">元</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">
						招标天数
					</label>
					<div class="col-sm-3 input-group">
						<select class="form-control" name="disableDays">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
						</select>
						<span class="input-group-addon">天</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">
						借款标题
					</label>
					<div class="col-sm-6 input-group">
						<input name="title" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">
						借款描述
					</label>
					<div class="col-sm-6  input-group">
						<textarea name="description" rows="4" class="form-control" style="resize: none;"></textarea>
					</div>
				</div>
				<div class="form-group">
					<button class="btn btn-primary col-lg-offset-3" type="submit" data-loading-text="提交">
						提交申请
					</button>
				</div>
				
				<div class="el-borrow-form-tip">
					<h4>相关费用</h4>
					<p>
						<span class="text-info">利息</span>
						<span class="el-borrow-cost" id="cost01">--</span>
					</p>
					<p>
						<span class="text-info">奖金</span>
						<span class="el-borrow-cost" id="cost02">--</span>
					</p>
					<p>
						<span class="text-info">管理费</span>
						<span class="el-borrow-cost" id="cost03">--</span>
					</p>
				</div>
			</form>
		</div>	
			
		<#include "common/footer-tpl.ftl" />			
	</body>
</html>