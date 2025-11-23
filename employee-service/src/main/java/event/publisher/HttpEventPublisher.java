package event.publisher;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import event.base.Base_event;
import event.base.EventPublisher;
import exceptions.exception.EventPublishException;

public class HttpEventPublisher<T> implements EventPublisher<T> {

	private final String targetUrl;
	private final ObjectMapper objectMapper;
	private final Map<String, String> headers;

	public HttpEventPublisher(String targetUrl, ObjectMapper objectMapper, Map<String, String> headers) {
		this.targetUrl = targetUrl;
		this.objectMapper = objectMapper;
		this.headers = headers;
	}

	@Override
	public void publishEvent(Base_event<T> event) throws EventPublishException {
		System.out.println("Publishing event via " + this.getClass().getSimpleName());
		String jsonPayload;
		try {
			jsonPayload = this.objectMapper.writeValueAsString(event.getPayload());
		} catch (JsonProcessingException e) {
			throw new EventPublishException("Failed to serialize event payload", null);
		}

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(targetUrl))
				.POST(HttpRequest.BodyPublishers.ofString(jsonPayload));

		for (Map.Entry<String, String> entry : this.headers.entrySet()) {
			builder.header(entry.getKey(), entry.getValue());
		}

		HttpRequest request = builder.build();

		HttpResponse<String> response;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			throw new EventPublishException("Failed to send HTTP request", e);
		}

		int statusCode = response.statusCode();

		if (statusCode < 200 || statusCode >= 300) {
			String body = response.body();
			throw new EventPublishException(
					"Failed to publish event to " + targetUrl + " (status: " + statusCode + ") " + body, null);
		}
	}
}
