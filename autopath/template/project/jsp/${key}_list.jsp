<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/static/include/head.jsp"%>

	<script type="text/javascript">
	#
	var oper_height = crud.editMap.~size * 60 + 90; // 重设高度
	if(oper_height < 300) oper_height = 300;
	if(oper_height > 500) oper_height = 500;
	#
	
	paginator = function(page){
		oper_list();
	};
	
	function oper_list(){
		form1.action = '@{crud.urlKey}/list';
		form1.submit();
	}
	
	function oper_view(pid){
		Iframe('@{crud.urlKey}/view/'+pid,300,@{oper_height},'@{crud.name}查看',false,false,false,EmptyFunc);
	}
	
	function oper_add(){
		var url = '@{crud.urlKey}/add';
		Iframe(url,350,@{oper_height},'@{crud.name}添加');
	}
	
	function oper_edit(pid){
		var url = '@{crud.urlKey}/edit/'+pid;
		Iframe(url,350,@{oper_height},'@{crud.name}修改');
	}
	
	function oper_del(pid){
		Confirm("确认要删除该@{crud.name}信息？",function(){
			form1.action = '@{crud.urlKey}/delete/'+pid;
			form1.submit();
		});
	}
	
	$(function(){
		//显示Menu索引
		showMenu('page_@{crud.urlKey}');
	});
	</script>
</head>
<body>

	<form name="form1" action="" method="post" class="form-inline" >
		<%@include file="/static/include/menu.jsp" %>
		
		<div class="tableSearch">
			<%  //查询列表 %>
			# for(entry in crud.searchMap){ #
				#
				var key = entry.value.key;
				var name = 'attr.'+entry.value.key;
				var data = flyfox.dataList(entry.value.formTypeData); 
				#
				<div class="form-group">
				#if (entry.value.formType=="INPUT") {#
					# if (entry.value.inputType=='TEXT') { #
						<input class="form-control" type="text" name="@{name}" value="${@{name}}" 
							placeholder="请输入@{entry.value.name}" />
					# } else if (entry.value.inputType=='RADIO') { #
						<select name="@{name}" class="form-control" >
						<option value="-1">--请选择--</option>
						# layout("util/select.html",{obj:data,name:name}){ } #
						</select>
					# } else if (entry.value.inputType=='CHECKBOX') { #
						<select name="@{name}" class="form-control" >
						<option value="-1">--请选择--</option>
						# layout("util/select.html",{obj:data,name:name}){ } #
						</select>
					# } else { #
						<input class="form-control" type="@{entry.value.inputType}" name="@{name}" value="${@{name}}"
							placeholder="请输入@{entry.value.name}" />
					# } #
				# } else if (entry.value.formType=='TEXTAREA') { #
					<input class="form-control" type="text" name="@{name}" value="${@{name}}"
						placeholder="请输入@{entry.value.name}" />
				# }  else if (entry.value.formType=='DICT') { #
					<select name="@{name}" class="form-control" >
					<option value="-1">--请选择--</option>
					${flyfox.dictSelect('@{entry.value.formTypeData}' , @{name})  }
					</select>
				# }  else if (entry.value.formType=='SELECT') { #
					<select name="@{name}" class="form-control" >
					<option value="-1">--请选择--</option>
					# layout("util/select.html",{obj:data,name:name}){ } #
					</select>
				# }#
				</div>
			# }#
			
			<button type="button" class="btn btn-default" onclick="oper_list();" name="search">
			 		<span class="glyphicon glyphicon-search"></span> 查 询
			</button>
			<button type="button" class="btn btn-default" onclick="resetForm();">
			 		<span class="glyphicon glyphicon-refresh"></span> 重 置
			</button>
			<button type="button" class="btn btn-default" onclick="oper_add();">
			 		<span class="glyphicon glyphicon-plus"></span> 新 增
			</button>
		</div>

		<!-- 数据列表 -->
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<%  // 列表头部 %>
					# for(entry in crud.listMap){ #
						<th>@{entry.value.name}</th> 
					# } #
					<th>操作</th>
				</tr>
			</thead>
			<c:forEach items="${requestScope.page.list}" var="item" varStatus="row">
				<tbody>
					<tr>
						<td width="50">${row.count }</td>
						<%  // 列表内容 %>
						# 
						for(entry in crud.listMap){  
							var key = entry.value.key;
							var name = 'item.'+entry.value.key;
							var data = flyfox.dataList(entry.value.formTypeData); 
						
							if (entry.value.formType=="TEXTAREA") {
						#
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
							# } else if (entry.value.formType=="INPUT") {#
								# if (entry.value.inputType=='TEXT') { #
									<td>${@{name}}</td> 
								# } else if (entry.value.inputType=='RADIO') { #
									<% var @{entry.value.key}_data = '@{entry.value.formTypeData}'; %>
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
							# }  else if (entry.value.formType=='DICT') { #
									<td>${flyfox.dictValue(@{name}!'' )  }</td>
							# }  else if (entry.value.formType=='SELECT') {  #
									<td>
									# layout("util/value.html",{obj:data,name:name}){ }  #
									</td>
							# }#
						# } #
						<td>
						<a href="javascript:void(0);" class="btn btn-sm btn-success" onclick="oper_view(${item.@{crud.primaryKey}});">查看</a> 
						<a href="javascript:void(0);" class="btn btn-sm btn-primary" onclick="oper_edit(${item.@{crud.primaryKey}});">修改</a> 
						<a href="javascript:void(0);" class="btn btn-sm btn-danger" onclick="oper_del(${item.@{crud.primaryKey}});">删除</a>
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
		
		<%@include file="/static/include/paginator.jsp" %>
			
	</form>
</body>
</html>