<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/static/include/head.jsp"%>
</head>
<body>
	<form name="form1" action="" method="post" class="form-horizontal" role="form">
		<!-- 数据列表 -->
		<table class="table">
			<%  // 列表头部 %>
			# for(entry in crud.viewMap){ #
				#
				var key = entry.value.key;
				var name = 'model.'+entry.value.key;
				var data = flyfox.dataList(entry.value.formTypeData); 
				#
				<tr>
					<td>@{entry.value.name}</td>
					#if (entry.value.formType=="INPUT") {#
						# if (entry.value.inputType=='TEXT') { #
					<td>${@{name}}</td>
						# } else if (entry.value.inputType=='RADIO') { #
					<td>
					# layout("util/value.html",{obj:data,name:name}){ }  #
					</td> 
						# } else if (entry.value.inputType=='CHECKBOX') { #
					<td>
					# layout("util/value.html",{obj:data,name:name}){ }  #
					</td> 
						# } else { #
					<td>${@{name}}</td>
						# } #
					# } else if (entry.value.formType=='TEXTAREA') { #
					<td title="${@{name}}">
						<c:choose>
							<c:when test="${fn:length(@{name}) > 6}">
									${fn:substring(@{name}, 0, 6)}...
							</c:when>
							<c:otherwise>
									${@{name}}
							</c:otherwise>
						</c:choose>
					</td>
					# }  else if (entry.value.formType=='DICT') { #
					<td>${flyfox.dictValue(@{name} )  }</td>
					# }  else if (entry.value.formType=='SELECT') { #
					<td>
					# layout("util/value.html",{obj:data,name:name}){ }  #
					</td> 
					#}#
				</tr>
			# } #
			
			<tr>
				<td colspan="2" align="center">
				<button class="btn btn-default" onclick="closeIframe();">关 闭</button>
				</td>
			</tr>
		</table>
	</form>

</body>
