package com.tyss.lmsjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tyss.lmsjdbc.dto.UserBean;
import com.tyss.lmsjdbc.exception.LMSException;
import com.tyss.lmsjdbc.utility.JdbcUtility;

public class RegisterationLoginDAOImplementation implements RegisterationLoginDAO {

	ResultSet resultSet = null;
	Connection connection = JdbcUtility.getConnection();

	@Override
	public UserBean login(String email, String password) throws LMSException {

		UserBean bean = new UserBean();
		try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.loginQuery)){

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				bean.setEmail(resultSet.getString("email"));
				bean.setPassword(resultSet.getString("password"));
				bean.setFirstName(resultSet.getString("firstName"));
				bean.setLastName(resultSet.getString("lastName"));
				bean.setPhoneNo(resultSet.getLong("phoneNo"));
				bean.setUserId(resultSet.getInt("userId"));
				bean.setRole(resultSet.getString("role"));
				return bean;
			} else {
				System.out.println("Invalid user");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean register(UserBean bean) {
		int generatedId = 0;
		boolean result = false;
		try(PreparedStatement pstmt0 = connection.prepareStatement(QueryMapper.loginQuery)){
			pstmt0.setString(1, bean.getEmail());
			pstmt0.setString(2, bean.getPassword());
			ResultSet resultSet0 = pstmt0.executeQuery();
			if(!(resultSet0.next() && resultSet0 != null)) {
				try(PreparedStatement pstmt = connection.prepareStatement(QueryMapper.insertRegisterQuery, Statement.RETURN_GENERATED_KEYS)){
					ResultSet rs = pstmt.getGeneratedKeys();
					if(rs != null && rs.next()){
						generatedId = rs.getInt(1);
					}
					pstmt.setInt(1, generatedId);
					pstmt.setString(2, bean.getFirstName());
					pstmt.setString(3, bean.getLastName());
					pstmt.setString(4, bean.getRole());
					pstmt.setLong(5, bean.getPhoneNo());
					pstmt.setString(6, bean.getEmail());
					pstmt.setString(7, bean.getPassword());

					int count = pstmt.executeUpdate();
					if(count != 0) {
						result = true;
					} else {
						result = false;
					}
				}
			}
		} catch (SQLException e) {
			throw new LMSException("User already exists");
		}
		return result;
	}


}
