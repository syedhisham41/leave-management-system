package exceptions.mapper;

import dto.DTO_api_response;

public interface ExceptionMapper {

	boolean canHandle(Exception e);
	
	public <T> DTO_api_response<T> mapper(Exception e);
}
