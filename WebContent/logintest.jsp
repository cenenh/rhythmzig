<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="//code.jquery.com/jquery.js"></script>
<script type="text/javascript">
function insertAction(){
	var mForm = document.insert;
	var obj = new Object();
	obj.deviceCode = mForm.deviceCode.value;
	obj.userCode = mForm.userCode.value;
	alert(obj.userCode)
	var jsonData = JSON.stringify(obj);

	var request = $.ajax({
		url : "/login",
		type : "POST",
		data : jsonData,
		dataType : "json",
		success : function (data) {
			var ob=data;
			console.log(ob);
		},
		error : function(data){
			alert("error : " + data);
		}
	});
}
</script>
<title>${USER.userCode}</title>
</head>
<body>
	<form name="insert" action="/login" method="POST">
		deviceCode :<input type="text" id="deviceCode" name="deviceCode"/><br />
		userCode :<input type="text" id="userCode" name="userCode"/>
		regId :<input type="text" id="regId" name="regId"/> <br/>
		<input type="submit" value="로그인" />
	</form>

</body>
</html>
