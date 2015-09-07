package com.sgen.app.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.sgen.DAO.UsersDAO;
import com.sgen.DTO.UsersDTO;
import com.sgen.util.JsonUtil;
import com.sgen.util.ResultStatus;

public class GrantAuthority extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UsersDTO userDTO = new UsersDTO();
		UsersDAO userDAO = new UsersDAO();

		JSONObject jsonObj = new JSONObject();

		HttpSession session = request.getSession();
		userDTO = (UsersDTO) session.getAttribute("USER");

		if (userDTO != null) {
			int depriveResult = userDAO.depriveAuthority(userDTO);
			
			String userCode = request.getParameter("userCode");
			userDTO.setUserCode(userCode);
			
			int grantResult = userDAO.grantAuthority(userDTO);
			
			if (grantResult == 1 && depriveResult ==1) {
				jsonObj = JsonUtil.makeSuccessObject();
			} else {
				jsonObj = JsonUtil.makeErrorObject(jsonObj,
						ResultStatus.NOSUCHUSER);
			}

		} else {
			jsonObj = JsonUtil.makeErrorObject(jsonObj,
					ResultStatus.INVALIDUSER);
		}
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsonObj);
		out.close();

	}

}
