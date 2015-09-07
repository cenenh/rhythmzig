<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
function insertAction(){

	var deviceStatus = document.getElementById("deviceStatus").value;
	console.log(deviceStatus);

	var request = $.ajax({
		url : "/setDeviceStatus",
		type : "POST",
		data : { deviceStatus : deviceStatus },
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
<title>setDeviceStatus Test</title>
</head>
<body>
<form name="insert" action="/login" method="POST">
		deviceStatus :<input type="text" id="deviceStatus" /><br />
		<input type="button" value="change deviceStatus" onClick="insertAction()"/>
	</form>
</body>
</html>