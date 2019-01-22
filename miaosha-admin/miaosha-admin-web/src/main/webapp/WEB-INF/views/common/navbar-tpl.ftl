<div class="navbar navbar-default el-navbar">
	<div class="container">
		<div class="navbar-header">
			<a href="/">
				<img alt="Brand" src="/images/logo.png">
			</a>
		</div>
		<ul class="nav navbar-nav">
			<li id="index"><a href="/">首页</a></li>
			<li id="invest"><a href="/invest.do">我要投资</a></li>
			<li id="borrow"><a href="/borrow.do">我要借款</a></li>
			<li id="account"><a href="/personal.do">个人中心</a></li>
			<li><a href="#">新手指引</a></li>
			<li><a href="#">关于我们</a></li>
		</ul>
	</div>
</div>

<#if currentNav??>
<script type="text/javascript">
	$(".navbar-nav li[id=${currentNav}]").addClass("active");
</script>
</#if>
