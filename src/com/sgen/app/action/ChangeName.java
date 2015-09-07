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

import com.sgen.DAO.UsersDAO;
import com.sgen.DTO.UsersDTO;
import com.sgen.util.JsonUtil;
import com.sgen.util.ResultStatus;

public class ChangeName extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UsersDTO usersDTO = new UsersDTO();
		UsersDAO usersDAO = new UsersDAO();
		HttpSession session = request.getSession();

		int result;

		JSONObject jsonObj = new JSONObject();
		JSONArray jsonResultArr = new JSONArray();

		String userCode = null, userName = null;

		usersDTO = (UsersDTO) session.getAttribute("USER");

		if (usersDTO != null) {
			userCode = request.getParameter("userCode");
			userName = request.getParameter("userName");

			if (userName.length() > 5) {
				// invalidNickname
				jsonObj = JsonUtil.makeErrorObject(jsonObj,
						ResultStatus.invalidNickname);
			} else {

				usersDTO.setUserCode(userCode);
				usersDTO.setUserName(userName);

				result = usersDAO.changeName(usersDTO);
				if (result == 1) {
					jsonObj = JsonUtil.makeSuccessObject();
				} else {
					jsonObj = JsonUtil.makeErrorObject(jsonObj,
							ResultStatus.NOSUCHUSER);
				}

			}

		} else {
			jsonObj = JsonUtil.makeErrorObject(jsonObj,
					ResultStatus.INVALIDUSER);
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");
		out.print(jsonObj);
		out.close();

	}
}
