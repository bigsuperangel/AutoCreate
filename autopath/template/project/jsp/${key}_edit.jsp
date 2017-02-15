<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/static/include/head.jsp"%>
	
	<script type="text/javascript">
		function oper_save(id) {
			id = id || '0'
			form1.action = "@{crud.urlKey}/save/"+id;
			form1.submit();
			return true;
		}
	</script>

</head>
<body>

	<form name="form1" action="" method="post" class="form-horizontal" role="form" >
		<input type="hidden" name="model.@{crud.primaryKey}" value="${model.@{crud.primaryKey}}" />
		<table class="table">
			<% // 列表头部 %>
			# for(entry in crud.editMap){ #
				#
				var key = entry.value.key;
				var name = 'model.'+entry.value.key;
				var data = flyfox.dataList(entry.value.formTypeData); 
				#
<tr>
					<td>@{entry.value.name}</td>
					<td>
					# if (entry.value.formType=="INPUT") { #
						# if (entry.value.inputType=='TEXT') { #
							<input class="form-control" type="text" name="@{name}" value="${@{name}}" @{entry.value.formTypeValid! } />
						#
						} else if (entry.value.inputType=='RADIO') {
							layout("util/radio.html",{obj:data,name:name}){ }  
						} else if (entry.value.inputType=='CHECKBOX') {
							layout("util/checkbox.html",{obj:data,name:name}){ } 
						} else { 
						#
							<input class="form-control" type="@{entry.value.inputType}" name="@{name}" value="${@{name}}" @{entry.value.formTypeValid! } />
						# } #
					# } else if (entry.value.formType=='TEXTAREA') { #
						<textarea class="form-control" rows="3" cols="30" name="@{name}">${@{name}}</textarea>
					# }  else if (entry.value.formType=='DICT') { #
						<select name="@{name}" class="form-control" >
						${flyfox.dictSelect('@{entry.value.formTypeData}' , @{name}!'')  }
						</select>
					# }  else if (entry.value.formType=='SELECT') { #
						<select name="@{name}" class="form-control" >
						# layout("util/select.html",{obj:data,name:name}){ } #
						</select>
					# }#
					</td>
				</tr>
			# } #
			 
			<tr>
				<td colspan="2" style="text-align: center;">
				<button class="btn btn-default" onclick="return oper_save(${model.@{crud.primaryKey}});">保 存</button>
				<button class="btn btn-default" onclick="closeIframe();">关 闭</button>
				</td>
			</tr>
		</table>
	</form>

</body>

