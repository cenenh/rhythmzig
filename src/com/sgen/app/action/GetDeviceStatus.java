package com.sgen.app.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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


public class GetDeviceStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		System.out.println("Get Device Status");
		int deviceStauts = 0;
		DeviceDAO deviceDAO = new DeviceDAO();
		DeviceDTO deviceDTO = new DeviceDTO();
		UsersDTO usersDTO = new UsersDTO();
		HttpSession session = request.getSession();
		JSONObject jsonObj = new JSONObject();
		JSONObject resultObject = new JSONObject();
		JSONArray jsonResultArr = new JSONArray();

		usersDTO = (UsersDTO) session.getAttribute("USER");
		
		if (usersDTO != null) 
		{
			deviceDTO.setDeviceCode(usersDTO.getDeviceCode());
			deviceDTO.setDeviceStatus(deviceStauts);

			deviceStauts = deviceDAO.getDeviceStatus(deviceDTO);
			jsonObj.put("status", "ok");
		
			if(deviceStauts == ResultStatus.DEVICE_NORMAL_STATUS)
				resultObject.put("deviceStatus", ResultStatus.NORMAL);
			else if(deviceStauts == ResultStatus.DEVICE_TRAVEL_STATUS)
				resultObject.put("deviceStatus", ResultStatus.TRAVEL);
			
			jsonResultArr.add(resultObject);
			jsonObj.put("result", jsonResultArr);
			
		} else {
			jsonObj = JsonUtil.makeErrorObject(jsonObj,ResultStatus.INVALIDUSER);
		}
		
		PrintWriter out = response.getWriter();
		out.print(jsonObj);
		out.close();
		
		
	}

}
