package dao.interfaces;

import java.sql.SQLException;

import dto.DTO_user_login_request;
import exceptions.exception.LastLoginUpdateFailedException;
import exceptions.exception.UserNotFoundException;
import model.User;

public interface DAO_Login {
	
	public User login(DTO_user_login_request user_details) throws SQLException, UserNotFoundException;
	
	public int updateLastLogin(int employee_id) throws SQLException, LastLoginUpdateFailedException;

}
