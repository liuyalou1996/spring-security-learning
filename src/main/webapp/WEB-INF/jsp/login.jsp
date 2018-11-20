<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String contextPath = request.getContextPath();
  String basePath =
      request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录界面</title>
</head>
<body>
	<!-- 注意：请求方式必须为post，且action地址必须为loginProcessingUrl配置的地址 -->
	<form action="loginProcess" method="post">
		<!-- 如果没禁用csrf则要加上隐藏域，用于传csrf token给后台-->
		<!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> -->
		<table>
			<tr>
				<td>用户名：</td>
				<td><input name="username" type="text" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input name="password" type="password" /></td>
			</tr>
			<tr>
				<td>记住我<input name="remember-me" type="checkbox" checked="checked" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="登录"></td>
			</tr>
		</table>
	</form>
</body>
</html>