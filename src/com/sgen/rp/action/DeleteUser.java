package com.sgen.rp.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.sgen.DAO.UsersDAO;
import com.sgen.DTO.UsersDTO;
import com.sgen.util.JsonUtil;
import com.sgen.util.ResultStatus;

public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int result = 0;
		String returnResult = null;
		String userCode = request.getParameter("userCode");
		String deviceCode = request.getParameter("deviceCode");
		
		UsersDTO usersDTO = new UsersDTO();
		UsersDAO usersDAO = new UsersDAO();
		
		usersDTO.setDeviceCode(deviceCode);
		usersDTO.setUserCode(userCode);
		System.out.println("I will DELETE the user whose deviceCode : "+usersDTO.getDeviceCode() + " and userCode : "+usersDTO.getUserCode());
		result = usersDAO.deleteUser(usersDTO);
		
		if(result > 0 || result == 1 || result > 1)
		{
			//delete success
			returnResult = "DELETE COMPLETE";
			System.out.println(returnResult);
		}
		else
		{
			//delete fail
			returnResult = "DELTE FAIL";
			System.out.println(returnResult);
		}
		response.setContentType("charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(returnResult);
		out.close();
	}
}
