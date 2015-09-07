<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Send OTP</title>
</head>
<body>
	<form action="getOTPfromRP" method="post">
		Device Code : <input type="text" name="deviceCode" value=""/> 
		OTP : <input type="text" name="otp" value=""/>
		<input type="submit" value="send OTP" />
	</form>
</body>
</html>