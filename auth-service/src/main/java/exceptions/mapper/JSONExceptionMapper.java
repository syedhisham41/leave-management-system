package exceptions.mapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import constants.enums.Error_code;
import dto.DTO_api_response;

public class JSONExceptionMapper implements ExceptionMapper {

	@Override
	public boolean canHandle(Exception e) {
		return e instanceof JsonMappingException || e instanceof JsonParseException
				|| e instanceof JsonProcessingException;
	}

	@Override
	public <T> DTO_api_response<T> mapper(Exception e) {
		DTO_api_response<T> response = new DTO_api_response<>();

		if (e instanceof JsonProcessingException) {
			response.setCode(Error_code.JSON_PROCESSING_ERROR.toString());
			response.setHttp_code(Error_code.JSON_PROCESSING_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof JsonMappingException) {
			response.setCode(Error_code.JSON_MAPPING_ERROR.toString());
			response.setHttp_code(Error_code.JSON_MAPPING_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		if (e instanceof JsonParseException) {
			response.setCode(Error_code.JSON_PARSE_ERROR.toString());
			response.setHttp_code(Error_code.JSON_PARSE_ERROR.getHttp_code());
			response.setMessage(e.getMessage());
		}

		return response;
	}

}
