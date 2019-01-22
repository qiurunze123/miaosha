<ul id="menu" class="list-group">
	<li class="list-group-item">
		<a href="javascript:;"><span>借贷项目</span></a>
		<ul>
			<li name="bid"><a href="/bid_list.do"><span>投标明细</span></a></li>
			<li name="receive"><a href="#"><span>收款明细</span></a></li>
			<li name="bidRequest"><a href="/myborrow_list.do"><span>借款项目</span></a></li>
			<li name="borrowBidReturn"><a href="#"><span>还款明细</span></a></li>
		</ul>
	</li>
	<li class="list-group-item">
		<a href="#"><span class="text-title">我的账户</span></a>
		<ul class="in">
			<li name="account"><a href="/personal.do">账户信息</a></li>
			<li name="realAuth"><a href="/realAuth.do">实名认证</a></li>
			<li name="userFile"><a href="/userFile.do">风控资料认证</a></li>
			<li name="bankInfo"><a href="#">银行卡管理</a></li>
			<li name="iplog"><a href="/iplog.do">登录记录</a></li>
			<li name="userInfo"><a href="/basicInfo.do"> <span>个人资料</span></a></li>
		</ul>
	</li>
	<li class="list-group-item">
		<a href="#"><span>资产详情</span></a>
		<ul class="in">
			<li name="accountFlow_list"><a href="">账户流水</a></li>
			<li name="recharge"><a href="/recharge_list.do">充值明细</a></li>
			<li name="moneyWithdraw"><a href="#">提现申请</a></li>
		</ul>
	</li>
</ul>

<#if currentMenu??>
<script type="text/javascript">
$("#menu li[name=${currentMenu}]").addClass("active");
</script>
</#if>