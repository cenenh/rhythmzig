package com.sgen.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sgen.DTO.UsersDTO;
import com.sgen.util.JDBCUtil;
import com.sgen.util.ResultStatus;

public class UsersDAO {
	private final String HAS_REG_ID = "SELECT regId FROM users WHERE userCode = ? and deviceCode = ? ";
	private final String LOGIN = "SELECT * FROM rhythmzig.users WHERE deviceCode = ? ";
	private final String CHANGE_NAME = "UPDATE users SET userName = ? WHERE userCode = ? and deviceCode = ?";
	private final String GETUSERS = "SELECT * FROM users WHERE deviceCode = ? ";
	private final String GET_ADMIN = "SELECT * FROM rhythmzig.users WHERE deviceCode=? and isAdmin=TRUE";
	private final String ADD_USER = "INSERT INTO rhythmzig.users values(? , ? , ? , ?, ?) ";
	private final String GRANT_AUTHORITY = "UPDATE users SET isAdmin = TRUE WHERE userCode = ? AND deviceCode = ?";
	private final String DEPRIVE_AUTHORITY = "UPDATE users SET isAdmin = FALSE WHERE userCode = ? AND deviceCode= ?";
	private final String REGIST_GCM_ID = "UPDATE users SET regId = ? WHERE userCode = ? AND deviceCode = ?";
	private final String IF_EXIST_USER = "SELECT COUNT(*) AS IFEXISTUSER FROM rhythmzig.users WHERE deviceCode=? AND userCode=?";
	private final String DELETE_USER = "DELETE FROM rhythmzig.users WHERE deviceCode=? AND userCode=? ";
	
	public int deleteUser(UsersDTO usersDTO)
	{
		
		int result = 0;
		int index = 1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(DELETE_USER);
			//"DELETE FROM rhythmzig.users WHERE deviceCode=? AND userCode=? ";
			pstmt.setString(index++, usersDTO.getDeviceCode());
			pstmt.setString(index++, usersDTO.getUserCode());
			System.out.println(pstmt);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public UsersDTO getAdminUserCode(UsersDTO usersDTO)
	{
		String adminUserCode = null;
		int index = 1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(GET_ADMIN);
			// deviceCode
			pstmt.setString(index++, usersDTO.getDeviceCode());
			//SELECT userCode FROM rhythmzig.users WHERE deviceCode=? and isAdmin=TRUE
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				usersDTO.setUserCode(rs.getString("userCode"));
				usersDTO.setRegId(rs.getString("regId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt,rs);
		}
		return usersDTO;
	}
	
	public int ifExistUSer(UsersDTO usersDTO)
	{
		int result = 0;
		int index = 1;
		int returnResult = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(IF_EXIST_USER);
			// deviceCode
			pstmt.setString(index++, usersDTO.getDeviceCode());
			pstmt.setString(index++, usersDTO.getUserCode());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(1) != null) {
					result = rs.getInt(1);
					if (result > 0)
						returnResult = 1;
					else
						returnResult = 0;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt,rs);
		}
		return returnResult;
	}
	
	public int addUser(UsersDTO usersDTO) {

		int result = 0;
		int index = 1;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(ADD_USER);
			// userCode , userName, isAdmin, deviceCode , regId
			pstmt.setString(index++, usersDTO.getUserCode());
			pstmt.setString(index++, usersDTO.getUserName());
			pstmt.setBoolean(index++, usersDTO.isAdmin());
			pstmt.setString(index++, usersDTO.getRegId());
			pstmt.setString(index++, usersDTO.getDeviceCode());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			if(e.getMessage().contains("Duplicate entry"))
				System.out.println("PRIMARY KEY ERROR");
			//e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}
	
	public UsersDTO isAdminUser(UsersDTO usersDTO) {

		int index = 1;
		Connection conn = null;
		UsersDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(GET_ADMIN);
			pstmt.setString(index++, usersDTO.getDeviceCode());
			rs = pstmt.executeQuery();
		
			if ( !rs.next() )
			{
				System.out.println("There is no Admin User in " +usersDTO.getDeviceCode() + " Device");
				System.out.println("[Admin USER] You entered userCode : "+usersDTO.getUserCode() + " and it is the Admin User in " + usersDTO.getDeviceCode() + " Device");
				dto = new UsersDTO();
				dto.setDeviceCode(usersDTO.getDeviceCode());
				dto.setUserCode(usersDTO.getUserCode());
				dto.setUserName(usersDTO.getUserCode());
				dto.setRegId(ResultStatus.INITIAL_REG_ID);
				dto.setAdmin(true);
			} 
			else
			{
				System.out.println(rs.getString("userCode") + " is Admin User in " + usersDTO.getDeviceCode() + " Device");
				System.out.println("[Nomal USER] You enterd userCode : "+usersDTO.getUserCode() + " and it is not Admin User in " + usersDTO.getDeviceCode() + " Device");
				dto = new UsersDTO();
				dto.setDeviceCode(usersDTO.getDeviceCode());
				dto.setUserCode(usersDTO.getUserCode());
				dto.setUserName(usersDTO.getUserCode());
				dto.setRegId(ResultStatus.INITIAL_REG_ID);
				dto.setAdmin(false);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return dto;
	}

	public UsersDTO login(UsersDTO usersDTO) {
		// TODO Auto-generated method stub
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(LOGIN);

			int idx = 0;
			pstmt.setString(++idx, usersDTO.getDeviceCode());

			rs = pstmt.executeQuery();
			String result = null;
			while (rs.next()) {
				String userCode = rs.getString("userCode");
				if (usersDTO.getUserCode().equals(userCode)) {
					result = rs.getString("userCode");
					usersDTO.setUserName(rs.getString("userName"));
					usersDTO.setAdmin(rs.getBoolean("isAdmin"));
					usersDTO.setDeviceCode(rs.getString("deviceCode"));

					break;
				} else {
					result = ResultStatus.NOSUCHUSER;
				}

			}
			if (result == null) {
				// 존재하지 않는 deviceCode
				System.out.println("no such device");
				result = ResultStatus.NOSUCHDEVICE;
			}
			usersDTO.setUserCode(result);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}

		return usersDTO;
	}

	public int changeName(UsersDTO usersDTO) {
		// TODO Auto-generated method stub
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;

		int result = 0;
		try {
			pstmt = conn.prepareStatement(CHANGE_NAME);

			int idx = 0;
			pstmt.setString(++idx, usersDTO.getUserName());
			pstmt.setString(++idx, usersDTO.getUserCode());
			pstmt.setString(++idx, usersDTO.getDeviceCode());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}

		return result;

	}

	public ArrayList<UsersDTO> getUsers(UsersDTO usersDTO) {
		// TODO Auto-generated method stub
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<UsersDTO> userlist = new ArrayList<UsersDTO>();
		try {
			pstmt = conn.prepareStatement(GETUSERS);

			pstmt.setString(1, usersDTO.getDeviceCode());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				UsersDTO user = new UsersDTO();
				user.setUserName(rs.getString("userName"));
				user.setUserCode(rs.getString("userCode"));
				user.setAdmin(rs.getBoolean("isAdmin"));
				user.setDeviceCode(rs.getString("deviceCode"));	
				user.setRegId(rs.getString("regId"));
				userlist.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}

		return userlist;
	}

	public int grantAuthority(UsersDTO userDTO) {
		// TODO Auto-generated method stub
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = conn.prepareStatement(GRANT_AUTHORITY);

			int idx = 0;
			pstmt.setString(++idx, userDTO.getUserCode());
			pstmt.setString(++idx, userDTO.getDeviceCode());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}

		return result;
	}

	public int depriveAuthority(UsersDTO userDTO) {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			pstmt = conn.prepareStatement(DEPRIVE_AUTHORITY);

			int idx = 0;
			pstmt.setString(++idx, userDTO.getUserCode());
			pstmt.setString(++idx, userDTO.getDeviceCode());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}

		return result;
	}

	public int registGCMId(UsersDTO usersDTO) {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		System.out.println("usersDTO.getRegId() : " + usersDTO.getRegId());
		System.out
				.println("usersDTO.getUserCode() : " + usersDTO.getUserCode());
		System.out.println("usersDTO.getDeviceCode() : "
				+ usersDTO.getDeviceCode());
		try {
			pstmt = conn.prepareStatement(REGIST_GCM_ID);

			int idx = 0;
			pstmt.setString(++idx, usersDTO.getRegId());
			pstmt.setString(++idx, usersDTO.getUserCode());
			pstmt.setString(++idx, usersDTO.getDeviceCode());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		System.out.println("gcm id update result : " + result);
		return result;
	}

	public String hasRegID(UsersDTO usersDTO) {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String regId = ResultStatus.NOT_EXISTS_REGIG;
		
		try {
			pstmt = conn.prepareStatement(HAS_REG_ID);
			
			int idx = 0;
			pstmt.setString(++idx, usersDTO.getUserCode());
			pstmt.setString(++idx, usersDTO.getDeviceCode());
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				regId = rs.getString("regId");
				System.out.println("rs.getString(regId) : " + regId);
				if(regId=="0" || regId==ResultStatus.NOT_EXISTS_REGIG)
					regId=ResultStatus.NOT_EXISTS_REGIG;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regId;
	}
}
