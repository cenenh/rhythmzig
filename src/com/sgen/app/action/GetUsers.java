package com.sgen.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

public class GetUsers extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UsersDTO usersDTO = new UsersDTO();
		UsersDAO usersDAO = new UsersDAO();

		JSONObject jsonObj = new JSONObject();
		JSONArray jsonResultArr = new JSONArray();
		JSONObject jsonMessageObj = new JSONObject();

		HttpSession session = request.getSession();
		usersDTO = (UsersDTO) session.getAttribute("USER");
		if (usersDTO != null) {
			System.out.println(usersDTO.getDeviceCode());
			ArrayList<UsersDTO> userlist = new ArrayList<UsersDTO>();

			userlist = usersDAO.getUsers(usersDTO);

			String adminId = "";
			ArrayList<JSONObject> jsonUserList = new ArrayList<JSONObject>();
			for (int i = 0; i < userlist.size(); i++) {
				JSONObject jsonUserObj = new JSONObject();
				jsonUserObj.put("userName", userlist.get(i).getUserName());
				jsonUserObj.put("userCode", userlist.get(i).getUserCode());
				jsonUserList.add(jsonUserObj);
				if (userlist.get(i).isAdmin())
					adminId = userlist.get(i).getUserCode();
			}
			jsonMessageObj.put("users", jsonUserList);
			jsonMessageObj.put("admin", adminId);

			jsonObj.put("status", "ok");
			jsonObj.put("result", jsonResultArr);
			jsonResultArr.add(jsonMessageObj);

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
