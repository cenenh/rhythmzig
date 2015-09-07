package com.sgen.app.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Ping extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		JSONObject jsonResult = new JSONObject();

		PrintWriter out = response.getWriter();

		jsonObject.put("status", "ok");
		jsonResult.put("message", "pong");
		jsonArray.add(jsonResult);

		jsonObject.put("result", jsonArray);

		out.print(jsonObject);
		out.close();

	}

}
