<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change Name test page</title>
<script src="//code.jquery.com/jquery.js"></script>
<script type="text/javascript">
function insertAction(){
	var mForm = document.insert;
	var obj = new Object();
	obj.userCode = mForm.userCode.value;
	obj.userName = mForm.userName.value;
	var jsonData = JSON.stringify(obj);
	console.log(jsonData);

	var request = $.ajax({
		url : "/changeName",
		type : "POST",
		data : jsonData,
		dataType : "json",
		success : function (data) {
			var ob=data;
			alert(ob.value);
			console.log(ob);
		},
		error : function(data){
			alert("error : " + data);
		}
	});
}
</script>
</head>
<body>
	<form name="insert" action="/changeName" action="POST">
		userCode :<input type="text" id="userCode" name="userCode"/>
		userName :<input type="text" id="userName" name="userName"/>
		<input type="submit" value="changename" />
	</form>

</body>
</html>
