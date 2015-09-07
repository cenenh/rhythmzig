package com.sgen.rp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sgen.DAO.DeviceDAO;
import com.sgen.DTO.DeviceDTO;
import com.sgen.DTO.UsersDTO;
import com.sgen.util.GCMUtil;

/**
 * Servlet implementation class getOTP
 */
public class GetOTPfromRP extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int result = 0;
		String returnResult = null;
		String deviceCode = request.getParameter("deviceCode");
		String otp = request.getParameter("otp");
		System.out.println("I got the otp : " + otp);
		System.out.println("I will set the otp! deviceCode : "+deviceCode+" / OTP : "+otp);
		System.out.println("Send OTP Device Code " + deviceCode);
		System.out.println("OTP Code : " + otp);
		GCMUtil gcmUtil = new GCMUtil(deviceCode);
		
		DeviceDTO deviceDTO = new DeviceDTO();
		DeviceDAO deviceDAO = new DeviceDAO();
		deviceDTO.setDeviceCode(deviceCode);
		deviceDTO.setOtp(otp);
		result = deviceDAO.setOTP(deviceDTO);
		
		if(result > 0 || result == 1)
		{
			//success set otp
			System.out.println("deviceCode : " + deviceDTO.getDeviceCode());
			System.out.println("OTP : "+deviceDTO.getOtp()+" is set");
			returnResult="OTP SET OK";
			
			gcmUtil.sendToAdminGCM("OTP 요청이 들어왔습니다.");
		}
		else
		{
			//fail set otp
			System.out.println("deviceCode : " + deviceDTO.getDeviceCode());
			System.out.println("OTP : "+deviceDTO.getOtp()+" is fail");
			returnResult="OTP SET FAIL";
		}
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
	    out.println(returnResult);
	} 
}
