package com.sgen.app.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sgen.DAO.DeviceDAO;
import com.sgen.DTO.DeviceDTO;
import com.sgen.DTO.UsersDTO;
import com.sgen.util.JsonUtil;
import com.sgen.util.ResultStatus;

public class GetOTP extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DeviceDAO deviceDAO = new DeviceDAO();
		DeviceDTO deviceDTO = new DeviceDTO();
		UsersDTO usersDTO = new UsersDTO();
		JSONObject jsonObj = new JSONObject();
		
		JSONObject jsonObjResultStatus = new JSONObject();
		JSONArray jsonArrResultStatus = new JSONArray();
		
		HttpSession session = request.getSession();
		usersDTO = (UsersDTO) session.getAttribute("USER");
		
		if(usersDTO!=null){
			deviceDTO.setDeviceCode(usersDTO.getDeviceCode());
			
			deviceDTO = deviceDAO.getOTP(deviceDTO);
			if(deviceDTO.getOtp()!=null){
				
				jsonObjResultStatus.put("otp", deviceDTO.getOtp());
				jsonArrResultStatus.add(jsonObjResultStatus);
				jsonObj.put("status", "ok");
				jsonObj.put("result", jsonArrResultStatus);
				
			} else {
				jsonObj = JsonUtil.makeErrorObject(jsonObj, ResultStatus.NOTEXISTSOTP);
			}
		} else {
			jsonObj = JsonUtil.makeErrorObject(jsonObj, ResultStatus.INVALIDUSER);
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonObj);
		out.print(jsonObj);
		out.close();
	}
	
}
