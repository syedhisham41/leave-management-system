package event.base;

import java.time.Instant;

public class Base_event<T> {

	private String eventType;
	private int version;
	private Instant timestamp;
	private T payload;

	public Base_event() {
	}

	public Base_event(String eventType, int version, Instant timestamp, T payload) {
		this.eventType = eventType;
		this.version = version;
		this.timestamp = timestamp;
		this.payload = payload;
	}

	public String getEventType() {
		return eventType;
	}

	public int getVersion() {
		return version;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public T getPayload() {
		return payload;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

}
