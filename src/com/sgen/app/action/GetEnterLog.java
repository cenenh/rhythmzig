package com.sgen.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sgen.DAO.*;
import com.sgen.DTO.LogsDTO;
import com.sgen.DTO.UsersDTO;
import com.sgen.util.JsonUtil;
import com.sgen.util.ResultStatus;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class GetEnterLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		
		int deviceStatus = 0;
		int IF_EXIST_USER = 0;
		boolean needAllLogs = false;
	
		LogsDTO dto = new LogsDTO();
		LogsDAO dao = new LogsDAO();
		UsersDTO usersDTO = new UsersDTO();
		UsersDAO usersDAO = new UsersDAO();
		
		JSONObject jsonObj = new JSONObject();
		
		JSONArray jsonResultArr = new JSONArray();
		JSONObject jsonMessageObj = new JSONObject();
		
		String mode = request.getParameter("mode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String userCode = request.getParameter("userCode");
		
		HttpSession session = request.getSession();
		usersDTO = (UsersDTO) session.getAttribute("USER");
						
		System.out.println("requested mode : "+mode);
		System.out.println("requested startDate : "+startDate);
		System.out.println("requested endDate : "+endDate);
		System.out.println("requested userCode : "+userCode);
			
		if(mode.equals(ResultStatus.TRAVEL)) //travel mode
		{
			deviceStatus = ResultStatus.DEVICE_TRAVEL_STATUS;
		}
		else if(mode.equals(ResultStatus.NORMAL)) //normal mode
		{
			deviceStatus = ResultStatus.DEVICE_NORMAL_STATUS;
		}
		else
		{
			deviceStatus = ResultStatus.DEVICE_INVALID_STATUS;			
		}
		
		if (usersDTO != null) // Yes Session...	
		{ 
			if(userCode.equals(ResultStatus.ALL_USER))
			{
				IF_EXIST_USER = 1;
				needAllLogs = true;
			}
			else if( !userCode.equals(ResultStatus.ALL_USER) && needAllLogs == false ) // Need the logs of specific person
			{
				IF_EXIST_USER = usersDAO.ifExistUSer(usersDTO);
			}
			if( IF_EXIST_USER == 1 )
			{
				System.out.println("EXIST USER");
				dto.setUserCode(userCode);
				dto.setDeviceStatus(deviceStatus);
				dto.setDeviceCode(usersDTO.getDeviceCode());
				
				System.out.println("Request Log ID : " + userCode);
				System.out.println("Request Log Name : "+ usersDTO.getUserName());
				System.out.println("Request Log Device Code : "+ usersDTO.getDeviceCode());	
				
				ArrayList<LogsDTO> LogsList = new ArrayList<LogsDTO>();
				
				if( deviceStatus != ResultStatus.DEVICE_INVALID_STATUS && needAllLogs == false ) { //deviceStatus 가 정확할때만
					
					LogsList = dao.getLogs(dto, startDate, endDate);
				}
				else if(deviceStatus != ResultStatus.DEVICE_INVALID_STATUS && needAllLogs == true)
				{
					dto.setUserCode(ResultStatus.ALL_USER);
					System.out.println("DeviceCode : " +dto.getDeviceCode()+" i will get the logs of Everyone!");
					LogsList = dao.getLogs(dto, startDate, endDate);
				}
				else{ //deviceStatus 부정확
					jsonObj = JsonUtil.makeErrorObject(jsonObj, ResultStatus.DEVICE_INVALID_MODE);
				}
				
				if (LogsList.isEmpty()) { // No logs...
					jsonObj = JsonUtil.makeErrorObject(jsonObj, ResultStatus.DEVICE_NO_LOGS);
				} 
				else 
				{
					ArrayList<JSONObject> jsonLogsList = new ArrayList<JSONObject>();
					
					for (int i = 0; i < LogsList.size(); i++) 
					{
						JSONObject jsonLogsObj = new JSONObject();
						
						jsonLogsObj.put("userCode", LogsList.get(i).getUserCode() );
						jsonLogsObj.put("time", LogsList.get(i).getEnterTime().toString() );
						
						//set deviceStatus
						if(LogsList.get(i).getDeviceStatus() == ResultStatus.DEVICE_NORMAL_STATUS)
						{
							jsonLogsObj.put("deviceStatus", ResultStatus.NORMAL);
						}
						else if (LogsList.get(i).getDeviceStatus() == ResultStatus.DEVICE_TRAVEL_STATUS)
						{
							jsonLogsObj.put("deviceStatus", ResultStatus.TRAVEL);
						}
						else
						{
							jsonLogsObj.put("deviceStatus", ResultStatus.UNKNOWN);
						}
						
						//set deviceName
						if(LogsList.get(i).getUserCode().equals(ResultStatus.OTPO))
						{
							jsonLogsObj.put("userName", "OTP 출입이 성공하였습니다.");
						}
						else if(LogsList.get(i).getUserCode().equals(ResultStatus.OTPX))
						{
							jsonLogsObj.put("userName", "OTP 출입이 실패하였습니다.");
						}
						else if(LogsList.get(i).getUserCode().equals(ResultStatus.FAIL))
						{
							jsonLogsObj.put("userName", "출입이 실패하였습니다.");	
						}
						else if(LogsList.get(i).getUserCode().equals(ResultStatus.LOCK))
						{
							jsonLogsObj.put("userName", "[5번 출입 실패] 도어락이 잠금되었습니다.");	
						}
						else
						{
							jsonLogsObj.put("userName", LogsList.get(i).getUserName() );
						}
						
						jsonLogsList.add(jsonLogsObj);
					}
					jsonObj.put("status", "ok");
					jsonObj.put("result", jsonLogsList);
					System.out.println(jsonObj);	
				}
			} //if(IF_EXIST_USER==1) 존재하는 user면 !!
			
			else if( IF_EXIST_USER == 0 ) // 존재하지 않는 USER
			{
				System.out.println("NOT EXIST USER");	
				jsonObj = JsonUtil.makeErrorObject(jsonObj, ResultStatus.NOSUCHUSER);
			}
		}
		else{ // No Session. can not get the logs;
			System.out.println("No session");
			jsonObj = JsonUtil.makeErrorObject(jsonObj, ResultStatus.INVALIDUSER);
		}
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonObj);
		out.close();	
	}
}
