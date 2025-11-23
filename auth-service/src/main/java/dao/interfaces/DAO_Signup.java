package dao.interfaces;

import java.sql.SQLException;

import dto.DTO_user_record;
import exceptions.exception.DataAccessException;
import exceptions.exception.UserRecordDeleteFailedException;
import exceptions.exception.UserRecordNotFoundException;
import model.User;

public interface DAO_Signup {

	public int signup(User user) throws SQLException, DataAccessException;

	public DTO_user_record getUserRecord(int employee_id) throws SQLException, UserRecordNotFoundException;

	public int deleteUserRecord(int employee_id) throws SQLException, UserRecordDeleteFailedException;
}
