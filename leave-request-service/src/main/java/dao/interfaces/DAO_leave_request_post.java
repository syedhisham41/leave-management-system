package dao.interfaces;

import java.sql.SQLException;

import dto.DTO_leave_request;

public interface DAO_leave_request_post {
	
	public int post_leave_request_dao(DTO_leave_request leave_request) throws SQLException;
	
	public boolean post_leave_request_overlap_check_dao(DTO_leave_request leave_request) throws SQLException;

}
