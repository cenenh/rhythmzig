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

import com.sgen.DAO.UsersDAO;
import com.sgen.DTO.UsersDTO;

public class AgetUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UsersDTO usersDTO = new UsersDTO();
		UsersDAO usersDAO = new UsersDAO();
		usersDTO.setDeviceCode("123412341234"); //deviceCode 설정
		ArrayList<UsersDTO> userlist = new ArrayList<UsersDTO>();
		
		userlist = usersDAO.getUsers(usersDTO);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(userlist);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String deviceCode = request.getParameter("deviceCode");
		String isAdmin = null;
		//System.out.println("GET CALLS");
		UsersDTO usersDTO = new UsersDTO();
		UsersDAO usersDAO = new UsersDAO();
		usersDTO.setDeviceCode(deviceCode); //deviceCode 설정
		ArrayList<UsersDTO> userlist = new ArrayList<UsersDTO>();

		userlist = usersDAO.getUsers(usersDTO);
		ArrayList<JSONObject> juserList = new ArrayList<JSONObject>();
		for(int i =0;i<userlist.size();i++)
		{
			JSONObject jsonUserObj = new JSONObject();
			jsonUserObj.put("userCode", userlist.get(i).getUserCode());
			jsonUserObj.put("userName", userlist.get(i).getUserName());
			jsonUserObj.put("deviceCode", userlist.get(i).getDeviceCode());
			if(userlist.get(i).isAdmin() == true )
				isAdmin = "Admin";
			else
				isAdmin = "";
			jsonUserObj.put("isAdmin",isAdmin);
			
			juserList.add(jsonUserObj);
		}
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(juserList);
		System.out.println(juserList);
		out.close();	
	
	}

}
