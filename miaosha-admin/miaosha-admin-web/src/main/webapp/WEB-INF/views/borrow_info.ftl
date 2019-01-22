<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link rel="stylesheet" type="text/css" href="/js/plugins/flipcountdown/jquery.flipcountdown.css" />
		<script type="text/javascript" src="/js/plugins/flipcountdown/jquery.flipcountdown.js"></script>
		
		<style type="text/css">
			.el-userhead{
				width: 100px;
				height: 100px;
				display: block;
				margin: 0px auto;
				
			}
			.muted{
				color: #999;
			}
			.text-info{
				color: #09d;
			}
		</style>
		
		<script type="text/javascript">
			$(function(){
				$("#retroclockbox").flipcountdown({
					size:'xs',
					beforeDateTime:"${bidRequest.disableDate?string('MM/dd/yyyy HH:mm:ss')}"
				});
				
				$("#bidBtn").on("click",function(){
					var amount = parseFloat($("#amount").val());
					if(!amount){
						$.messager.popup("请输入投资金额");
						return;
					}
					if(parseFloat($("#usableAmount").val())<amount){
						$.messager.popup("投资金额已超过账户余额");
						return;
					}
					if(parseFloat($("#minBidAmount").val())>amount){
						$.messager.popup("投资金额低于最小投标金额");
						return;
					}
					if(parseFloat($("#maxBidAmount").val())<amount){
						$.messager.popup("投资金额已超过借款标金额");
						return;
					}
					$.post("/borrow_bid.do",{
						bidRequestId : ${bidRequest.id},
						amount:amount
					},function(data){
						if(data.success){
							$.messager.popup("恭喜你,投标成功");
							location.reload();
						}else{
							$.messager.popup(data.message);
						}
					});
				});
			});
		</script>
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<#assign currentNav = "invest" />
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container" style="margin-top: 10px;">
			
			<div class="row">
				<div class="col-sm-3">
					<div class="panel panel-default">
						<div class="panel-heading">
							借款人
						</div>
						<div class="panel-body">
							<img class="el-userhead" src="/images/person_icon.png" />
							<p class="text-center">
								<a class="text-info" href="#">${bidRequest.createUser.username}</a>
							</p><br />
							<div>
							 	籍贯： 四川 - 成都
							 </div>
							 <div>
							 	认证信息：
							 		<label class="label label-success">
						 			<#if userInfo.realAuth>
							 			<span class="glyphicon glyphicon-user"></span>
							 		</#if>
							 		<#if userInfo.vedioAuth>
							 			<span class="glyphicon glyphicon-eye-open"></span>
							 		</#if>
							 		</label>
							 </div>
						</div>
					</div>	
				</div>
				
				<div class="col-sm-6">
					<h3 class="text-info" style="margin-top: 0px;">
						${bidRequest.title}借款
						<small>&emsp;<label class="label label-primary">信</label></small>
					</h3>
					<div>
						<table width="100%" height="250px">
							<tr>
								<td class="muted" width="80px">借款金额</td>
								<td class="text-info" width="120px" style="padding-left: 10px;">
									${bidRequest.bidRequestAmount}
								</td>
								<td class="muted" width="80px">年化利率</td>
								<td class="text-info" style="padding-left: 10px;">
									${bidRequest.currentRate}%
								</td>
							</tr>
							<tr>
								<td class="muted ">借款期限</td>
								<td class="text-info" style="padding-left: 10px;">
									${bidRequest.monthes2Return}月
								</td>
								<td class="muted">总可得利息</td>
								<td class="text-info" style="padding-left: 10px;">
									${bidRequest.totalRewardAmount}
								</td>
							</tr>
							<tr>
								<td class="muted">还款方式</td>
								<td class="text-info" style="padding-left: 10px;">
									${bidRequest.returnTypeDisplay}
								</td>
								<td class="muted">最小投标</td>
								<td class="text-info" style="padding-left: 10px;">
									${bidRequest.minBidAmount}
								</td>
							</tr>
							<tr>
								<td class="muted">风控意见</td>
								<td class="text-info" style="padding-left: 10px;" colspan="3">
									${bidRequest.note}
								</td>
							</tr>
							<tr>
								<td class="muted">剩余时间</td>
								<td class="text-info" style="padding-left: 10px;" colspan="3">
									<div id="retroclockbox"></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="col-sm-3">
					<table style="height:110px;width:230px;">
						<tr>
							<td  class="muted">投标总数</td><td class="text-info" style="padding-left: 10px;">
								${bidRequest.bidCount}
							</td>
						</tr>
						<tr>
							<td  class="muted">还需金额</td><td class="text-info" style="padding-left: 10px;">
								${bidRequest.remainAmount} 元
							</td>
						</tr>
						<tr>
							<td  class="muted" colspan="2">投标进度</td>
						</tr>
						<tr>
							<td colspan="2">
							<div style="margin-bottom: 10px;" class="progress">
								<div style="width: ${bidRequest.persent}" class="progress-bar progress-bar-info progress-bar-striped"></div>
							</div>
							</td>
						</tr>
					</table>
					
					<#if bidRequest.bidRequestState==1>
						<#if self>
							<br />
							<a class="btn btn-danger btn-block" style="font-size: 18px;" href="#">
								投标中
							</a>
						<#elseif account?? && !self>
						<input id="usableAmount" autocomplete="off" value="${account.usableAmount}" type="hidden"/>
						<input id="minBidAmount" autocomplete="off" value="${bidRequest.minBidAmount}" type="hidden"/>
						<input id="maxBidAmount" autocomplete="off" value="${bidRequest.remainAmount}" type="hidden"/>
						
						<table style="height:100px;width:180px;">
							<tr>
								<td class="muted">可用余额</td>
								<td>
									<span class="text-info">
										${account.usableAmount}
									</span>
								</td>
							</tr>
							<tr>
								<td class="muted">已投</td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td class="muted">还需要</td>
								<td>
									<span class="text-info">
										${bidRequest.remainAmount}
									</span>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="form-group">
										<input class="form-control input-sm" id="amount" autocomplete="off" placeholder="投资金额" />
									</div>
								</td>
							</tr>
						</table>
						<button id="bidBtn" class="btn btn-danger  btn-block">
							马上投标
						</button>
						<#else>
							<br />
							<a class="btn btn-danger btn-block" style="font-size: 18px;" href="/login.html">
								登录投标
							</a>
						</#if>
					</#if>
					
					<#if bidRequest.bidRequestState==4 || bidRequest.bidRequestState==5>
						<h4 class="text-primary">满标审核中</h4>
					</#if>
					<#if bidRequest.bidRequestState==7>
						<h4 class="text-primary">还款中</h4>
					</#if>
					<#if bidRequest.bidRequestState==8>
						<h4 class="text-primary">已还清</h4>
					</#if>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					借款人信息
				</div>
				<div class="panel-body">
					<table>
						<tbody>
							<tr>
								<td class="muted text-right" width="140px;">出生时间</td>
								<td width="150px;" style="padding-left: 10px;" class="text-info">
									${(realAuth.birthDate)!""}
								</td>
								<td class="muted text-right" width="140px;">借款额度</td>
								<td width="150px;" style="padding-left: 10px;" class="text-info">
									${bidRequest.bidRequestAmount}
								</td>
								<td class="muted text-right" width="140px;">性别</td>
								<td width="150px;" style="padding-left: 10px;" class="text-info">
									${realAuth.sexDisplay}
								</td>
								<td class="muted text-right" width="140px;">住房条件</td>
								<td width="150px;" style="padding-left: 10px;" class="text-info">
									${userInfo.houseCondition.title}
								</td>
							</tr>
							<tr>
								<td class="muted text-right">文化程度</td>
								<td style="padding-left: 10px;" class="text-info">
									${userInfo.educationBackground.title}
								</td>
								<td class="muted text-right">每月收入</td>
								<td style="padding-left: 10px;" class="text-info">
									${userInfo.incomeGrade.title}
								</td>
								<td class="muted text-right">婚姻情况</td>
								<td style="padding-left: 10px;" class="text-info">
									${userInfo.marriage.title}
								</td>
								<td class="muted text-right">子女情况</td>
								<td style="padding-left: 10px;" class="text-info">
									${userInfo.kidCount.title}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					<div style="font-size: 16px;">材料信息</div>
				</div>
				<div class="panel-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>材料类型</th>
								<th>材料数量</th>
							</tr>
						</thead>
						<tbody>
							<#list userFiles as file>
					    	<tr style="cursor: pointer;" lid="2101" st="1" class="more">
						        <th>${file.fileType.title}</th>
						        <td>1</td>
						    </tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					<div style="font-size: 16px;">还款情况</div>
				</div>
				<div class="panel-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>还款状态</th>
								<th>最近一周</th>
								<th>最近1月</th>
								<th>最近6月</th>
								<th>6个月前</th>
								<th>总计[?]</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>提前还款</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
							</tr>
							<tr>
								<td>准时还款</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
							</tr>
							<tr>
								<td>逾期已还</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
							</tr>
							<tr>
								<td>逾期未还</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
								<td>0</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<div style="font-size: 16px;">投标记录</div>
				</div>
				<div class="panel-body">
						<table class="table table-striped">
						<thead>
							<tr>
								<th>投标人</th>
								<th>年利率 </th>
								<th>有效金额(¥)</th>
								<th>投标时间</th>
								<th>类型</th>
							</tr>
						</thead>
						<tbody>
							<#if bidRequest.bids?size &gt; 0>
								<#list bidRequest.bids as bid>
									<tr>
										<td>${bid.bidUser.username}</td>
										<td>
											${bid.actualRate}%
										</td>
										<td style="padding-right:60px;" class="text-info">
											${bid.availableAmount}
										</td>
										<td>
											${bid.bidTime?string("yyyy-MM-dd HH:mm:ss")}
										</td>
										<td>手动投标</td>
									</tr>
								</#list>
							<#else>
								<tr>
									<td colspan="6">
										<p class="text-primary text-center">暂时没有投标数据</p>
									</td>
								</tr>
							</#if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<#include "common/footer-tpl.ftl" />	
	</body>
</html>