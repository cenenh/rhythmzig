package com.sgen.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.sgen.DTO.DeviceDTO;
import com.sgen.util.JDBCUtil;

public class DeviceDAO {
	
	private final String GET_DEVICE_LIST = "SELECT * FROM device;";
	private final String SET_DEVICE_STATUS = "UPDATE device SET deviceStatus = ? WHERE deviceCode = ?";
	private final String GET_OTP = "SELECT * FROM device WHERE deviceCode = ?";
	private final String SET_OTP = "UPDATE device SET otp = ? where deviceCode = ?";
	private final String GET_DEVICE_STATUS = "SELECT * FROM rhythmzig.device WHERE deviceCode = ?";
	private final String SET_OTP_TO_ORIGIN = "UPDATE device SET otp = '0000' where deviceCode = ?";
	
	public ArrayList<DeviceDTO> getDeviceList(DeviceDTO deviceDTO) 
	{
		
		ArrayList<DeviceDTO> deviceList = new ArrayList<DeviceDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = JDBCUtil.getConnection();
		try {
			pstmt = conn.prepareStatement(GET_DEVICE_LIST);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				DeviceDTO dto = new DeviceDTO();
				dto.setDeviceCode(rs.getString("deviceCode"));
				dto.setDeviceStatus(rs.getInt("deviceStatus"));
				System.out.println(dto.getDeviceCode() + dto.getDeviceStatus());
				deviceList.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			JDBCUtil.close(conn, pstmt, rs);
		}
		return deviceList;
	}
	
	public int getDeviceStatus(DeviceDTO deviceDTO) {
		
		int deviceStatus = 0;
		PreparedStatement pstmt = null;
		Connection conn = JDBCUtil.getConnection();
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(GET_DEVICE_STATUS);
			
			int idx=0;
			pstmt.setString(++idx, deviceDTO.getDeviceCode());
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				deviceStatus = rs.getInt("deviceStatus");
			}
			else 
				deviceStatus = -1;
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return deviceStatus;
	}

	public int setDeviceStatus(DeviceDTO deviceDTO) {
		PreparedStatement pstmt = null;
		Connection conn = JDBCUtil.getConnection();
		
		int result=0;
		
		try {
			pstmt = conn.prepareStatement(SET_DEVICE_STATUS);
			
			int idx=0;
			pstmt.setInt(++idx, deviceDTO.getDeviceStatus());
			pstmt.setString(++idx, deviceDTO.getDeviceCode());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}

	public DeviceDTO getOTP(DeviceDTO deviceDTO) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = JDBCUtil.getConnection();
		
		try {
			pstmt = conn.prepareStatement(GET_OTP);
			
			int idx=0;
			pstmt.setString(++idx, deviceDTO.getDeviceCode());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				deviceDTO.setOtp(rs.getString("otp"));
			} else {
				deviceDTO.setOtp("");
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deviceDTO;
	}
	
	public int setOTP(DeviceDTO deviceDTO) {
		
		PreparedStatement pstmt = null;
		Connection conn = JDBCUtil.getConnection();
		int idx=1;
		int result=0;
		//"UPDATE device SET otp = ? where deviceCode = ?";
		try {
			pstmt = conn.prepareStatement(SET_OTP);
					
			pstmt.setString(idx++, deviceDTO.getOtp());
			pstmt.setString(idx++, deviceDTO.getDeviceCode());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public int SET_OTP_TO_ORIGIN(DeviceDTO deviceDTO) {
		
		PreparedStatement pstmt = null;
		Connection conn = JDBCUtil.getConnection();
		int idx=1;
		int result=0;
		//"UPDATE device SET otp = 0000 where deviceCode = ?";
		try {
			pstmt = conn.prepareStatement(SET_OTP_TO_ORIGIN);
			pstmt.setString(idx++, deviceDTO.getDeviceCode());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}

}
