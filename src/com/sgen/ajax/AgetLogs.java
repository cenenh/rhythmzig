package com.sgen.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.sgen.DAO.LogsDAO;
import com.sgen.DTO.LogsDTO;
import com.sgen.util.ResultStatus;

/**
 * Servlet implementation class AgetLogs
 */

public class AgetLogs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String deviceCode = request.getParameter("deviceCode");
		ArrayList<LogsDTO> loglist = new ArrayList<LogsDTO>();
		LogsDTO logsDTO = new LogsDTO();
		LogsDAO logsDAO = new LogsDAO();
	
		logsDTO.setDeviceCode(deviceCode);
		logsDTO.setDeviceCode(deviceCode);
		
		loglist = logsDAO.getLogsForAjax(logsDTO);
		
		ArrayList<JSONObject> jlogList = new ArrayList<JSONObject>();
		
		for(int i =0;i<loglist.size();i++)
		{
			JSONObject jsonLogObj = new JSONObject();
			
			if(loglist.get(i).getUserCode().equals(ResultStatus.OTPO))
			{	
				jsonLogObj.put("userCode", "OTP 출입 성공");
				jsonLogObj.put("userName", "OTP 출입 성공");
			}
			else if(loglist.get(i).getUserCode().equals(ResultStatus.OTPX))
			{
				jsonLogObj.put("userCode", "OTP 출입 실패");
				jsonLogObj.put("userName", "OTP 출입 실패");
			}
			else if(loglist.get(i).getUserCode().equals(ResultStatus.FAIL))
			{
				jsonLogObj.put("userCode", "출입 실패");
				jsonLogObj.put("userName", "출입 실패");
			}
			else if(loglist.get(i).getUserCode().equals(ResultStatus.LOCK))
			{
				jsonLogObj.put("userCode", "잠금되었습니다.");
				jsonLogObj.put("userName", "잠금되었습니다.");
			}
			else
			{
				jsonLogObj.put("userCode", loglist.get(i).getUserCode());
				jsonLogObj.put("userName", loglist.get(i).getUserName());
			}
			
			if(loglist.get(i).getDeviceStatus() == ResultStatus.DEVICE_NORMAL_STATUS)
			{
				jsonLogObj.put("deviceStatus", "일반 모드");
			}
			else if(loglist.get(i).getDeviceStatus() == ResultStatus.DEVICE_TRAVEL_STATUS )
			{
				jsonLogObj.put("deviceStatus", "여행 모드");
			}
			else
			{
				jsonLogObj.put("deviceStatus",loglist.get(i).getDeviceStatus());
			}
			jsonLogObj.put("deviceCode", loglist.get(i).getDeviceCode());
			jsonLogObj.put("enterTime", loglist.get(i).getEnterTime().toString());
			
			jlogList.add(jsonLogObj);
		}
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jlogList);
		System.out.println(jlogList);
		out.close();	
	}

}
