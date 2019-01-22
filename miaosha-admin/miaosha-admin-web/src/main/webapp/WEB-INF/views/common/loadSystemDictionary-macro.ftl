<#macro loadDictionary sn>
	<#assign itemList = _DicUtil.list(sn)/>
	<#list itemList as item>
		<option value="${item.id}">${item.title}</option>
	</#list>
</#macro>