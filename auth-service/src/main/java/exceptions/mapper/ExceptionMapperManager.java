package exceptions.mapper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import constants.enums.Error_code;
import dto.DTO_api_response;

public class ExceptionMapperManager {

	private static ExceptionMapperManager instance;
	private final List<ExceptionMapper> mappers;
	private boolean debug;
	private final AtomicInteger exceptionCount;
	private final Deque<String> lastExceptions;

	public ExceptionMapperManager(boolean isDebug, List<ExceptionMapper> mappers) {
		this.debug = isDebug;
		this.mappers = mappers;
		this.exceptionCount = new AtomicInteger(0);
		this.lastExceptions = new ArrayDeque<>();

	}

	public <T> DTO_api_response<T> handle(Exception e) {

		exceptionCount.incrementAndGet();

		for (ExceptionMapper mapper : mappers) {
			if (mapper.canHandle(e)) {
				if (debug)
					System.out.println("Handled by: " + mapper.getClass().getSimpleName());
				if (lastExceptions.size() == 10)
					lastExceptions.removeFirst();
				lastExceptions.addLast(e.getClass().getSimpleName() + ": " + e.getMessage());
				return mapper.mapper(e);
			}
		}
		// fallback
		DTO_api_response<T> fallback = new DTO_api_response<>();
		fallback.setCode(Error_code.UNHANDLED_EXCEPTION.toString());
		fallback.setHttp_code(Error_code.UNHANDLED_EXCEPTION.getHttp_code());
		fallback.setMessage(e.getMessage());

		if (lastExceptions.size() == 10)
			lastExceptions.removeFirst();
		lastExceptions.addLast("Unhandled: " + e.getClass().getSimpleName() + ": " + e.getMessage());

		return fallback;
	}

	public static synchronized ExceptionMapperManager getInstance(boolean isDebug, List<ExceptionMapper> mappers) {
		if (instance == null)
			instance = new ExceptionMapperManager(isDebug, mappers);
		return instance;
	}

	public boolean isDebug() {
		return debug;
	}

	public AtomicInteger getExceptionCount() {
		return exceptionCount;
	}

	public List<String> getLastExceptions() {
		return new ArrayList<>(lastExceptions);
	}
}
