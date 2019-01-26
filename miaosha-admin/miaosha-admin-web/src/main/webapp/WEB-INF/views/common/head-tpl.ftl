<div class="el-header" >
		<div class="container" style="position: relative;">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/">首页</a></li>
					<#if !logininfo??>
					<li><a href="/WEB-INF/login.html">登录</a></li>
					<li><a href="/WEB-INF/register.html">快速注册</a></li>
					<#else>
					<li>
						  <a class="el-current-user" href="/personal.do">
						  ${logininfo.nickname}
						  </a>
					</li>
					<li>
						  <a  href="#">
						  		账户充值
						  </a>
					</li>
					<li>
						  <a  href="#">
						  		注销
						  </a>
					</li>
					</#if>
				<li><a href="#">帮助</a></li>
			</ul>
		</div>
</div>
