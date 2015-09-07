package com.sgen.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonUtil {
	public static JSONObject makeErrorObject(JSONObject jsonObj, String status) {
		// TODO Auto-generated method stub
		JSONObject jsonMessageObj = new JSONObject();
		JSONArray jsonResultArr = new JSONArray();

		jsonMessageObj.put("message", status);
		jsonResultArr.add(jsonMessageObj);
		jsonObj.put("status", status);
		jsonObj.put("result", jsonResultArr);

		return jsonObj;
	}
	
	public static JSONObject makeSuccessObject() {
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonResultArr = new JSONArray();
		jsonObj.put("status", "ok");
		jsonObj.put("result", jsonResultArr);

		return jsonObj;
	}
	
	public static JSONObject CAN_NOT_DELETE_USER()
	{
		JSONObject jsonObj = new JSONObject();
		//JSONArray jsonResultArr = new JSONArray();
		jsonObj.put("status", "ADMIN_USER");
		jsonObj.put("message", ResultStatus.CAN_NOT_DELETE);
		
		return jsonObj;
	}
	
	public static JSONObject CAN_DELETE_USER()
	{
		JSONObject jsonObj = new JSONObject();
		//JSONArray jsonResultArr = new JSONArray();
		jsonObj.put("status", "NORMAL_USER");
		jsonObj.put("message", ResultStatus.OK_TO_DELETE);
		
		return jsonObj;
	}
	
	public static JSONObject UNKNOWN_USER()
	{
		JSONObject jsonObj = new JSONObject();
		//JSONArray jsonResultArr = new JSONArray();
		jsonObj.put("status", "UNKNOWN_USER");
		jsonObj.put("message", ResultStatus.RE_CHECK_WANTED);
		
		return jsonObj;
	}
	
	public static JSONObject Success()
	{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status","SUCCESS");
		jsonObj.put("message", "DELETE COMPLETE");
		return jsonObj;
	}
	
	public static JSONObject Fail()
	{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("status","FAIL");
		jsonObj.put("message", "DELETE FAIL");
		return jsonObj;
	}
	
}
