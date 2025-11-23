package config;

public class EventConfig {

	public static final String PUBLISH_MODE = System.getProperty("EVENT_PUBLISH_MODE",
			System.getenv().getOrDefault("EVENT_PUBLISH_MODE", "HTTP"));

}
