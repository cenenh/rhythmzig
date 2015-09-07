package com.sgen.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sgen.util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.sql.*;
import java.text.SimpleDateFormat;

import com.sgen.DTO.LogsDTO;
import com.sgen.DTO.UsersDTO;

public class LogsDAO {
	

	public static final String ADD_LOGS_TEST = "INSERT INTO enterlog VALUES"
			+ "(?, ?, ?, (SELECT deviceStatus FROM device WHERE deviceCode=?), ?)";
	
	public static final String ADD_LOGS=
			"INSERT INTO rhythmzig.enterlog(userCode,deviceCode,deviceStatus) VALUES(?,?,(SELECT deviceStatus FROM rhythmzig.device WHERE deviceCode=?))";
	
	public static final String GET_LOGS_OF_ONE_USER = 
			"select userCode,"
			+ "(select userName from rhythmzig.users where deviceCode= ? and userCode = enterlog.userCode) as userName,"
			+ "deviceCode,deviceStatus,enterTime "
			+ "from rhythmzig.enterlog "
			+ "where deviceCode=? and userCode=? and deviceStatus=? "
			+ "and enterTime between ? and ? order by enterTime desc";
	
	public static final String GET_LOGS_OF_ALL_USERS = 
			"select userCode,"
			+ "(select userName from rhythmzig.users where deviceCode =? and userCode = enterlog.userCode) as userName,"
			+ "deviceCode,deviceStatus,enterTime "
			+ "from rhythmzig.enterlog "
			+ "where deviceCode=? and deviceStatus=? "
			+ "and enterTime between ? and ? order by enterTime desc";
	
	public static final String GET_LOGS_OF_ALL_USERS_FOR_AJAX = 
			"select userCode,"
			+ "(select userName from rhythmzig.users where deviceCode =? and userCode = enterlog.userCode) as userName,"
			+ "deviceCode,deviceStatus,enterTime "
			+ "from rhythmzig.enterlog "
			+ "where deviceCode=? order by enterTime desc";

	public static Date startDate;
	public static Date endDate; 
	
	public ArrayList<LogsDTO> getLogsForAjax(LogsDTO dto){
		
		ArrayList<LogsDTO> logsList = new ArrayList<LogsDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			int idx = 1;
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(GET_LOGS_OF_ALL_USERS_FOR_AJAX);
			
			pstmt.setString(idx++, dto.getDeviceCode()); 
			pstmt.setString(idx++, dto.getDeviceCode()); 
			
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				LogsDTO resultDTO = new LogsDTO();
				resultDTO.setDeviceCode(rs.getString("deviceCode"));
				resultDTO.setUserCode(rs.getString("userCode"));
				resultDTO.setUserName(rs.getString("userName"));
				resultDTO.setDeviceStatus(rs.getInt("deviceStatus"));
				resultDTO.setEnterTime(rs.getTimestamp("enterTime"));
				logsList.add(resultDTO);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt,rs);
		}
		return logsList;
	}

	public ArrayList<LogsDTO> getLogs(LogsDTO dto,String reQuestedStartDate, String reQuestedEndDate)
	{
		System.out.println("getLogs() in LOGSDAO CALL!!");
		ArrayList<LogsDTO> enterLogsList = new ArrayList<LogsDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TimeZone tz;

		try {
			int idx = 1;
			conn = JDBCUtil.getConnection();
			// reQuestedStartDate , reQuestedEndDate
			
			startDate = new Date();
			endDate = new Date();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
			//	SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			tz = TimeZone.getTimeZone("Asia/Seoul"); 
			dateFormat.setTimeZone(tz);
			startDate = dateFormat.parse(reQuestedStartDate);
		    endDate = dateFormat.parse(reQuestedEndDate);		  
		   
		    System.out.println("Parsed starte date : " + startDate); //로컬에서는 잘 되지만, 서버에서는 UTC로 파싱됨..
		    System.out.println("Parsed end date : " + endDate); //로컬에서는 잘 되지만, 서버에서는 UTC로 파싱됨..
		    
		    //Calendar calendar_start = Calendar.getInstance();
		    //Calendar calendar_end = Calendar.getInstance();
		    
		   // calendar_start.setTime(startDate);
		   // calendar_start.add(Calendar.HOUR, 9);
		    
		    //calendar_end.setTime(endDate);
		   // calendar_end.add(Calendar.HOUR, 9);
		    
		    //Date calculated_start_date = calendar_start.getTime();
		    //Date calculated_end_date = calendar_end.getTime();
		    //System.out.println("After calculate start date : " + calculated_start_date);
		   // System.out.println("After calculate end date : " + calculated_end_date);
		    
		    Timestamp s_timestamp = new java.sql.Timestamp(startDate.getTime()); 
		    Timestamp e_timestamp = new java.sql.Timestamp(endDate.getTime());

		    System.out.println("Parsed timestamp start date : "+s_timestamp);
		    System.out.println("Parsed timestamp end date : "+e_timestamp);
		    	    
			if(dto.getUserCode().equals(ResultStatus.ALL_USER))
			{
				//all users;
				System.out.println("Need the LOGS OF ALL_USER");
				pstmt = conn.prepareStatement(GET_LOGS_OF_ALL_USERS);
				pstmt.setString(idx++, dto.getDeviceCode()); 
				pstmt.setString(idx++, dto.getDeviceCode()); 
				pstmt.setInt(idx++, dto.getDeviceStatus());
				pstmt.setTimestamp(idx++, s_timestamp );
				pstmt.setTimestamp(idx++, e_timestamp );
				rs = pstmt.executeQuery();
			}
			else
			{
				//specific user;
				System.out.println("Need the LOGS OF "+dto.getUserCode());
				pstmt = conn.prepareStatement(GET_LOGS_OF_ONE_USER);
				pstmt.setString(idx++, dto.getDeviceCode());
				pstmt.setString(idx++, dto.getDeviceCode()); 
				pstmt.setString(idx++,dto.getUserCode());
				pstmt.setInt(idx++, dto.getDeviceStatus()); 
				pstmt.setTimestamp(idx++, s_timestamp );
				pstmt.setTimestamp(idx++, e_timestamp );
				rs = pstmt.executeQuery();
			}

			System.out.println("I got the Logs of userCode : "+ dto.getUserCode());
			int i = 1;
			while (rs.next()) 
			{
				LogsDTO resultDto = new LogsDTO();
				resultDto.setUserCode(rs.getString("userCode"));
				resultDto.setUserName(rs.getString("userName"));
				resultDto.setDeviceCode(rs.getString("deviceCode"));
				resultDto.setDeviceStatus(rs.getInt("deviceStatus"));
				resultDto.setEnterTime(rs.getTimestamp("enterTime"));
				enterLogsList.add(resultDto);

				//System.out.println("=================== " + i + "th log ====================");
				//System.out.println("userCode : " + resultDto.getUserCode());
				//System.out.println("userName : " + resultDto.getUserName());
				//System.out.println("deviceCode : " + resultDto.getDeviceCode());
				//System.out.println("deviceStatus : "+ resultDto.getDeviceStatus());
				//System.out.println("enterTime : " + resultDto.getEnterTime());
				//System.out.println("===============================================");
				i++;
			}
			System.out.println("we have " + i + " logs");
			if(enterLogsList.isEmpty())
				System.out.println("No Logs.....");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return enterLogsList;
	}
	
	public int addLogs(LogsDTO logsDTO) {
		int result = 0;
		int index=1;
		PreparedStatement pstmt = null;
		Connection conn = null;
		System.out.println("Addlogs() is Called!");
		System.out.println("In "+logsDTO.getDeviceCode()+", "+logsDTO.getUserCode()+" is entered.");
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(ADD_LOGS);
			// idx,userCode,deviceCode,deviceStatus,enterTime
			
			//pstmt.setInt(index++,auto_index); //idx
			pstmt.setString(index++, logsDTO.getUserCode()); //userCode
			pstmt.setString(index++, logsDTO.getDeviceCode()); //deviceCode
			pstmt.setString(index++, logsDTO.getDeviceCode()); //deviceStatus
			//pstmt.setDate(index++, logsDTO.getEnterTime()); //entertime
			result = pstmt.executeUpdate();
			
			if(result > 0 || result == 1) 
				System.out.println("Add logs SUCCESS");
			else 
				System.out.println("Add logs FAIL");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		//GCM 전송!
		return result;
	}
}
