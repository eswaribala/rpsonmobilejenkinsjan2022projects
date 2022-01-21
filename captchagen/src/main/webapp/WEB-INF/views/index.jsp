<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Account</title>
</head>
<body>

	<h3>Register Account</h3>
	<s:form method="post" modelAttribute="account"
		action="${pageContext.request.contextPath }/account/save">
		<table>
			<tr>
				<td>Username</td>
				<td>
					<s:input path="username" required="required"/>
				</td>
			</tr>
			<tr>
				<td>Password</td>
				<td>
					<s:password path="password" required="required"/>
				</td>
			</tr>
			<tr>
				<td>Captcha</td>
				<td>
					<img src="${pageContext.request.contextPath }/captcha">
					<br>
					<input type="text" name="captcha" required="required" style="margin-top: 5px;">
					<br>
					${error }
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<input type="submit" value="Save">
				</td>
			</tr>
		</table>
	</s:form>

</body>
</html>